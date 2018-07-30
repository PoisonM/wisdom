

var product ='/business/crossBorder/order/';


// var product ='http://192.168.1.197/business/crossBorder/order/';

define(['appCrossborder'], function (app) {
    app
        .factory('GetBorderSpecialProductList',['$resource',function ($resource){
            return $resource(product+'getBorderSpecialProductList');
        }])
        .factory('GetBorderSpecialProductBrandList',['$resource',function ($resource){
            return $resource(product+'getBorderSpecialProductBrandList');
        }])
        .factory('GetBorderSpecialProductDetail',['$resource',function ($resource){
            return $resource(product+'getBorderSpecialProductDetail');
        }])

        .factory('GetBorderSpecialProductOrderList',['$resource',function ($resource){
            return $resource(product+'crossBordOrderList');
        }])
        .factory('AddBorderSpecialProduct2ShoppingCart',['$resource',function ($resource){
            return $resource(product+'addProduct2ShoppingCart');
        }])
        .factory('CreateBusinessOrder',['$resource',function ($resource){
            return $resource('/business/transaction/createBusinessOrder');
        }])
        .factory('PutNeedPayOrderListToRedis',['$resource',function ($resource){
            return $resource('/business/transaction/putNeedPayOrderListToRedis');
        }])

        .factory('UpdateBusinessOrderStatus',['$resource',function ($resource){
            return $resource('/business/transaction/UpdateBusinessOrderStatus');
        }])

        .factory('DeleteOrderFromBuyCart',['$resource',function ($resource){
            return $resource(transaction + 'deleteOrderFromBuyCart')
        }])


});
