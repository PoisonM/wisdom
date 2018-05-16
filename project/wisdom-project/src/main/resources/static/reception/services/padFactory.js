var beautyIP = ' http://192.168.1.117/beauty/';
var userIP = ' http://192.168.1.117/user/';
var appointmentInfo = "http://localhost:9051/appointmentInfo/";
PADWeb.factory('httpInterceptor', ["$q", "$injector", function($q) {
        return {
            request: function(config) {
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
            responseError: function(err) {}
        };
    }])
    /**
     * 
     */
    //验证码
    .factory('GetUserValidateCode', ['$resource', function($resource) {
        return $resource(userIP + 'getUserValidateCode')
    }])
    //登录
    .factory('ClerkLogin', ['$resource', function($resource) {
        return $resource(userIP + 'clerkLogin')
    }])
    //获取用户二维码
    .factory('getBeautyQRCode', ['$resource', function($resource) {
        return $resource('http://mx99test2.kpbeauty.com.cn/weixin/beauty/getBeautyQRCode')
    }])
    //http轮询
    .factory('getUserScanInfo', ['$resource', function($resource) {
        return $resource(beautyIP + 'shop/getUserScanInfo')
    }])

    .factory('ShopDayAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(appointmentInfo + 'shopDayAppointmentInfoByDate')
    }])
    .factory('Archives', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/:userId', { userId: '@id' })
    }])
    //待领取汇总
    .factory('GetProductRecord', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/getProductRecord')
    }])
    //领取记录
    .factory('GetWaitReceiveDetail', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/getWaitReceiveDetail')
    }])
    //店员相关的记录统计
    .factory('Consumes', ['$resource', function($resource) {
        return $resource(beautyIP + 'mine/consumes')
    }])
    //收银记录统计
    .factory('cashConsumes', ['$resource', function($resource) {
        return $resource(beautyIP + 'consumes')
    }])
    //收银记录统计明细
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
    //个人中心用户信息
    .factory('ClerkInfo', ['$resource', function($resource) {
        return $resource(userIP + 'clerkInfo/:clerkId', { clerkId: '@id' })
    }])
    //更新个人中心用户信息
    .factory('UpateClerkInfo', ['$resource', function($resource) {
        return $resource(userIP + 'upateClerkInfo')
    }])
    //查询某用户档案信息
    .factory('GetShopUserArchivesInfoByUserId', ['$resource', function($resource) {
        return $resource(beautyIP + 'archives/getShopUserArchivesInfoByUserId')
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
    //查询用户充值卡的总金额  http://192.168.1.117:9051/cardInfo/getUserRechargeSumAmount
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
        return $resource(beautyIP + '/cardInfo/userRechargeConfirm')
    }])
    //充值卡充值签字确认查询接口
    .factory('SearchRechargeConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + 'cardInfo/searchRechargeConfirm')
    }])
    //充值卡充值签字确认接口
    .factory('RechargeCardSignConfirm', ['$resource', function($resource) {
        return $resource(beautyIP + '/cardInfo/rechargeCardSignConfirm')
    }])
    //图片上传
    .factory('ImageUploadToOSS', ['$resource', function($resource) {
        return $resource('http://192.168.1.117/system/file/imageUploadToOSS')
    }])
    //全量更新用户的订单
    .factory('UpdateShopUserOrderInfo', ['$resource', function($resource) {
        return $resource(beautyIP + '/orderInfo/updateShopUserOrderInfo')
    }])
    //用户支付接口
    .factory('UserPayOpe', ['$resource', function($resource) {
        return $resource(beautyIP + '/userPay/userPayOpe')
    }])

;