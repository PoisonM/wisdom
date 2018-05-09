
var user = '/user/';
var appointmentInfo = '/beauty/appointmentInfo/';
var work = '/beauty/work/';
var stock = '/beauty/stock/';
var consume = '/beauty/consume/';
var  earlyWarning =  '/beauty/earlyWarning/';
var  archives =  '/beauty/archives/';


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

/*综合分析*/
        /*根据时间查询某个美容院的耗卡和业绩*/
        .factory('GetExpenditureAndIncome',['$resource',function ($resource){
            return $resource(work + 'getExpenditureAndIncome')
        }])
        /*根据时间获取所有美容院的列表的业绩和卡耗*/
        .factory('GetBossExpenditureAndIncome',['$resource',function ($resource){
            return $resource(work + 'getBossExpenditureAndIncome')
        }])
        /*获取具体某个美容院的业绩和耗卡（包含来源分析）*/
        .factory('GetShopConsumeAndRecharge',['$resource',function ($resource){
            return $resource(work + 'getShopConsumeAndRecharge')
        }])
        /*业绩明细(boss端)*/
        .factory('GetBossPerformance',['$resource',function ($resource){
            return $resource(work + 'getBossPerformance')
        }])
        /*账户信息记录的详细信息*/
        .factory('ConsumeFlowNo',['$resource',function ($resource){
            return $resource(consume+"consumeFlowNo")
        }])
        /*获取当前美容院当前boss的当前家人列表*/
        .factory('GetFamilyList',['$resource',function ($resource){
            return $resource(work+"getFamilyList")
        }])



  /*工作首页*/
        /*获取人头数，人次数，新客，服务次数*/
        .factory('GetBossAchievement',['$resource',function ($resource){
            return $resource(work+"getBossAchievement")
        }])


   /*档案*/
        /*预警档案*/
        .factory('GetEarlyWarningList',['$resource',function ($resource){
            return $resource(earlyWarning+"getEarlyWarningList")
        }])
        /*用户档案详情*/
        .factory('Detail',['$resource',function ($resource){
            return $resource(archives+"detail/:id",{id:"@id"})
        }])

});
