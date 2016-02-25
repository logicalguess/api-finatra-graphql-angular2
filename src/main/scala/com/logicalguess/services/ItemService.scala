package com.logicalguess.services

import com.logicalguess.domain.{Item, ItemCreationModel}

/**
  * Created by logicalguess on 2/25/16.
  */
trait ItemService {
  def getItem(id: String): Option[Item]
  def getItems(keyword: Option[String]): List[Item]
  def addItem(item: ItemCreationModel): Item
  def updateItem(item: Item): Option[Item]
  def deleteItem(id: String): String
}
