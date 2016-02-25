package com.logicalguess.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.sksamuel.elastic4s.ElasticClient
import com.twitter.inject.TwitterModule

object ElasticClientModule extends TwitterModule {
  private val host = flag("host", "localhost", "host name of ES")
  private val port = flag("port", 9300, "port no of ES")
  val timeout = flag("timeout", 30, "default timeout duration of execution")

  @Singleton
  @Provides
  def provideElasticClient(): ElasticClient = {
      println("------------------elastic client init-------------------")
      ElasticClient.remote(host(), port())
  }
}
