package com.logicalguess.controllers

import javax.inject.{Inject, Singleton}

import com.logicalguess.domain.ItemCreationModel
import com.logicalguess.services.ItemService
import com.twitter.finagle.http.{Response, Status, Request}
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future

@Singleton
class ItemController @Inject()(itemService: ItemService)() extends Controller {

  get("/api/items/list") { request: Request =>
    itemService.getItems(None)
  }
  
  post("/api/items/add") { request: ItemCreationModel =>
    println(request)
    val newItem = itemService.addItem(request)
    response.created(newItem)//.toFuture
  }

  def toResponse(outcome: Future[_], responseBuilder: ResponseBuilder, status: Status = Status.Ok): Future[Response] = {
    outcome flatMap { result =>
      status match {
        case Status.Ok => responseBuilder.ok(result).toFuture
        case Status.Created => responseBuilder.created(result).toFuture
        case _ => responseBuilder.internalServerError.toFuture
      }
    }
  }
}
