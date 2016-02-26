package com.logicalguess.domain

import com.twitter.finatra.request.QueryParam

case class Item(id: String, title: String, desc: String)

case class ItemCreationModel(title: String, desc: String)

