package com.logicalguess.server

import javax.inject.Inject

import com.logicalguess.services.{ALSRecommenderService, ElasticSearchItemService}
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.utils.Handler

class RecommenderWarmupHandler @Inject()(httpWarmup: HttpWarmup, recSvc: ALSRecommenderService) extends Handler {
  override def handle() = {
    recSvc.createModel
  }
}