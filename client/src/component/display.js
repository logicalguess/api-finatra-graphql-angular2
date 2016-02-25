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
    var ItemDisplayComponent;
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
            ItemDisplayComponent = (function () {
                function ItemDisplayComponent(itemService, _router) {
                    this.itemService = itemService;
                    this._router = _router;
                    this.fields = item_1.Item.fields;
                }
                ItemDisplayComponent.prototype.remove = function () {
                    var _this = this;
                    this.itemService.delete(this.item)
                        .subscribe(function (data) {
                        console.log(data),
                            _this._router.navigate(['ListItems', { deletedItem: _this.item['id'] }]);
                    }, function (err) { return console.log(err); }, function () { return console.log('Deletion Complete'); });
                };
                ItemDisplayComponent = __decorate([
                    core_1.Component({
                        selector: 'item-display',
                        template: "\n\t\t<div class=\"panel panel-default\" *ngIf=\"item\" style=\"margin-top:20px\">\n\t\t\t<div class=\"panel-heading\">Details for: {{item.title}}</div>\n\t\t\t<div class=\"panel-body\">\n\t\t\t\t<div class=\"form-horizontal\">\n\t\t\t\t\t<div *ngFor=\"#field of fields\" class=\"form-group\">\n\t\t    \t\t\t<label class=\"col-sm-1 control-label\">{{field}}:</label>\n\t\t    \t\t\t<div class=\"col-sm-11\">\n\t\t    \t\t\t\t<p class=\"form-control-static\">{{item[field]}}</p>\n\t\t    \t\t\t</div>\n\t\t  \t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"remove()\">Delete</button>\n\t\t\t</div>\n\t\t</div>\n\n\n\t",
                        providers: [items_1.ItemService],
                        inputs: ['item']
                    }), 
                    __metadata('design:paramtypes', [items_1.ItemService, router_1.Router])
                ], ItemDisplayComponent);
                return ItemDisplayComponent;
            })();
            exports_1("ItemDisplayComponent", ItemDisplayComponent);
        }
    }
});
//# sourceMappingURL=display.js.map