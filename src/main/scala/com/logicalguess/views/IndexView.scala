package com.logicalguess.views

import com.twitter.finatra.response.Mustache

@Mustache("index")
case class IndexView() {
  val name = "Finatra"
}