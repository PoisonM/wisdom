
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

define(['appCustomer'], function (app) {
    app

        .factory('GetUserValidateCode',['$resource',function ($resource){
            return $resource(user + 'getUserValidateCode')
        }])
        .factory('UserLogin',['$resource',function ($resource){
            return $resource(user + 'userLogin')
        }])
        .factory('UserLoginOut',['$resource',function ($resource){
            return $resource(user + 'userLoginOut')
        }])
        .factory('GetUserInfo',['$resource',function ($resource){
            return $resource(customer + 'getUserInfo')
        }])
        .factory('GetUserInfoByOpenId',['$resource',function ($resource){
            return $resource(customer + 'getUserInfoByOpenId')
        }])
        .factory('GetUserNextLevelStruct',['$resource',function ($resource){
            return $resource(userType + 'getUserNextLevelStruct')
        }])

        .factory('GetHomeBannerList',['$resource',function ($resource){
            return $resource(bannerList + 'getHomeBannerList')
        }])

        .factory('GetTrainingProductDetail',['$resource',function ($resource){
            return $resource(product + 'getTrainingProductDetail')
        }])
        .factory('GetBusinessProductInfo',['$resource',function ($resource){
            return $resource(product + 'getBusinessProductInfo')
        }])
        .factory('GetTrainingProductPlayNum',['$resource',function ($resource){
            return $resource(product + 'getTrainingProductPlayNum')
        }])
        .factory('GetOfflineProductList',['$resource',function ($resource){
            return $resource(product + 'getOfflineProductList')
        }])
        .factory('GetTrainingProductListNeedPay',['$resource',function ($resource){
            return $resource(product + 'getTrainingProductListNeedPay')
        }])
        .factory('GetTrainingProductListNoNeedPay',['$resource',function ($resource){
            return $resource(product + 'getTrainingProductListNoNeedPay')
        }])
        .factory('GetOfflineProductDetail',['$resource',function ($resource){
            return $resource(product + 'getOfflineProductDetail')
        }])
        .factory('AddTrainingProductPlayNum',['$resource',function ($resource){
            return $resource(product + 'AddTrainingProductPlayNum')
        }])
        //发票
        .factory('AddInvoiceInfo',['$resource',function ($resource){
            return $resource( product+ 'addInvoiceInfo')
        }])

        .factory('GetAttentionTeacherStatus',['$resource',function ($resource){
            return $resource( transaction+ 'getAttentionTeacherStatus')
        }])
        .factory('AttentionTeacher',['$resource',function ($resource){
            return $resource( transaction+ 'attentionTeacher')
        }])
        .factory('BuriedPoint',['$resource',function ($resource){
            return $resource( "http://mx99test1.kpbeauty.com.cn:3000/dotLog");
        }])
        .factory('GetOrderAddressDetail',['$resource',function ($resource){
            return $resource( transaction+ 'orderAddressDetail')
        }])
        .factory('GetOrderDetailInfo',['$resource',function ($resource){
            return $resource( transaction+ 'orderDetailInfo')
        }])
        .factory('GetTripleMonthBonus',['$resource',function ($resource){
            return $resource( transaction+ 'getTripleMonthBonus')
        }])
        //获取
        .factory('QueryOrderCopRelationById',['$resource',function ($resource){
            return $resource(transaction+'queryOrderCopRelationById')
        }])
        .factory('GetBusinessOrderByProductId',['$resource',function ($resource){
            return $resource(transaction + 'getBusinessOrderByProductId')
        }])
        .factory('CheckTripleMonthBonus',['$resource',function ($resource){
            return $resource( transaction + 'checkTripleMonthBonus');
        }])
        // 点击查看是否购买视频
         .factory('GetTrainingBusinessOrder',['$resource',function ($resource){
             return $resource(transaction + 'getTrainingBusinessOrder')
         }])
        .factory('AddProduct2BuyCart',['$resource',function ($resource){
            return $resource(transaction + 'addProduct2BuyCart')
        }])
        .factory('MinusProduct2BuyCart',['$resource',function ($resource){
            return $resource(transaction + 'minusProduct2BuyCart')
        }])
        .factory('GetProductNumFromBuyCart',['$resource',function ($resource){
            return $resource(transaction + 'getProductNumFromBuyCart')
        }])
        .factory('GetBuyCartInfo',['$resource',function ($resource){
            return $resource(transaction + 'buyCart')
        }])
        .factory('DeleteOrderFromBuyCart',['$resource',function ($resource){
            return $resource(transaction + 'deleteOrderFromBuyCart')
        }])
        .factory('PutNeedPayOrderListToRedis',['$resource',function ($resource){
            return $resource(transaction + 'putNeedPayOrderListToRedis')
        }])
        .factory('GetUserAddressList',['$resource',function ($resource){
            return $resource(transaction + 'userAddressList')
        }])
        .factory('AddUserAddress',['$resource',function ($resource){
            return $resource(transaction + 'addUserAddress')
        }])
        .factory('UpdateUserAddress',['$resource',function ($resource){
            return $resource(transaction + 'updateUserAddress')
        }])
        .factory('DeleteUserAddress',['$resource',function ($resource){
            return $resource(transaction + 'deleteUserAddress')
        }])
        .factory('GetTransactionList',['$resource',function ($resource){
            return $resource(transaction + 'getTransactionList')
        }])
        .factory('GetUserTransactionDetail',['$resource',function ($resource){
            return $resource(transaction + 'getUserTransactionDetail')
        }])
        .factory('GetBusinessOrderList',['$resource',function ($resource){
            return $resource(transaction + 'businessOrderList')
        }])
        .factory('UpdateBusinessOrderStatus',['$resource',function ($resource){
            return $resource(transaction + 'updateBusinessOrderStatus')
        }])
        .factory('CreateBusinessOrder',['$resource',function ($resource){
            return $resource(transaction + 'createBusinessOrder')
        }])
        .factory('FindUserAddressById',['$resource',function ($resource){
            return $resource(transaction + 'findUserAddressById')
        }])
        .factory('GetUserAccountInfo',['$resource',function ($resource){
            return $resource(account + 'getUserAccountInfo')
        }])
        .factory('WithDrawMoneyFromAccount',['$resource',function ($resource){
            return $resource( withdraw+'withDrawMoneyFromAccount')
        }])

        .factory('SuggestionDetail',['$resource',function ($resource){
            return $resource(suggest + 'suggestionDetail')
        }])

        //获取物流信息
        .factory('GetlogisticsQuery',['$resource',function ($resource){
            return $resource('http://47.100.102.37:3000/logisticsQuery')
        }])

        //获取用户的推广二维码
        .factory('GetQRCodeURL',['$resource',function ($resource){
            return $resource(weixin + 'getUserQRCode')
        }])

        //获取用户的推广二维码
        .factory('GetSpecialProductList',['$resource',function ($resource){
            return $resource(product + 'getSpecialProductList')
        }])

        .factory('GetSpecialShopInfo',['$resource',function ($resource){
            return $resource(product + 'getSpecialShopInfo')
        }])


});
