/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('comprehensiveCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetExpenditureAndIncome','BossUtil',
        function ($scope,$rootScope,$stateParams,$state,GetExpenditureAndIncome,BossUtil) {

            $rootScope.title = "综合分析";

            GetExpenditureAndIncome.get({sysShopId:$rootScope.shopInfo.shopId,
                startTime:BossUtil.getNowFormatDate(),endTime:BossUtil.getAddDate(BossUtil.getNowFormatDate(),1)},function (data) {
                console.log(data);
            })

        }]);