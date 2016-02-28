package com.logicalguess.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.logicalguess.services.item.{MemoryItemService, ItemService, ElasticSearchItemService}
import com.twitter.inject.TwitterModule

object ItemServiceModule extends TwitterModule {

  val MEMORY_TYPE = "memory"
  val ELASTIC_TYPE = "elastic"

  private val serviceType = flag("type", "memory", "item service type: memory or elastic")

  @Singleton
  @Provides
  def provideItemService(): ItemService = {
    println("------------------item service init-------------------")
    serviceType() match {
      case MEMORY_TYPE => new MemoryItemService()
      case ELASTIC_TYPE => new ElasticSearchItemService(
        ElasticClientModule.provideElasticClient(), ElasticClientModule.timeout().toShort)
      case _ => throw new IllegalArgumentException("unknown service type")
    }
  }
}
