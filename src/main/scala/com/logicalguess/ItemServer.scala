package com.logicalguess

import com.logicalguess.controllers._
import com.logicalguess.filters.CorsFilter
import com.logicalguess.modules.{SparkContextModule, ItemServiceModule, ElasticClientModule}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule

object ItemServerMain extends ItemServer

class ItemServer extends HttpServer {
  override def modules = Seq(Slf4jBridgeModule, ItemServiceModule, ElasticClientModule/*, SparkContextModule*/)

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[CorsFilter]
      .add[ItemController]
      .add[GraphQLController]
      .add[IndexController]
      .add[AssetController]
      //.add[RecommenderController]
  }

  override def warmup() {
    run[WarmupHandler]()
  }
}