

var product ='business/crossBorder/order/';
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

});
