package com.logicalguess.controllers

import javax.inject.{Inject, Singleton}

import com.logicalguess.domain.{Item, ItemCreationModel}
import com.logicalguess.services.ItemService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class ItemController @Inject()(itemService: ItemService)() extends Controller {

  get("/api/items/list") { request: Request =>
    itemService.getItems(None)
  }

  get("/api/items/:id") { request: Request =>
    itemService.getItem(request.params("id"))
  }
  
  post("/api/items/add") { request: ItemCreationModel =>
    val newItem = itemService.addItem(request)
    response.created(newItem)
  }

  put("/api/items/update") { request: Item =>
    itemService.updateItem(request)
  }

  delete("/api/items/delete/:id") { request: Request =>
    itemService.deleteItem(request.params("id"))
  }
}
