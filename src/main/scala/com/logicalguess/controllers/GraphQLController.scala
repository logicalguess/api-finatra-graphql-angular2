package com.logicalguess.controllers

import javax.inject.{Inject, Singleton}

import com.logicalguess.graphQL.SchemaDefinition
import com.logicalguess.services.item.MemoryItemService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import org.json4s.JsonAST.JString
import org.json4s._
import org.json4s.native.JsonMethods._
import sangria.execution.Executor
import sangria.integration.json4s.native.Json4sNativeInputUnmarshaller
import sangria.parser.QueryParser

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

@Singleton
class GraphQLController @Inject()(itemService: MemoryItemService)() extends Controller {
  val _awaitTimeout = 30.seconds
  val executor = Executor(
    schema = SchemaDefinition.ItemSchema,
    userContext = itemService)

  get("/api/gql/items") { request: Request =>
    val reqJson = request.getParam("query")

    QueryParser.parse(reqJson) match {
      // query parsed successfully, time to execute it!
      case Success(queryAst) =>
        Await.result(executor.execute(queryAst), _awaitTimeout)

      // can't parse GraphQL query, return error
      case Failure(error) => error.getMessage
    }
  }

  post("/api/gql/items") { request: Request =>
    println(request.getContentString())
    val reqJson = parse(request.getContentString)
    val JString(mutation) = reqJson \ "mutation"

    QueryParser.parse(mutation) match {
      // query parsed successfully, time to execute it!
      case Success(queryAst) =>
        Await.result(executor.execute(queryAst), _awaitTimeout)

      // can't parse GraphQL query, return error
      case Failure(error) => error.getMessage
    }
  }
}
