package com.logicalguess.graphQL

import com.logicalguess.domain.{Item, ItemCreationModel}
import com.logicalguess.services.{MemoryItemService, ElasticSearchItemService}
import sangria.schema._

object SchemaDefinition {

  type I = MemoryItemService

  val ItemType = ObjectType(
    "item",
    "An entity of item.",
    fields[Unit, Item](
      Field("id", StringType,
        Some("The id of the item."),
        resolve = _.value._id),
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

  val listResolver: (Context[I, Unit]) => Action[I, List[Item]] = ctx => ctx.ctx.getItems(ctx argOpt KeywordArg)
  val createResolver: (Context[I, Unit]) => Action[I, Item] = ctx =>
    ctx.ctx.addItem(ItemCreationModel(ctx arg TitleArg, ctx arg DescArg))
  val updateResolver: (Context[I, Unit]) => Action[I, Option[Item]] = ctx =>
    ctx.ctx.updateItem(Item(ctx arg ID, ctx arg TitleArg, ctx arg DescArg))
  val deleteResolver: (Context[I, Unit]) => Action[I, String] = ctx => ctx.ctx.deleteItem(ctx arg ID)

  val Query = ObjectType(
    "Query", fields[I, Unit](
      Field("item", OptionType(ItemType),
        arguments = ID :: Nil,
        resolve = ctx => ctx.ctx.getItem(ctx arg ID)),
      Field("items", ListType(ItemType),
        arguments = KeywordArg :: Nil,
        resolve = listResolver)
    ))

  val Mutation = ObjectType("MutationRoot", fields[I, Unit](
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
