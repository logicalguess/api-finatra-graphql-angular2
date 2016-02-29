package com.logicalguess.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.inject.Injector
import com.twitter.inject.TwitterModule
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object SparkContextModule extends TwitterModule {
  private val master = flag("spark.master", "local[2]", "spark master")
  private val memory = flag("spark.executor.memory", "1g", "spark memory")
  private val sparkEnable = flag("spark.enable", "true", "true or false")

  @Singleton
  @Provides
  def provideSparkContext(): Option[SparkContext] = {
    println("------------------spark context init-------------------")

    if (sparkEnable().toBoolean) {
      Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
      Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

      val conf = new SparkConf()
        .setMaster(master())
        .setAppName("InteractiveALS")
        .set("spark.executor.memory", memory())
        .set("spark.ui.enabled", "false")
      Some(new SparkContext(conf))
    }
    else None
  }
}
