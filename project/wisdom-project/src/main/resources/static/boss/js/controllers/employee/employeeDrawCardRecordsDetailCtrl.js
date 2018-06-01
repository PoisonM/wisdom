/**
 * Created by Administrator on 2018/6/1.
 */
angular.module('controllers',[]).controller('employeeDrawCardRecordsDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','ConsumeFlowNo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,ConsumeFlowNo) {
            $rootScope.title = "详情";
            ConsumeFlowNo.get({consumeFlowNo:"20180426160908422"},function (data) {
                $scope.drawCardRecordsDetail=data.responseData;
                console.log( $scope.drawCardRecordsDetail)
            })
        }]);