package com.logicalguess.server

import javax.inject.Inject

import com.logicalguess.services.ElasticSearchItemService
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.utils.Handler

class ElasticSearchWarmupHandler @Inject()(httpWarmup: HttpWarmup, itemService: ElasticSearchItemService) extends Handler {
  override def handle() = {
    itemService.checkAndInitIndex
  }
}