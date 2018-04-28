var IP = ' http://192.168.1.117/beauty/';
var cashierIP = 'http://192.168.1.117/'
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
    //
    .factory('ShopDayAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(appointmentInfo + 'shopDayAppointmentInfoByDate')
    }])
    .factory('Archives', ['$resource', function($resource) {
        return $resource(IP + 'archives/:userId', { userId: '@id' })
    }])
    //待领取汇总
    .factory('GetProductRecord', ['$resource', function($resource) {
        return $resource(IP + 'mine/getProductRecord')
    }])
    //领取记录
    .factory('GetWaitReceiveDetail', ['$resource', function($resource) {
        return $resource(IP + 'mine/getWaitReceiveDetail')
    }])
    //店员相关的记录统计
    .factory('Consumes', ['$resource', function($resource) {
        return $resource(IP + 'mine/consumes')
    }])
    //获取档案列表
    .factory('FindArchives', ['$resource', function($resource) {
        return $resource(IP + 'archives/findArchives')
    }])
    //添加档案
    .factory('SaveArchiveInfo', ['$resource', function($resource) {
        return $resource(IP + 'archives/saveArchiveInfo')
    }])
    //删除档案信息
    .factory('DeleteArchiveInfo', ['$resource', function($resource) {
        return $resource(IP + 'archives/deleteArchiveInfo')
    }])
    //个人中心用户信息
    .factory('ClerkInfo', ['$resource', function($resource) {
        return $resource('http://192.168.1.117/user/clerkInfo/:clerkId', { clerkId: '@id' })
    }])
    //查询某用户档案信息
    .factory('GetShopUserArchivesInfoByUserId', ['$resource', function($resource) {
        return $resource(IP + 'archives/getShopUserArchivesInfoByUserId')
    }])
    //个人中心获取今日业绩
    .factory('GetClerkAchievement', ['$resource', function($resource) {
        return $resource(IP + 'work/getClerkAchievement')
    }])
    //日预约请求接口
    .factory('ShopDayAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(IP + 'appointmentInfo/shopDayAppointmentInfoByDate')
    }])
    //根据预约主键查询预约项目
    .factory('GetUserCardProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserCardProjectList', { appointmentId: '@id' })
    }])
    //根据预约主键获取预约详情
    .factory('GetAppointmentInfoById', ['$resource', function($resource) {
        return $resource(IP + 'appointmentInfo/getAppointmentInfoById', { appointmentId: '@id' })
    }])

    //项目的接口
    .factory('OneLevelProject', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/oneLevelProject')
    }])
    .factory('TwoLevelProject', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/twoLevelProject')
    }])
    .factory('ThreeLevelProject', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/threeLevelProject')
    }])
    //项目的详情页面
    .factory('ProjectInfo', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/:id', { id: '@id' })
    }])

    /*产品接口*/
    .factory('OneLevelProduct', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/oneLevelProduct')
    }])
    .factory('TwoLevelProduct', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/twoLevelProduct')
    }])
    .factory('ThreeLevelProduct', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/threeLevelProduct')
    }])
    .factory('ProductInfo', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/:productId', { productId: '@productId' })
    }])

    /*套卡接口*/
    .factory('GetShopProjectGroups', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getShopProjectGroups')
    }])
    .factory('Detail', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getShopProjectGroup/detail')
    }])
    /*充值卡接口*/
    .factory('GetRechargeCardList', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getRechargeCardList')
    }])
    .factory('GetCardInfo', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/:id', { id: '@id' })
    }])
    //查询某个用户的产品信息
    .factory('GetUserProductList', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/getUserProductList')
    }])
    //查询某个用户的套卡信息
    .factory('GetUserProjectGroupList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserProjectGroupList',{sysUserId:"@id"})
    }])
    //查询某个用户的单次卡信息
    .factory('GetUserCourseProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserCourseProjectList')
    }])
    //查询某个店的疗程卡、单次卡列表信息
    .factory('SearchShopProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/searchShopProjectList')
    }])
    //查询某个店的产品信息
    .factory('SearchShopProductList', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/searchShopProductList')
    }])
    //获取套卡列表
    .factory('GetShopProjectGroups', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getShopProjectGroups')
    }])
    //获取充值卡列表
    .factory('GetRechargeCardList', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getRechargeCardList')
    }])
    //获取三级级项目列表
    .factory('ThreeLevelProject', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/threeLevelProject')
    }])
    //获取产品三级列表
    .factory('productInfoThreeLevelProject', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/threeLevelProduct')
    }])
    //根据用户与项目的关系主键列表查询用户与项目的关系  俗称划卡
    .factory('GetUserShopProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserShopProjectList',{relationIds:"@id"})
    }])
    //保存 划卡
    .factory('ConsumeCourseCard', ['$resource', function($resource) {
        return $resource(IP + 'consumes/consumeCourseCard')
    }])
    //获取店员信息
    .factory('GetShopClerkList', ['$resource', function($resource) {
        return $resource(IP + 'appointmentInfo/getShopClerkList')
    }])
    //取消预约
    .factory('UpdateAppointmentInfoById', ['$resource', function($resource) {
        return $resource(IP + 'appointmentInfo/updateAppointmentInfoById')
    }])
    //查询某个店的项目列表
    .factory('GetShopProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getShopProjectList')
    }])
    //查询某个店的项目列表
    .factory('ShopWeekAppointmentInfoByDate', ['$resource', function($resource) {
        return $resource(IP + 'appointmentInfo/shopWeekAppointmentInfoByDate')
    }])
    //查询美容师的排班信息
    .factory('GetShopClerkScheduleList', ['$resource', function($resource) {
        return $resource(IP + 'clerkSchedule/getShopClerkScheduleList')
    }])



    //查询某个用户的疗程卡、单次卡信息
    .factory('GetUserCourseProjectList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserCourseProjectList')
    }])
    //查询用户的套卡信息
    .factory('GetUserProjectGroupList', ['$resource', function($resource) {
        return $resource(IP + 'projectInfo/getUserProjectGroupList')
    }])

    //用户充值卡列表
    .factory('GetUserRechargeCardList', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getUserRechargeCardList')
    }])
    //用户产品列表  http://localhost:9051/productInfo/getUserProductList?sysUserId=1&sysShopId=1
    .factory('GetUserProductList', ['$resource', function($resource) {
        return $resource(IP + 'productInfo/getUserProductList')
    }])
    //查询用户充值卡的总金额  http://192.168.1.117:9051/cardInfo/getUserRechargeSumAmount
    .factory('GetUserRechargeSumAmount', ['$resource', function($resource) {
        return $resource(IP + 'cardInfo/getUserRechargeSumAmount')
    }])








;