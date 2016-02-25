System.register(['angular2/core', 'angular2/router', './../domain/item', './../service/items'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, router_1, item_1, items_1;
    var ItemEditComponent;
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
            }],
        execute: function() {
            ItemEditComponent = (function () {
                function ItemEditComponent(service, _router) {
                    this.service = service;
                    this._router = _router;
                    this.fields = item_1.Item.nonIdFields;
                    this.item = new item_1.Item();
                }
                ItemEditComponent.prototype.save = function () {
                    var _this = this;
                    this.service.save(this.item).subscribe(function (data) {
                        console.log(data);
                        _this.message = 'Item Successfully Saved!';
                    }, function (err) { return console.error(err); }, function () {
                        _this.item = new item_1.Item();
                        _this._router.navigate(['ListItems']);
                        console.log('Edit Complete');
                    });
                };
                ItemEditComponent = __decorate([
                    core_1.Component({
                        selector: 'item-form',
                        template: "\n\t\t<div class=\"container\">\n\t\t\t<h1>Create Item</h1>\n\t\t\t<div class=\"alert alert-success\" role=\"alert\" *ngIf=\"message\">{{message}}</div>\n\t\t\t<form>\n\t\t\t\t<div *ngFor=\"#field of fields\" class=\"form-group\">\n\t\t\t\t\t<label for=\"title\">{{field}}</label>\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" [(ngModel)]=\"item[field]\"/>\n\t\t\t\t</div>\n\t\t\t\t<div>\n\t\t\t\t\t<button type=\"submit\" class=\"btn btn-default\" (click)=\"save()\">Save</button>\n\t\t\t\t</div>\n\t\t\t</form>\n\t\t</div>\n\t",
                        providers: [items_1.ItemService],
                        inputs: ['item']
                    }), 
                    __metadata('design:paramtypes', [items_1.ItemService, router_1.Router])
                ], ItemEditComponent);
                return ItemEditComponent;
            })();
            exports_1("ItemEditComponent", ItemEditComponent);
        }
    }
});
//# sourceMappingURL=edit.js.map