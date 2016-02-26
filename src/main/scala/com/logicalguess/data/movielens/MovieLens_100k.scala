package com.logicalguess.data.movielens

import com.logicalguess.data.{DataProvider, WrongInputDataException}
import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD

/**
  * Created by logicalguess on 1/18/16.
  */
case class MovieLens_100k(sc: SparkContext, dataDirectoryPath: String) extends DataProvider {
  override protected val ratings: RDD[Rating] = loadRatings(dataDirectoryPath)
  override protected val productNames: Map[Int, String] = loadProductNames(dataDirectoryPath)


  protected def loadRatings(dataDirectoryPath: String): RDD[Rating] = {

    /* Load the raw ratings data from a file */
    val rawData = sc.textFile(dataDirectoryPath + "/u.data")

    /* Extract the user id, movie id and rating only from the dataset */
    val rawRatings = rawData.map(_.split("\t").take(3))

    /* Construct the RDD of Rating objects */
    val ratings = rawRatings.map {
      case Array(user, movie, rating) => Rating(user.toInt, movie.toInt, rating.toDouble)
    }
    ratings
  }

  protected def loadProductNames(dataDirectoryPath: String): Map[Int, String] = {
    /* Load item names to inspect the recommendations */
    val items = sc.textFile(dataDirectoryPath + "/u.item")
    val pairRDD: RDD[(Int, String)] = items.map(line => line.split("\\|").take(2)).map(array => (array(0).toInt, array(1)))
    val names = pairRDD.collect.toMap
    names
  }
}
