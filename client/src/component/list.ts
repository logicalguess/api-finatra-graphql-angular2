import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import {Item} from './../domain/item';
import {ItemService} from './../service/items';
import {OnInit} from 'angular2/core';
import {ItemDisplayComponent} from './display';
import {ItemEditComponent} from './edit';


@Component({
	selector: 'list-items',
	template: `
		<div class="container">
			<table class="table table-striped">
				<thead>
					<tr>
						<td *ngFor="#field of fields">{{field}}</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<tr *ngFor="#item of items">
						<td *ngFor="#field of fields" (click)="setSelectedItem(item)">{{item[field]}}</td>
						<td>
							<button type="button" class="btn btn-default" (click)="remove(item)">Delete</button>
						</td>
						<td>
							<button type="button" class="btn btn-default" (click)="update(item)">Edit</button>
						</td>
					</tr>
				</tbody>
			</table>

			<item-display [item]="selectedItem"></item-display>
			<item-form *ngIf="updatingItem" [item]="updatingItem"></item-form>

		</div>
	`,
	providers: [ItemService],
	directives: [ItemDisplayComponent, ItemEditComponent]
})
export class ListItemsComponent {

	fields =  Item.fields;
	items: Item[] = [];
	selectedItem: Item = null;
	updatingItem: Item = null;


	constructor(private itemService: ItemService, private _router: Router) {

	}

	ngOnInit() {
		//this.itemService.getItems().then(_items => this.items = _items);
		this.itemService.getItems().subscribe(
			// the first argument is a function which runs on success
			data => { this.items = data},
			// the second argument is a function which runs on error
			err => console.error(err),
			// the third argument is a function which runs on completion
			() => console.log('done loading items')
		);
	}

	setSelectedItem(item: Item) {
		this.selectedItem = item;
	}

	remove(item: Item) {
		this.itemService.delete(item)
			.subscribe(
				data => {
					console.log(data),
						this._router.navigate(['ListItems', {deletedItem: item['id']}]);
				},
				err => console.log(err),
				() => console.log('Deletion Complete')
			);
	}

	update(item: Item) {
		this.updatingItem = item;
	}
}