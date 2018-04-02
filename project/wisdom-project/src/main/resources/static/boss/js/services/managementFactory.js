var product ='/business/product/';
var withdraw ='/business/withdraw/';
var income ='/business/income/';
var account = '/business/account/';
var system = '/business/system/';
var transaction = '/business/transaction/';

var customer = '/user/customer/';


define(['appManagement'], function (app) {
    app
        .factory('UserLoginOut',['$resource',function ($resource){
            return $resource(system + 'loginOut');
        }])
        /*上传图片*/
        .factory('ImageUploadToOSS',['$resource',function ($resource){
            return $resource(system + 'imageUploadToOSS');
        }])
        /*导出列表*/
        .factory('ExportExcelToOSS',['$resource',function ($resource){
            return $resource(transaction + 'exportExcelToOSS');
        }])
        /*代理详情*/
        .factory('QueryUserBusinessById',['$resource',function ($resource){
            return $resource(system + 'queryUserBusinessById');
        }])
        /*发展的*用户/*/
        .factory('QueryNextUserById',['$resource',function ($resource){
            return $resource(system + 'queryNextUserById');
        }])
        /*管理员登录*/
        .factory('ManagerLogin',['$resource',function ($resource){
            return $resource(system + 'managerLogin');
        }])

        /*人员管理*/
        .factory('QueryUserInfoDTOByParameters',['$resource',function ($resource){
            return $resource(customer + 'queryUserInfoDTOByParameters');
        }])

        /*首页*/
        /*按钮的切换与搜索*/
        .factory('QueryProductsByParameters',['$resource',function ($resource){
            return $resource(product + 'queryProductsByParameters');
        }])
        /*更改状态 上架*/
        .factory('PutAwayProductById',['$resource',function ($resource){
            return $resource(product + 'putAwayProductById');
        }])
        /*更改状态 下架*/
        .factory('DelProductById',['$resource',function ($resource){
            return $resource(product + 'delProductById');
        }])
        /*编辑*/
        .factory('FindProductById',['$resource',function ($resource){
            return $resource(product + 'findProductById');
        }])
        /*保存*/
        .factory('UpdateProductByParameters',['$resource',function ($resource){
            return $resource(product + 'updateProductByParameters');
        }])
        /*上传商品*/
        .factory('AddOfflineProduct',['$resource',function ($resource){
            return $resource(product + 'addOfflineProduct');
        }])
        /*运营管理*/
        .factory('QueryAllTrainingProductDTO',['$resource',function ($resource){
            return $resource(product + 'queryAllTrainingProductDTO');
        }])

         /*财务管理——提现*/
        /*改变状态*/
        .factory('UpdateWithdrawById',['$resource',function ($resource){
            return $resource(withdraw + 'updateWithdrawById');
        }])
        /*按条件查询*/
        .factory('QueryWithdrawsByParameters',['$resource',function ($resource){
            return $resource(withdraw + 'queryWithdrawsByParameters');
        }])


        /*财务管理——余额*/
        /*搜索*/
        .factory('QueryUserBalanceByParameters',['$resource',function ($resource){
            return $resource(account + 'queryUserBalanceByParameters');
        }])

        /*账单*/
        /*获得所有数据与按条件查询*/
        .factory('QueryPayRecordsByParameters',['$resource',function ($resource){
            return $resource(account + 'queryPayRecordsByParameters');
        }])

        /*月结*/
        /*按条件查询*/
        .factory('QueryUserIncomeByParameters',['$resource',function ($resource){
            return $resource(income + 'queryUserIncomeByParameters');
        }])

        /*详情 月度结算*/
        .factory('QueryMonthTransactionRecordByIncomeRecord',['$resource',function ($resource){
            return $resource(income + 'queryMonthTransactionRecordByIncomeRecord');
        }])
        .factory('QueryMonthPayRecordByUserId',['$resource',function ($resource){
            return $resource(income + 'queryMonthPayRecordByUserId');
        }])
        /*详情 即时奖励*/
        .factory('QueryInstanceInfoByTransactionId',['$resource',function ($resource){
            return $resource(income + 'queryInstanceInfoByTransactionId');
        }])
        /*审核*/
        .factory('UpdateIncomeRecordStatusById',['$resource',function ($resource){
            return $resource(income + 'updateIncomeRecordStatusById');
        }])


        /*订单页  查询与 全部 确认收货。。*/
        .factory('QueryBusinessOrderByParameters',['$resource',function ($resource){
            return $resource(transaction + 'queryBusinessOrderByParameters');
        }])
        /*展示所有*/
        .factory('QueryAllBusinessOrders',['$resource',function ($resource){
            return $resource(transaction + 'queryAllBusinessOrders');
        }])
    /*详情*/
        .factory('QueryOrderDetailsById',['$resource',function ($resource){
            return $resource(transaction + 'queryOrderDetailsById');
        }])
    /*编辑地址*/
        .factory('UpdateOrderAddress',['$resource',function ($resource){
            return $resource(transaction + 'updateOrderAddress');
        }])

});
