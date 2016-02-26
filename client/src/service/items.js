System.register(['angular2/core', 'angular2/http', 'rxjs/add/operator/map', '../domain/item'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, item_1;
    var ItemService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_1) {},
            function (item_1_1) {
                item_1 = item_1_1;
            }],
        execute: function() {
            ItemService = (function () {
                function ItemService(http) {
                    this.http = http;
                    this.serviceUrl = 'http://localhost:8888/api/gql/items';
                    this.getItemsQuery = 'query itemEntities {items {' + item_1.Item.fields.join() + '}}';
                    this.createItemCommand = function (title, desc) {
                        return 'mutation newItem { addItem(title: \\"' + title + '\\", desc: \\"' + desc + '\\") { id, title, desc} }';
                    };
                    this.updateItemCommand = function (id, title, desc) {
                        return 'mutation updateItem { updateItem(id: \\"' + id + '\\", title: \\"' + title + '\\", desc: \\"' + desc + '\\") { title, desc } }';
                    };
                    this.deleteItemCommand = function (id) {
                        return 'mutation deleteItem { deleteItem(id: \\"' + id + '\\") }';
                    };
                }
                ItemService.prototype.getItems = function () {
                    return this.http.get(this.serviceUrl + '?query=' + this.getItemsQuery)
                        .map(function (res) { return res.json().data.items; });
                };
                ItemService.prototype.save = function (item) {
                    console.log('creating', item);
                    return this.http.post(this.serviceUrl, '{"mutation":"' + (item['id'] ? this.updateItemCommand(item['id'], item['title'], item['desc']) : this.createItemCommand(item['title'], item['desc'])) + '"}')
                        .map(function (res) { return res.json(); });
                };
                ItemService.prototype.delete = function (item) {
                    return this.http.post(this.serviceUrl, '{"mutation":"' + this.deleteItemCommand(item['id']) + '"}')
                        .map(function (res) { return res.json(); });
                };
                ItemService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], ItemService);
                return ItemService;
            })();
            exports_1("ItemService", ItemService);
        }
    }
});
//# sourceMappingURL=items.js.map