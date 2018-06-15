var beautyIP = '/beauty/';
var userIP = '/user/';
var systemService = '/system-service/';
var weixinService = '/weixin/';
var  mine='/beauty/mine/';
PADWeb.factory('httpInterceptor', ["$q", "$injector", function($q) {
        return {
            /*request: function(config) {
                config.headers = config.headers || {};
                if (localStorage.getItem("token") != undefined) {
                    config.headers['user_login_token'] = localStorage.getItem("token");
                }
                return config || $q.when(config);
            },
            requestError: function(err) {},
            response: function(res) {
                return res;
            },
            responseError: function(err) {}*/
            request: function(config){
                config.headers = config.headers || {};
                if(window.location.href.indexOf("pad-web")!=-1) {
                    config.headers.usertype = "beautyClerk";
                }

                if(window.localStorage.getItem("beautyUserLoginToken")!=undefined
                    &&window.localStorage.getItem("beautyUserLoginToken")!=null){
                    config.headers.beautyuserlogintoken = window.localStorage.getItem("beautyUserLoginToken");
                }

                if(window.localStorage.getItem("beautyBossLoginToken")!=undefined
                    &&window.localStorage.getItem("beautyBossLoginToken")!=null){
                    config.headers.beautybosslogintoken = window.localStorage.getItem("beautyBossLoginToken");
                }

                if(window.localStorage.getItem("beautyClerkLoginToken")!=undefined
                    &&window.localStorage.getItem("beautyClerkLoginToken")!=null){
                    config.headers.beautyclerklogintoken = window.localStorage.getItem("beautyClerkLoginToken");
                }

                return config;
            },

        };
    }])
    /**
     * 
     */
    //验证码
    .factory('GetUserValidateCode', ['$resource', function($resource) {
        return $resource(userIP + 'getUserValidateCode')
    }])
    /*//登录
    .factory('ClerkLogin', ['$resource', function($resource) {
        return $resource(userIP + 'clerkLogin')
    }])*/
    //登录
    .factory('BeautyLogin',['$resource',function ($resource){
        return $resource(userIP + 'beautyLogin')
    }])
    .factory('BeautyLoginOut',['$resource',function ($resource){
        return $resource(userIP + 'beautyLoginOut')
    }])
    //获取用户二维码
    .factory('getBeautyQRCode', ['$resource', function($resource) {
        return $resource(weixinService+'beauty/getBeautyQRCode')
    }])
    //http轮询
    .factory('getUserScanInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'shop/getUserScanInfo')
    }])
    .factory('Archives', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/:userId', { id: '@id' })
    }])
    //档案详情
    .factory('ArchivesDetail', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/detail/:id', { id: '@id' })
    }])
    //查询某用户档案信息
    .factory('GetShopUserArchivesInfoByUserId', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/getShopUserArchivesInfoByUserId')
    }])
    //待领取汇总
    .factory('GetProductRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/getProductRecord')
    }])
    //领取记录
    .factory('GetWaitReceiveDetail', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/getWaitReceiveDetail')
    }])
    //待领取记录汇总
    .factory('GetWaitReceivePeopleAndNumber', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/getWaitReceivePeopleAndNumber')
    }])
    //店员相关的记录统计
    .factory('Consumes', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/consumes')
    }])
    //收银记录统计
    .factory('cashConsumes', ['$resource', function($resource) {
        return $resource(beautyIP + 'consumes')
    }])
    //根据流水号查消费记录
    .factory('cashConsume', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/:consumeFlowNo', { consumeFlowNo: '@id' })
    }])
    //获取档案列表
    .factory('FindArchives', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/findArchives')
    }])
    //添加档案
    .factory('SaveArchiveInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/saveArchiveInfo')
    }])
    //删除档案信息
    .factory('DeleteArchiveInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/deleteArchiveInfo')
    }])
    .factory('GetCurrentLoginUserInfo',['$resource',function ($resource){
        return $resource(mine+"getCurrentLoginUserInfo")
    }])
    //个人中心用户信息
    .factory('ClerkInfo', ['$resource', function($resource) {
        return $resource(userIP + 'clerkInfo/:clerkId', { clerkId: '@id' })
    }])
    //更新个人中心用户信息
    .factory('UpdateClerkInfo', ['$resource', function($resource) {
        return $resource(userIP + 'updateClerkInfo')
    }])

    //个人中心获取今日业绩
    .factory('GetClerkAchievement', ['$resource', function($resource) {
        return $resource(beautyIP + 'work/getClerkAchievement')
    }])
    //日预约请求接口
    .factory('ShopDayAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/shopDayAppointmentInfoByDate')
    }])
    //根据预约主键查询预约项目
    .factory('GetUserCardProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserCardProjectList', { appointmentId: '@id' })
    }])
    //根据预约主键获取预约详情
    .factory('GetAppointmentInfoById', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/getAppointmentInfoById', { appointmentId: '@id' })
    }])
    //修改预约详情
    .factory('UpdateUserAppointInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/updateUserAppointInfo')
    }])

    //项目的接口
    .factory('OneLevelProject', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/oneLevelProject')
    }])
    .factory('TwoLevelProject', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/twoLevelProject')
    }])
    .factory('ThreeLevelProject', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/threeLevelProject')
    }])
    //项目的详情页面
    .factory('ProjectInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/:id', { id: '@id' })
    }])

    /*产品接口*/
    .factory('OneLevelProduct', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/oneLevelProduct')
    }])
    .factory('TwoLevelProduct', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/twoLevelProduct')
    }])
    .factory('ThreeLevelProduct', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/threeLevelProduct')
    }])
    .factory('ProductInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/:productId', { productId: '@productId' })
    }])

    /*套卡接口*/
    .factory('GetShopProjectGroups', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getShopProjectGroups')
    }])
    .factory('Detail', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getShopProjectGroup/detail')
    }])
    /*充值卡接口*/
    .factory('GetRechargeCardList', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getRechargeCardList')
    }])
    .factory('GetCardInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/:id', { id: '@id' })
    }])
    //查询某个用户的产品信息
    .factory('GetUserProductList', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/getUserProductList')
    }])
    //查询某个用户的套卡信息
    .factory('GetUserProjectGroupList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserProjectGroupList', { sysUserId: "@id" })
    }])
    //查询某个用户的单次卡信息
    .factory('GetUserCourseProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserCourseProjectList')
    }])
    //查询某个店的疗程卡、单次卡列表信息
    .factory('SearchShopProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/searchShopProjectList')
    }])
    //查询某个店的产品信息
    .factory('SearchShopProductList', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/searchShopProductList')
    }])
    //获取套卡列表
    .factory('GetShopProjectGroups', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getShopProjectGroups')
    }])
    //获取充值卡列表
    .factory('GetRechargeCardList', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getRechargeCardList')
    }])
    //获取三级级项目列表
    .factory('ThreeLevelProject', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/threeLevelProject')
    }])
    //获取产品三级列表
    .factory('productInfoThreeLevelProject', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/threeLevelProduct')
    }])
    //根据用户与项目的关系主键列表查询用户与项目的关系  俗称划卡
    .factory('GetUserShopProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserShopProjectList', { relationIds: "@id" })
    }])
    //保存 划卡
    .factory('ConsumeCourseCard', ['$resource', function($resource) {
        return $resource(beautyIP + 'consumes/consumeCourseCard')
    }])
    //获取店员信息
    .factory('GetShopClerkList', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/getShopClerkList')
    }])
    //取消预约
    .factory('UpdateAppointmentInfoById', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/updateAppointmentInfoById')
    }])
    //新建预约保存
    .factory('SaveUserAppointInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/saveUserAppointInfo')
    }])
    //查询某个店的项目列表
    .factory('GetShopProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getShopProjectList')
    }])
    //查询某个店的项目列表
    .factory('ShopWeekAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(beautyIP + 'appointmentInfo/shopWeekAppointmentInfoByDate')
    }])
    //查询美容师的排班信息
    .factory('GetShopClerkScheduleList', ['$resource', function($resource) {
        return $resource(beautyIP + 'clerkSchedule/getShopClerkScheduleList')
    }])
    //获取某个美容师排班信息
    .factory('GetClerkScheduleInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'clerkSchedule/getClerkScheduleInfo')
    }])

    //查询某个用户的疗程卡、单次卡信息
    .factory('GetUserCourseProjectList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserCourseProjectList')
    }])
    //查询用户的套卡信息
    .factory('GetUserProjectGroupList', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getUserProjectGroupList')
    }])

    //用户充值卡列表
    .factory('GetUserRechargeCardList', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getUserRechargeCardList')
    }])
    //用户产品列表  http://localhost:9051/productInfo/getUserProductList?sysUserId=1&sysShopId=1
    .factory('GetUserProductList', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/getUserProductList')
    }])
    //查询用户充值卡的总金额  http://47.100.246.201:9051/cardInfo/getUserRechargeSumAmount
    .factory('GetUserRechargeSumAmount', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/getUserRechargeSumAmount')
    }])
    //单独更新订单的产品信息/项目信息/套卡信息
    .factory('UpdateVirtualGoodsOrderInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/updateVirtualGoodsOrderInfo')
    }])
    //获取排班列表
    .factory('GetShopClerkScheduleList', ['$resource', function($resource) {
        return $resource(beautyIP + 'clerkSchedule/getShopClerkScheduleList')
    }])
    //保存用户订单
    .factory('SaveShopUserOrderInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/saveShopUserOrderInfo')
    }])

    //编辑排班列表
    .factory('UpdateShopClerkScheduleList', ['$resource', function($resource) {
        return $resource(beautyIP + 'clerkSchedule/updateShopClerkScheduleList')
    }])
    //查询最近的一笔订单信息
    .factory('GetShopUserRecentlyOrderInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/getShopUserRecentlyOrderInfo')
    }])
    //消费界面回显接口
    .factory('GetConsumeDisplayIds', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/getConsumeDisplayIds')
    }])
    //根据用户与项目的关系主键列表查询用户与项目的关系  俗称划卡
    .factory('CardInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/:id', { id: "@id" })
    }])
    //充值卡充值确认接口
    .factory('UserRechargeConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/userRechargeConfirm')
    }])
    //充值卡充值签字确认查询接口
    .factory('SearchRechargeConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/searchRechargeConfirm')
    }])
    //充值卡充值签字确认接口
    .factory('RechargeCardSignConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/rechargeCardSignConfirm')
    }])
    //图片上传
    .factory('ImageBase64UploadToOSS', ['$resource', function($resource) {
        return $resource(systemService+'file/imageBase64UploadToOSS')
    }])
    //普通图片上传
    .factory('imageUploadToOSS', ['$resource', function($resource) {
        return $resource(systemService+'imageUploadToOSS')
    }])
    //全量更新用户的订单
    .factory('UpdateShopUserOrderInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/updateShopUserOrderInfo')
    }])
    //用户支付接口
    .factory('UserPayOpe', ['$resource', function($resource) {
        return $resource(beautyIP + 'userPay/userPayOpe')
    }])
    //支付界面添加充值卡列表
    .factory('UpdateShopUserOrderPayInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'userPay/updateShopUserOrderPayInfo')
    }])
    //用户领取产品
    .factory('ConsumesUserProduct', ['$resource', function($resource) {
        return $resource(beautyIP + 'consumes/consumesUserProduct')
    }])
    //查询用户某个产品详情
    .factory('GetUserProductInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'productInfo/getUserProductInfo')
    }])
    //查询用户套卡下的子卡的详细信息
    .factory('GetShopUserProjectGroupRelRelationInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'projectInfo/getShopUserProjectGroupRelRelationInfo')
    }])
    //用户用户划套卡下的子卡操作
    .factory('ConsumesDaughterCard', ['$resource', function($resource) {
        return $resource(beautyIP + 'consumes/consumesDaughterCard')
    }])
    //划卡签字确认
    .factory('UpdateConsumeRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/updateConsumeRecord')
    }])
    //账户信息记录的详细信息
    .factory('ConsumeFlowNo', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/consumeFlowNo')
    }])
    //支付签字确认接口
    .factory('PaySignConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + 'userPay/paySignConfirm')
    }])
    //获取疗程和套卡的划卡记录
    .factory('TreatmentAndGroupCardRecordList', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/treatmentAndGroupCardRecordList')
    }])
    //疗程卡消费详情
    .factory('GetCureByflowId', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/flowId')
    }])
    //套卡消费详情
    .factory('GetCompleteByflowId', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/id')
    }])
    //获取用户产品的领取记录

    .factory('GetProductDrawRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/id')
    }])
    // http://localhost:9051/consume/getRechargeRecord
    .factory('GetRechargeRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/getRechargeRecord')
    }])
    //获取普通充值卡的订单详情产品的消费详情
    .factory('GetConsumeDetail', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/productAndRechargeCard/getConsumeDetail')
    }])
    //特殊充值卡的订单详情 http://47.100.102.37:1002/workspace/myWorkspace.do?projectId=6#24
    .factory('GetConsumeDetail', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/productAndRechargeCard/getConsumeDetail')
    }])
    //获取用户产品的领取记录  :9051/consume/getProductDrawRecord
    .factory('GetProductDrawRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'consume/getProductDrawRecord')
    }])
    //消费详情
    .factory('GetOrderConsumeDetailInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'orderInfo/getOrderConsumeDetailInfo')
    }])
;