import {Component} from 'angular2/core';
import {RouteConfig, ROUTER_DIRECTIVES} from 'angular2/router';
import {ItemEditComponent} from './edit';
import {ListItemsComponent} from './list';

@Component({
	selector: 'my-app',
	template: `
		<ul class="nav nav-tabs">
			<li class="presentation"><a [routerLink]="['ListItems']">List Items</a></li>
			<li class="presentation"><a [routerLink]="['CreateItem']">New Item</a></li>
		</ul>
		<router-outlet></router-outlet>
	`,
	directives: [ROUTER_DIRECTIVES]
})
@RouteConfig([
	{ path: '/list', name: 'ListItems', component: ListItemsComponent},
  	{ path: '/new', name: 'CreateItem', component: ItemEditComponent}
])
export class AppComponent {
}