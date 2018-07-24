var product ='/business/product/';

define(['appCrossborder'], function (app) {
    app
        .factory('UserLoginOut',['$resource',function ($resource){
            return $resource(product + 'loginOut');
        }])

});
