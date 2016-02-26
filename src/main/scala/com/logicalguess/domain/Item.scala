package com.logicalguess.domain

import com.twitter.finatra.request.FormParam

case class Item(id: String, title: String, desc: String)

case class ItemCreationModel(@FormParam title: String, @FormParam desc: String)

