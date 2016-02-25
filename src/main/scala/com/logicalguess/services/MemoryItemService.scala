package com.logicalguess.services

import java.util.UUID
import javax.inject.Singleton

import com.logicalguess.domain.{ItemCreationModel, Item}


/**
  * Created by logicalguess on 2/25/16.
  */
@Singleton
class MemoryItemService extends ItemService {

  object ItemRepo {
    var items = List(
      Item(
        _id = UUID.randomUUID().toString,
        title = "The Matrix",
        desc = "complex stuff"),
      Item(
        _id = UUID.randomUUID().toString,
        title = "The Godfather",
        desc = "simple stuff")
    )
  }

  override def getItem(id: String): Option[Item] = {
    ItemRepo.items.find(_._id == id)
  }

  override def addItem(model: ItemCreationModel): Item = {
    val item = Item(UUID.randomUUID().toString, model.title ,model.desc)
    ItemRepo.items = ItemRepo.items :+ item
    item
  }

  override def getItems(keyword: Option[String]): List[Item] = {
    ItemRepo.items
  }

  override def deleteItem(id: String): String = {
    val item = getItem(id)
    ItemRepo.items = ItemRepo.items.filter(_._id == id)
//    item match {
//      case Some(i) => Some(i._id)
//      case None => None
//    }
    id
  }

  override def updateItem(item: Item): Option[Item] = {
    None
  }
}
