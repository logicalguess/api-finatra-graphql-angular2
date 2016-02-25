package com.logicalguess.domain

import com.twitter.finatra.request.QueryParam

case class Item(_id: String, title: String, desc: String)

case class ItemCreationModel(@QueryParam title: String, @QueryParam desc: String)

