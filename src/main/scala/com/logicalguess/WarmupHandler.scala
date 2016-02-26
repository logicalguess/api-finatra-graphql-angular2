package com.logicalguess

import com.logicalguess.services.ElasticSearchItemService
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.utils.Handler
import javax.inject.Inject

class WarmupHandler @Inject()(httpWarmup: HttpWarmup, itemService: ElasticSearchItemService) extends Handler {
  override def handle() = {
    itemService.checkAndInitIndex
  }
}