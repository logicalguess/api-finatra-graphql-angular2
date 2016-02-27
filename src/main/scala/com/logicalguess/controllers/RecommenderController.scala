package com.logicalguess.controllers

import javax.inject.Singleton

import com.google.inject.Inject
import com.logicalguess.services.ALSRecommenderService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class RecommenderController @Inject()(recSvc: ALSRecommenderService) extends Controller {

  get("/api/recommender/:userId") { request: Request =>
    val recommendations = recSvc.getRecommendationsForUser(request.params("userId").toInt)
    recSvc.getItems(recommendations.toList.map { r => r.product })
      .zip(recommendations.map {r => r.rating})
  }

}
