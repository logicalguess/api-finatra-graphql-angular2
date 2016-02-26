package com.logicalguess.controllers

import javax.inject.{Inject, Singleton}

import com.logicalguess.domain.ItemCreationModel
import com.logicalguess.services.ItemService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class ItemController @Inject()(itemService: ItemService)() extends Controller {

  get("/api/items/list") { request: Request =>
    itemService.getItems(None)
  }
  
  post("/api/items/add") { request: ItemCreationModel =>
    val newItem = itemService.addItem(request)
    response.created(newItem)//.toFuture
  }

  delete("/api/items/delete/:id") { request: Request =>
    itemService.deleteItem(request.params("id"))
  }
}
