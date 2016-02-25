package com.logicalguess.services

import javax.inject.{Inject, Singleton}

import com.logicalguess.domain.{Item, ItemCreationModel}
import com.sksamuel.elastic4s.ElasticDsl.{delete => delete4s, _}
import com.sksamuel.elastic4s._
import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._
import com.twitter.finatra.annotations.Flag
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.index.get.GetField

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

@Singleton
class ElasticSearchItemService @Inject()
(elasticClient: ElasticClient, @Flag("timeout") timeout: Short) extends ItemService {
  private val _index: String = "library"
  private val _type: String = "item"

  def getItem(id: String) = {
    val req = get id id from _index / _type
    val future = this.elasticClient.execute(req)
    val resp = Await.result(future, timeout.seconds)
    val map = resp.getFields
    parseToItem(resp.getId, Some(map))
  }

  def getItems(keyword: Option[String]) = {
    val req = search in _index / _type query s"*${keyword.getOrElse('*')}*" size 999
    val future = this.elasticClient.execute(req)
    Await.result(future.map(res => res.as[Item]), timeout.seconds).toList
  }

  def addItem(item: ItemCreationModel): Item = {
    val future = this.elasticClient.execute(index into _index / _type source item)
    val resp = Await.result(future, timeout.seconds)
    refreshIndex
    Item(resp.getId, item.title, item.desc)
  }

  def updateItem(item: Item): Option[Item] = {
    val req = update id item._id in _index / _type source item includeSource
    //    val future = this.client.execute(req).map(rlt => rlt.getGetResult.sourceAsString)
    val resp: UpdateResponse = Await.result(this.elasticClient.execute(req), timeout.seconds)
    val map = resp.getGetResult.getFields
    refreshIndex
    parseToItem(resp.getId, Some(map))
  }

  def deleteItem(id: String) = {
    val req = delete4s id id from _index -> _type
    val future = this.elasticClient.execute(req) map (res => (res.getId, res.getIndex))
    Await.result(future, timeout.seconds)
    refreshIndex
    id
  }

  private def parseToItem(id: String, map: Option[java.util.Map[String, GetField]]): Option[Item] = {
    map match {
      case Some(null) => None
      case Some(map) => Some(Item(id, map.get("title").toString, map.get("desc").toString))
    }
  }

  private def isIndexExists(): Boolean = {
    val req = indexExists(this._index)
    Await.result(this.elasticClient.execute(req), timeout.seconds).isExists
  }

  //refresh data manually
  def refreshIndex() = {
    Await.result(this.elasticClient.execute(refresh index this._index), timeout.seconds)
  }

  def checkAndInitIndex() = {
    if (!this.isIndexExists) {
      this.elasticClient.execute(create index this._index)
    }
  }
}