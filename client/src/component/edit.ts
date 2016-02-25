import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import {Item} from './../domain/item';
import {ItemService} from './../service/items';

@Component({
	selector: 'item-form',
	template: `
		<div class="container">
			<h1>Create Item</h1>
			<div class="alert alert-success" role="alert" *ngIf="message">{{message}}</div>
			<form>
				<div *ngFor="#field of fields" class="form-group">
					<label for="title">{{field}}</label>
					<input type="text" class="form-control" [(ngModel)]="item[field]"/>
				</div>
				<div>
					<button type="submit" class="btn btn-default" (click)="save()">Save</button>
				</div>
			</form>
		</div>
	`,
	providers: [ItemService],
	inputs: ['item']
})
export class ItemEditComponent {

	fields =  Item.nonIdFields;
	item: Item = new Item();
	message: string;

	constructor(public service: ItemService, private _router: Router) {}

	save() {
		this.service.save(this.item).subscribe(
			data => {
				console.log(data);
				this.message = 'Item Successfully Saved!';
			},
			err => console.error(err),
			() => {
				this.item = new Item();
				this._router.navigate(['ListItems']);
				console.log('Edit Complete')
			}
		);
	}
}