import {Injectable} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import {Item} from '../domain/item';

@Injectable()
export class ItemService {

	private serviceUrl = 'http://localhost:8888/api/gql/items';

	constructor(private http: Http) { }

	private getItemsQuery = 'query itemEntities {items {' + Item.fields.join() + '}}'
	private createItemCommand = function(title, desc) {
		return 'mutation newItem { addItem(title: \\"' + title + '\\", desc: \\"' + desc + '\\") { id, title, desc} }'
	}
	private updateItemCommand = function(id, title, desc) {
		return 'mutation updateItem { updateItem(id: \\"' + id + '\\", title: \\"' + title + '\\", desc: \\"' + desc + '\\") { title, desc } }'
	}
	private deleteItemCommand = function(id) {
		return 'mutation deleteItem { deleteItem(id: \\"' + id + '\\") }'
	}



	getItems() {

		return this.http.get(this.serviceUrl + '?query=' + this.getItemsQuery)
			.map(function (res) { return res.json().data.items; });

		//return Promise.resolve([{id: 1, title: 'abc'}]);

	}

	save(item: Item) {
		console.log('creating', item)
		return this.http.post(this.serviceUrl, '{"mutation":"' + (item['id'] ? this.updateItemCommand(item['id'], item['title'], item['desc']) : this.createItemCommand(item['title'], item['desc'])) + '"}')
			.map(res => res.json());
	}

	delete(item: Item) {
		return this.http.post(this.serviceUrl, '{"mutation":"' + this.deleteItemCommand(item['id']) + '"}')
			.map(res => res.json());
	}
}