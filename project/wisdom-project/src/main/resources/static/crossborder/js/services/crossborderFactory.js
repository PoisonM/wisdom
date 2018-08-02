var product ='/business/crossBorder/order/';
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
            return $resource('/business/transaction/addProduct2BuyCart')
        }])
        .factory('MinusProduct2BuyCart',['$resource',function ($resource){
            return $resource('/business/transaction/minusProduct2BuyCart')
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
            return $resource('/user/getUserValidateCode')
        }])
        .factory('UserLogin',['$resource',function ($resource){
            return $resource('/user/crossBorderLogin')
        }])
        .factory('CreateSpecialOrderAddressRelation',['$resource',function ($resource){
            return $resource('/business/crossBorder/order/createSpecialOrderAddressRelation')
        }])
        .factory('GetNeedPayOrderListToRedis',['$resource',function ($resource){
            return $resource('/business/transaction/getNeedPayOrderListToRedis')
        }])
        .factory('CheackRealName',['$resource',function ($resource){
            return $resource('/user/customer/queryRealNameAuthentication')
        }])
        .factory('UpdateBusinessOrderAddress',['$resource',function ($resource){
            return $resource('/user/customer/queryRealNameAuthentication')
        }])
        .factory('UpdateUserAddress',['$resource',function ($resource){
            return $resource(transaction + 'updateUserAddress')
        }])
        .factory('PayOrder',['$resource',function ($resource){
            return $resource('/business/crossBorder/order/payOrder')
        }])
        .factory('CheackOrderStatus',['$resource',function ($resource){
            return $resource('/business/crossBorder/order/cheackOrderStatus')
        }])

});
