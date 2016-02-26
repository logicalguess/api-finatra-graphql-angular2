package com.logicalguess.data

import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD

/**
 * Holds ratings and a Map which maps IDs to names of products
 */
trait DataProvider {

  protected val ratings: RDD[Rating]
  protected val productNames: Map[Int, String]

  def getRatings(): RDD[Rating] = ratings

  def getProductNames(): Map[Int, String] = productNames
}

class WrongInputDataException extends Exception

