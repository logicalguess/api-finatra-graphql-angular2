import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import {Item} from './../domain/item';
import {ItemService} from './../service/items';

@Component({
	selector: 'item-display',
	template: `
		<div class="panel panel-default" *ngIf="item" style="margin-top:20px">
			<div class="panel-heading">Details for: {{item.title}}</div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div *ngFor="#field of fields" class="form-group">
		    			<label class="col-sm-1 control-label">{{field}}:</label>
		    			<div class="col-sm-11">
		    				<p class="form-control-static">{{item[field]}}</p>
		    			</div>
		  			</div>
				</div>
				<button type="button" class="btn btn-default" (click)="remove()">Delete</button>
			</div>
		</div>


	`,
	providers: [ItemService],
	inputs: ['item']
})
export class ItemDisplayComponent {

	constructor(private itemService: ItemService, private _router: Router) {}

	fields =  Item.fields;
	item: Item;

	remove() {
		this.itemService.delete(this.item)
			.subscribe(
				data => {
					console.log(data),
					this._router.navigate(['ListItems', {deletedItem: this.item['id']}]);
				},
				err => console.log(err),
				() => console.log('Deletion Complete')
			);
	}
}