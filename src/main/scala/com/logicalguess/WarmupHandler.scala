package com.logicalguess

import com.logicalguess.services.ItemService
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.httpclient.RequestBuilder._
import com.twitter.finatra.utils.Handler
import javax.inject.Inject

class WarmupHandler @Inject()(httpWarmup: HttpWarmup, bookSrv: ItemService) extends Handler {
  override def handle() = {
    bookSrv.checkAndInitIndex
  }
}