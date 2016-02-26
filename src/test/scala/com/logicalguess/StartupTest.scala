package com.logicalguess

import com.google.inject.Stage
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Test
import server.ItemServer

class StartupTest extends Test {

  val server = new EmbeddedHttpServer(
    stage = Stage.PRODUCTION,
    twitterServer = new ItemServer)

  "Application" should {
    "startup" in {
      server.assertHealthy()
    }
  }
}
