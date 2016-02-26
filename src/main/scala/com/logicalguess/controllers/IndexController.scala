package com.logicalguess.controllers

import javax.inject.{Inject, Singleton}
import com.logicalguess.services.ItemService
import com.logicalguess.views.IndexView
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class IndexController @Inject()(itemService: ItemService)() extends Controller {

  get("/") { request: Request =>
    IndexView()
  }

}
