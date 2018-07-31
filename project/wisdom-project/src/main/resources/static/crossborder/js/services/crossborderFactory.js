

// var product ='/business/crossBorder/order/';


var product ='http://192.168.1.130/business-service/crossBorder/order/';

define(['appCrossborder'], function (app) {

    // var product ='/business/crossBorder/order/';
    var product ='http://192.168.1.130:8081/business/crossBorder/order/';
    var ip ='http://192.168.1.130:8081/';


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

            .factory('AddProduct2BuyCart',['$resource',function ($resource){
                return $resource(ip+'/business/transaction/addProduct2BuyCart')
            }])
            .factory('MinusProduct2BuyCart',['$resource',function ($resource){
                return $resource(ip+'/business/transaction/minusProduct2BuyCart')
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
                return $resource('/business/transaction/deleteOrderFromBuyCart')
            }])
            .factory('GetUserValidateCode',['$resource',function ($resource){
                return $resource(ip+'/user/getUserValidateCode')
            }])
            .factory('UserLogin',['$resource',function ($resource){
                return $resource(ip+'/user/crossBorderLogin')
            }])
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
                return $resource(ip+'/business/transaction/createBusinessOrder');
            }])
            .factory('PutNeedPayOrderListToRedis',['$resource',function ($resource){
                return $resource(ip+'/business/transaction/putNeedPayOrderListToRedis');
            }])

            .factory('UpdateBusinessOrderStatus',['$resource',function ($resource){
                return $resource(ip+'/business/transaction/UpdateBusinessOrderStatus');
            }])
            .factory('DeleteOrderFromBuyCart',['$resource',function ($resource){
                return $resource(ip+'/business/transaction/deleteOrderFromBuyCart')
            }])
    });





});
