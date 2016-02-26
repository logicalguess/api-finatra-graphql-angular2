package com.logicalguess.controllers

import javax.inject.Singleton
import com.logicalguess.views.IndexView
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class IndexController() extends Controller {

  get("/") { request: Request =>
    IndexView()
  }

}
