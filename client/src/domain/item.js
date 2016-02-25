System.register([], function(exports_1) {
    var Item;
    return {
        setters:[],
        execute: function() {
            Item = (function () {
                function Item() {
                }
                Item.fields = ['id', 'title', 'desc'];
                Item.nonIdFields = Item.fields.filter(function (field) {
                    return field !== 'id';
                });
                return Item;
            })();
            exports_1("Item", Item);
        }
    }
});
//# sourceMappingURL=item.js.map