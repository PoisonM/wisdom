var IP = ' http://192.168.1.117/beauty/';
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
    //查询某用户档案信息
    .factory('GetShopUserArchivesInfoByUserId', ['$resource', function($resource) {
        return $resource(IP + 'archives/getShopUserArchivesInfoByUserId')
    }])





;