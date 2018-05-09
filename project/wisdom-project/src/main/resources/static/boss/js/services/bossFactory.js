
var user = '/user/';
var appointmentInfo = '/beauty/appointmentInfo/';
var work = '/beauty/work/'
var stock = '/beauty/stock/'

define(['appBoss'], function (app) {
    app

        .factory('GetUserValidateCode',['$resource',function ($resource){
            return $resource(user + 'getUserValidateCode')
        }])
       /*根据时间查询各店预约情况*/
        .factory('GetShopAppointmentNumberInfo',['$resource',function ($resource){
            return $resource(appointmentInfo + 'getShopAppointmentNumberInfo')
        }])
        /*根据时间查询某个店的预约详情*/
        .factory('ShopDayAppointmentInfoByDate',['$resource',function ($resource){
            return $resource(appointmentInfo + 'shopDayAppointmentInfoByDate')
        }])
       /*获取某个老板下面的某个店下的某个美容师的预约列表*/
        .factory('GetShopClerkAppointmentInfo',['$resource',function ($resource){
        return $resource(appointmentInfo + 'getShopClerkAppointmentInfo')
        }])
       /*根据预约状态获取某个老板下面的某个店的预约列表*/
        .factory('GetShopAppointmentInfoByStatus',['$resource',function ($resource){
            return $resource(appointmentInfo + 'getShopAppointmentInfoByStatus')
       }])
        /*根据预约主键获取预约详情*/
        .factory('GetAppointmentInfoById',['$resource',function ($resource){
            return $resource(appointmentInfo + 'getAppointmentInfoById')
        }])
        /*根据预约主键修改此次预约信息（修改预约状态等）*/
        .factory('UpdateAppointmentInfoById',['$resource',function ($resource){
            return $resource(appointmentInfo + 'updateAppointmentInfoById')
        }])
        /*入库出库记录*/
        .factory('ShopStockRecordList',['$resource',function ($resource){
            return $resource(stock + 'shopStockRecordList')
        }])


        /*根据时间查询某个美容院的耗卡和业绩*/
        .factory('GetExpenditureAndIncome',['$resource',function ($resource){
            return $resource(work + 'getExpenditureAndIncome')
        }])
        /*根据时间获取所有美容院的列表的业绩和卡耗*/
        .factory('GetBossExpenditureAndIncome',['$resource',function ($resource){
            return $resource(work + 'getBossExpenditureAndIncome')
        }])
        /*业绩明细(boss端)*/
        .factory('GetBossPerformance',['$resource',function ($resource){
            return $resource(work + 'getBossPerformance')
        }])


});
