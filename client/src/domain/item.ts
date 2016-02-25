export class Item {
	static fields =  ['id', 'title', 'desc'];
	static nonIdFields = Item.fields.filter(function( field ) {
		return field !== 'id';
	});
}