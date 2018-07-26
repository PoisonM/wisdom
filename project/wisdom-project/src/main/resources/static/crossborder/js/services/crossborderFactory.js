var product ='/business/product/';

define(['appCrossborder'], function (app) {
    app
        .factory('oneLevelProject',['$resource',function ($resource){
            return $resource('192.168.1.117/beauty/projectInfo/oneLevelProject');
        }])

});
