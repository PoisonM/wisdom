angular.module('controllers',[]).controller('drawCardRecordsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Consumes','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Consumes,Global) {
            $rootScope.title = "划卡记录";
           $scope.goDrawCardRecordsDetail=function (flowNo) {
               $state.go("drawCardRecordsDetail",{flowNo:flowNo})
           }
            $scope.userConsumeRequest = {
                consumeType:'1',
                goodType:"5",
                pageSize:1000,
                shopUserId:"11"
            }
            Consumes.save($scope.userConsumeRequest,function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.recordCashier =data.responseData
                }
            })
        }]);