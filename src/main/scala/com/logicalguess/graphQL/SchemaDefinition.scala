package com.logicalguess.graphQL

import com.logicalguess.domain.{Item, ItemCreationModel}
import com.logicalguess.services.{ItemService, MemoryItemService, ElasticSearchItemService}
import sangria.schema._

object SchemaDefinition {

  val ItemType = ObjectType(
    "item",
    "An entity of item.",
    fields[Unit, Item](
      Field("id", StringType,
        Some("The id of the item."),
        resolve = _.value.id),
      Field("title", StringType,
        Some("The title of the item."),
        resolve = _.value.title),
      Field("desc", OptionType(StringType),
        Some("The description of the item."),
        resolve = _.value.desc)
    ))

  val ID = Argument("id", StringType, description = "id of the item")
  val TitleArg = Argument("title", StringType, description = "title of the item")
  val DescArg = Argument("desc", StringType, description = "description of the item")
  val KeywordArg = Argument("keyword", OptionInputType(StringType), description = "keyword of filtering items")

  val singleResolver: (Context[ItemService, Unit]) => Action[ItemService, Option[Item]] = ctx =>
    ctx.ctx.getItem(ctx arg ID)
  val listResolver: (Context[ItemService, Unit]) => Action[ItemService, List[Item]] = ctx => ctx.ctx.getItems(ctx argOpt KeywordArg)
  val createResolver: (Context[ItemService, Unit]) => Action[ItemService, Item] = ctx =>
    ctx.ctx.addItem(ItemCreationModel(ctx arg TitleArg, ctx arg DescArg))
  val updateResolver: (Context[ItemService, Unit]) => Action[ItemService, Option[Item]] = ctx =>
    ctx.ctx.updateItem(Item(ctx arg ID, ctx arg TitleArg, ctx arg DescArg))
  val deleteResolver: (Context[ItemService, Unit]) => Action[ItemService, String] = ctx =>
    ctx.ctx.deleteItem(ctx arg ID)

  val Query = ObjectType(
    "Query", fields[ItemService, Unit](
      Field("item", OptionType(ItemType),
        arguments = ID :: Nil,
        resolve = singleResolver),
      Field("items", ListType(ItemType),
        arguments = KeywordArg :: Nil,
        resolve = listResolver)
    ))

  val Mutation = ObjectType("MutationRoot", fields[ItemService, Unit](
    Field("addItem", OptionType(ItemType),
      arguments = TitleArg :: DescArg :: Nil,
      resolve = createResolver),
      Field("updateItem", OptionType(ItemType),
        arguments = ID :: TitleArg :: DescArg :: Nil,
        resolve = updateResolver),
      Field("deleteItem", OptionType(IDType),
        arguments = ID :: Nil,
        resolve = deleteResolver)
    ))

  val ItemSchema = Schema(Query, Some(Mutation))
}
