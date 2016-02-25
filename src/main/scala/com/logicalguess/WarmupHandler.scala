package com.logicalguess

import com.logicalguess.services.ElasticSearchItemService
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.utils.Handler
import javax.inject.Inject

class WarmupHandler @Inject()(httpWarmup: HttpWarmup, bookSrv: ElasticSearchItemService) extends Handler {
  override def handle() = {
    bookSrv.checkAndInitIndex
  }
}