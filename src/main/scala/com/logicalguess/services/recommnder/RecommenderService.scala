package com.logicalguess.services.recommnder

import org.apache.spark.mllib.recommendation.Rating

/**
  * Created by logicalguess on 2/28/16.
  */
trait RecommenderService {
  def getRecommendationsForUser(userId: Int, count: Int): Array[Rating]
  def getItems(itemIds: List[Int]): List[String]
}
