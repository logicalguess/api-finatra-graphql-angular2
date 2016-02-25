System.register(['angular2/core', 'angular2/router', './../domain/item', './../service/items', './display', './edit'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, router_1, item_1, items_1, display_1, edit_1;
    var ListItemsComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (item_1_1) {
                item_1 = item_1_1;
            },
            function (items_1_1) {
                items_1 = items_1_1;
            },
            function (display_1_1) {
                display_1 = display_1_1;
            },
            function (edit_1_1) {
                edit_1 = edit_1_1;
            }],
        execute: function() {
            ListItemsComponent = (function () {
                function ListItemsComponent(itemService, _router) {
                    this.itemService = itemService;
                    this._router = _router;
                    this.fields = item_1.Item.fields;
                    this.items = [];
                    this.selectedItem = null;
                    this.updatingItem = null;
                }
                ListItemsComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    //this.itemService.getItems().then(_items => this.items = _items);
                    this.itemService.getItems().subscribe(
                    // the first argument is a function which runs on success
                    function (data) { _this.items = data; }, 
                    // the second argument is a function which runs on error
                    function (err) { return console.error(err); }, 
                    // the third argument is a function which runs on completion
                    function () { return console.log('done loading items'); });
                };
                ListItemsComponent.prototype.setSelectedItem = function (item) {
                    this.selectedItem = item;
                };
                ListItemsComponent.prototype.remove = function (item) {
                    var _this = this;
                    this.itemService.delete(item)
                        .subscribe(function (data) {
                        console.log(data),
                            _this._router.navigate(['ListItems', { deletedItem: item['id'] }]);
                    }, function (err) { return console.log(err); }, function () { return console.log('Deletion Complete'); });
                };
                ListItemsComponent.prototype.update = function (item) {
                    this.updatingItem = item;
                };
                ListItemsComponent = __decorate([
                    core_1.Component({
                        selector: 'list-items',
                        template: "\n\t\t<div class=\"container\">\n\t\t\t<table class=\"table table-striped\">\n\t\t\t\t<thead>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td *ngFor=\"#field of fields\">{{field}}</td>\n\t\t\t\t\t\t<td></td>\n\t\t\t\t\t</tr>\n\t\t\t\t</thead>\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr *ngFor=\"#item of items\">\n\t\t\t\t\t\t<td *ngFor=\"#field of fields\" (click)=\"setSelectedItem(item)\">{{item[field]}}</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"remove(item)\">Delete</button>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"update(item)\">Edit</button>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</tbody>\n\t\t\t</table>\n\n\t\t\t<item-display [item]=\"selectedItem\"></item-display>\n\t\t\t<item-form *ngIf=\"updatingItem\" [item]=\"updatingItem\"></item-form>\n\n\t\t</div>\n\t",
                        providers: [items_1.ItemService],
                        directives: [display_1.ItemDisplayComponent, edit_1.ItemEditComponent]
                    }), 
                    __metadata('design:paramtypes', [items_1.ItemService, router_1.Router])
                ], ListItemsComponent);
                return ListItemsComponent;
            })();
            exports_1("ListItemsComponent", ListItemsComponent);
        }
    }
});
//# sourceMappingURL=list.js.map