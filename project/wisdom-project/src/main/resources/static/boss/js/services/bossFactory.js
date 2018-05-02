
var product = '/business/product/';
var account = '/business/account/';
var withdraw = '/business/withdraw/';
var transaction = '/business/transaction/';
var userType = '/business/userType/';

var bannerList = '/system/banner/';
var suggest = '/system/feedback/';

var customer  = '/user/customer/';
var user = '/user/';

var weixin = '/weixin/customer/';

define(['appBoss'], function (app) {
    app

        .factory('GetUserValidateCode',['$resource',function ($resource){
            return $resource(user + 'getUserValidateCode')
        }])


});
