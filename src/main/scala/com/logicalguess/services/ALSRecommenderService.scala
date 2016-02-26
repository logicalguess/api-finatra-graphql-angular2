package com.logicalguess.services

import javax.inject.{Inject, Singleton}

import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD

/**
  * Created by logicalguess on 2/26/16.
  */

@Singleton
class ALSRecommenderService @Inject()(sc: SparkContext) {
  //TODO change state management

  val movieLensHomeDir = "src/main/resources/ml-1m"

  val ratings: RDD[(Long, Rating)] = getRatings
  val items: RDD[(Int, String)] = getItems
  val movies = items.collect.toMap
  val model: MatrixFactorizationModel = createModel

  def getRecommendationsForUser(userId: Int) = {
    val candidates = sc.parallelize(movies.keys.toSeq)

    // println("bestModel.get " + bestModel.get.predict(candidates.map((0, _))).take(20).foreach(println))
    // println("bestModel " +  bestModel )
    // use candidates.map((0, _)) returns an empty set.
    // use candidates.map((1, _)) returns a recommend list set.
    // it means the user real time data must be in the training set.
    model
      .predict(candidates.map((userId, _)))
      .collect
      .sortBy(- _.rating)
      .take(30)
  }

  def getItems(itemIds: List[Int]): List[String] = {
    itemIds.map { id => movies(id)}
  }

  def createModel: MatrixFactorizationModel = {
    val numRatings = ratings.count
    // _._2 is the RDD ratings's Rating in the (Int, Rating) pairs
    // The Rating class is a wrapper around tuple (user: Int, product: Int, rating: Double)
    val numUsers = ratings.map(_._2.user).distinct.count
    val numMovies = ratings.map(_._2.product).distinct.count

    println("Loaded data: " + numRatings + " ratings from "
      + numUsers + " users on " + numMovies + " movies.")

    // We will use MLlib’s ALS to train a MatrixFactorizationModel,
    // which takes a RDD[Rating] object as input.
    // ALS has training parameters such as rank for matrix factors and regularization constants.
    // To determine a good combination of the training parameters,
    // we split ratings into train (60%), validation (20%), and test (20%) based on the
    // last digit of the timestamp, and cache them

    val numPartitions = 20
    // ratings format // format: (timestamp % 10, Rating(userId, movieId, rating))
    // The training set is 60%, it is based on the last digit of the timestamp
    // change to 30%, 10% and 10%
    val training = ratings.filter(x => x._1 <= 3)
      .values
      .repartition(numPartitions)
      .persist
    // val validation = ratings.filter(x => x._1 >= 3 && x._1 < 8)
    val validation = ratings.filter(x => x._1 == 4 )
      .values
      .repartition(numPartitions)
      .persist
    // val test = ratings.filter(x => x._1 >= 8).values.persist
    val test = ratings.filter(x => x._1 == 5).values.persist

    val numTraining = training.count
    val numValidation = validation.count
    val numTest = test.count

    println("\nStep 2, train with " + numTraining + " ratings.")
    // println("\nTraining: " + numTraining + " ratings, validation: " + numValidation + " ratings, test: " + numTest + " ratings.")

    // train models and evaluate them on the validation set
    // we will test only 8 combinations resulting from the cross product of 2 different ranks (8 and 12)
    // use rank 12 to reduce the running time
    // val ranks = List(8, 12)
    val ranks = List(12)

    // 2 different lambdas (1.0 and 10.0)
    val lambdas = List(0.1, 10.0)

    // two different numbers of iterations (10 and 20)
    // use numIters 20 to reduce the running time
    // val numIters = List(10, 20)
    val numIters = List(10)

    // We use the provided method computeRmse to compute the RMSE on the validation set for each model.
    // The model with the smallest RMSE on the validation set becomes the one selected
    // and its RMSE on the test set is used as the final metric
    // import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
    var bestModel: Option[MatrixFactorizationModel] = None
    var bestValidationRmse = Double.MaxValue
    var bestRank = 0
    var bestLambda = -1.0
    var bestNumIter = -1
    for (rank <- ranks; lambda <- lambdas; numIter <- numIters) {
      // in object ALS
      // def train(ratings: RDD[Rating], rank: Int, iterations: Int, lambda: Double) : MatrixFactorizationModel
      val model = ALS.train(training, rank, numIter, lambda)

      // def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], n: Long)
      // return  math.sqrt, type is double
      // model is from training.
      val validationRmse = computeRmse(model, validation, numValidation)
      // println("RMSE (validation) = " + validationRmse + " for the model trained with rank = "
      //    + rank + ", lambda = " + lambda + ", and numIter = " + numIter + ".")
      if (validationRmse < bestValidationRmse) {
        // println("inside bestModel  " +  bestModel);
        bestModel = Some(model)
        bestValidationRmse = validationRmse
        bestRank = rank
        bestLambda = lambda
        bestNumIter = numIter
      }
    }

    // evaluate the best model on the test set
    println("\nStep 3, evaluate the best model on the test set.")

    val testRmse = computeRmse(bestModel.get, test, numTest)

    println("The best model was trained with rank = " + bestRank + " and lambda = " + bestLambda
      + ", and numIter = " + bestNumIter + ", and its RMSE on the test set is " + testRmse + ".")
    bestModel.get
  }

  def getItems: RDD[(Int, String)] = {
    val items = sc.textFile(movieLensHomeDir + "/movies.dat").map { line =>
      val fields = line.split("::")
      // MovieID::Title::Genres
      // e.g.
      // 1::Toy Story (1995)::Animation|Children's|Comedy
      //  read in movie ids and titles only
      // format: (movieId, movieName)
      (fields(0).toInt, fields(1))
    }
    items
  }

  def getRatings: RDD[(Long, Rating)] = {
    val ratings = sc.textFile(movieLensHomeDir + "/ratings.dat").map { line =>
      val fields = line.split("::")
      // UserID::MovieID::Rating::Timestamp
      // e.g.
      // 1::1193::5::978300760
      // The RDD contains (Int, Rating) pairs.
      // We only keep the last digit of the timestamp as a random key: = fields(3).toLong % 10
      // The Rating class is a wrapper around tuple (user: Int, product: Int, rating: Double)
      //      defined in org.apache.spark.mllib.recommendation package.
      // format: (timestamp % 10, Rating(userId, movieId, rating))
      //
      (fields(3).toLong % 10, Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble))
    }
    ratings
  }

  /** Compute RMSE (Root Mean Squared Error). */
  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], n: Long) = {
    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))
    val predictionsAndRatings = predictions.map(x => ((x.user, x.product), x.rating))
      .join(data.map(x => ((x.user, x.product), x.rating)))
      .values
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).reduce(_ + _) / n)
  }


}
