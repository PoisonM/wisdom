var product ='/business/product/';
var withdraw ='/business/withdraw/';
var income ='/business/income/';
var account = '/business/account/';
var transaction = '/business/transaction/';


var user = '/user/';
var customer = '/user/customer/';

var file = '/system/file/';

var banner = '/system/banner/';

define(['appManagement'], function (app) {
    app
        .factory('UserLoginOut',['$resource',function ($resource){
            return $resource(user + 'loginOut');
        }])
        /*管理员登录*/
        .factory('ManagerLogin',['$resource',function ($resource){
            return $resource(user + 'managerLogin');
        }])

        /*上传图片*/
        .factory('ImageUploadToOSS',['$resource',function ($resource){
            return $resource(file + 'imageUploadToOSS');
        }])

        /*代理详情*/
        .factory('QueryUserBusinessById',['$resource',function ($resource){
            return $resource(customer + 'queryUserBusinessById');
        }])
        /*发展的*用户/*/
        .factory('QueryNextUserById',['$resource',function ($resource){
            return $resource(customer + 'queryNextUserById');
        }])
        .factory('QueryParentUserById',['$resource',function ($resource){
            return $resource(customer + 'queryParentUserById');
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
        .factory('QueryTrainingProductsByParameters',['$resource',function ($resource){
            return $resource(product + 'queryTrainingProductsByParameters');
        }])
        .factory('GetTrainingProductDetail',['$resource',function ($resource){
            return $resource(product + 'getTrainingProductDetail');
        }])
        .factory('UpdateTrainingProduct',['$resource',function ($resource){
            return $resource(product + 'updateTrainingProduct');
        }])
        .factory('AddTrainingProduct',['$resource',function ($resource){
            return $resource(product + 'addTrainingProduct');
        }])

        /*输入定单号*/
        .factory('InsertOrderCopRelation',['$resource',function ($resource){
            return $resource(transaction + 'insertOrderCopRelation');
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
        /*导出列表*/
        .factory('ExportExcelToOSS',['$resource',function ($resource){
            return $resource(transaction + 'exportExcelToOSS');
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
        .factory('GetIncomeRecordByPageParam',['$resource',function ($resource){
            return $resource(income + 'getIncomeRecordByPageParam');
        }])
        .factory('CheckIncomeRecordManagement',['$resource',function ($resource){
            return $resource(income + 'checkIncomeRecordManagement');
        }])
        .factory('SelectSelfMonthTransactionRecordByUserId',['$resource',function ($resource){
            return $resource(income + 'selectSelfMonthTransactionRecordByUserId');
        }])
        .factory('ExportExcelMonthTransactionRecordByUserId',['$resource',function ($resource){
            return $resource(income + 'exportExcelMonthTransactionRecordByUserId');
        }])
        .factory('SelectNextMonthTransactionRecordByUserId',['$resource',function ($resource){
            return $resource(income + 'selectNextMonthTransactionRecordByUserId');
        }])
        .factory('QueryIncomeInfoByIncomeId',['$resource',function ($resource){
            return $resource(income + 'queryIncomeInfoByIncomeId');
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
        /*分享奖励查看*/
        .factory('GetIncomeShareActivityInfoByIncomeId',['$resource',function ($resource){
            return $resource(income + 'getIncomeShareActivityInfoByIncomeId');
        }])
        /*审核*/
        .factory('UpdateIncomeRecordStatusById',['$resource',function ($resource){
            return $resource(income + 'updateIncomeRecordStatusById');
        }])
        .factory('ExportExcelIncomeRecord',['$resource',function ($resource){
            return $resource(income + 'exportExcelIncomeRecord');
        }])

        .factory('FindNextUserInfoControl',['$resource',function ($resource){
            return $resource(income + 'findNextUserInfoControl');
        }])

        .factory('ExportNextUserInfoControl',['$resource',function ($resource){
            return $resource(income + 'exportNextUserInfoControl');
        }])

        //手动生成月度
        .factory('MonthlyIncomeSignalMT',['$resource',function ($resource){
            return $resource(income + 'MonthlyIncomeSignalMT');
        }])

         //监听
        .factory('GetKey',['$resource',function ($resource){
            return $resource(income + 'getKey');
        }])

        //获取banner图列表
        .factory('GetHomeBannerList',['$resource',function ($resource){
            return $resource(banner + 'getHomeBannerList');
        }])

         //获取banner上移下移delHomeBannerById
        .factory('UpdateHomeBannerRank',['$resource',function ($resource){
            return $resource(banner + 'updateHomeBannerRank');
        }])

         //获取删除banner图
        .factory('DelHomeBannerById',['$resource',function ($resource){
            return $resource(banner + 'delHomeBannerById');
        }])

         //获取banner详细信息
        .factory('FindHomeBannerInfoById',['$resource',function ($resource){
            return $resource(banner + 'findHomeBannerInfoById');
        }])

        //新增banner
        .factory('AddHomeBanner',['$resource',function ($resource){
            return $resource(banner + 'addHomeBanner');
        }])

         //更新banner
        .factory('UpdateHomeBanner',['$resource',function ($resource){
            return $resource(banner + 'updateHomeBanner');
        }])

        //更新获取用户信息
        .factory('GetUserInfo',['$resource',function ($resource){
            return $resource(income + 'getUserInfo');
        }])
});
