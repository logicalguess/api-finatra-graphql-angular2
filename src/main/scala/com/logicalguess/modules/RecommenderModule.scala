package com.logicalguess.modules

import javax.inject.Singleton

import com.google.inject.{Inject, Provides}
import com.logicalguess.data.DataProvider
import com.logicalguess.data.movielens.{MovieLens_100k, MovieLens_1m}
import com.logicalguess.services._
import com.logicalguess.services.recommnder.{MahoutRecommender, RecommenderService, ALSRecommenderService}
import com.twitter.inject.TwitterModule
import org.apache.spark.SparkContext

object RecommenderModule extends TwitterModule {
  flag("rec.count", 10, "number of recommendations to be returned")

  private val dataSet = flag("data.set", "100k", "1m or 100k")
  private val recType = flag("rec.type", "als", "als or mahout")


  val MOVIE_LENS_1M = "1m"
  val MOVIE_LENS_100K = "100k"

  val REC_TYPE_ALS = "als"
  val REC_TYPE_MAHOUT = "mahout"

  @Singleton
  @Provides
  def dataProvider(@Inject() sc: SparkContext): DataProvider = {
    println("------------------data provider init-------------------")
    dataSet() match {
      case MOVIE_LENS_1M => MovieLens_1m(sc, "src/main/resources/ml-1m")
      case MOVIE_LENS_100K => MovieLens_100k(sc, "src/main/resources/ml-100k")
      case _ => throw new IllegalArgumentException("unknown data set")
    }
  }

  @Singleton
  @Provides
  def recommenderProvider(@Inject() sc: SparkContext, dataProvider: DataProvider): RecommenderService = {

    recType() match {
      case REC_TYPE_ALS => ALSRecommenderService(sc, dataProvider)
      case REC_TYPE_MAHOUT => MahoutRecommender(sc, dataProvider)
      case _ => throw new IllegalArgumentException("unknown data set")
    }
  }
}
