
var user = '/user/';
var beautyIP = ' http://192.168.1.117/beauty/';

define(['appBoss'], function (app) {
    app

        .factory('GetUserValidateCode',['$resource',function ($resource){
            return $resource(user + 'getUserValidateCode')
        }])
/*根据时间查询各店预约情况*/
        .factory('GetShopAppointmentNumberInfo',['$resource',function ($resource){
            return $resource(beautyIP + 'appointmentInfo/getShopAppointmentNumberInfo')
        }])
        /*根据时间查询某个店的预约详情*/
        .factory('ShopDayAppointmentInfoByDate',['$resource',function ($resource){
            return $resource(beautyIP + 'appointmentInfo/shopDayAppointmentInfoByDate')
        }])
    /*获取某个老板下面的某个店下的某个美容师的预约列表*/
        .factory('GetShopClerkAppointmentInfo',['$resource',function ($resource){
        return $resource(beautyIP + 'appointmentInfo/getShopClerkAppointmentInfo')
        }])
/*根据预约状态获取某个老板下面的某个店的预约列表*/
        .factory('GetShopAppointmentInfoByStatus',['$resource',function ($resource){
            return $resource(beautyIP + 'appointmentInfo/getShopAppointmentInfoByStatus')
       }])




});
