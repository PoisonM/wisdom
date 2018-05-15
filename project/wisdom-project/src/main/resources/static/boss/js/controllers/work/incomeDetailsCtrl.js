/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('incomeDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetIncomeExpenditureAnalysisDetailList',
        function ($scope,$rootScope,$stateParams,$state,GetIncomeExpenditureAnalysisDetailList) {

            $rootScope.title = "收入明细";
            $scope.param={
                pageSize:2
            };
            GetIncomeExpenditureAnalysisDetailList.get({sysShopId:11,pageSize:$scope.param.pageSize},function (data) {
                $scope.incomeDetails=data.responseData;
            })
        }]);