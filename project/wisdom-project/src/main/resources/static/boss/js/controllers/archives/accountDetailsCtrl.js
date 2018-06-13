angular.module('controllers',[]).controller('accountDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetRechargeRecord','Global','BossUtil',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetRechargeRecord,Global,BossUtil) {
            $rootScope.title = "账户记录";
            GetRechargeRecord.get({
                flowId:$stateParams.id,
                pageSize:1000
            },function(data){
                BossUtil.checkResponseData(data,'accountDetails&'+$stateParams.id);
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.accountDetails =data.responseData
                }
            })
            $scope.detailsOfCashierGo = function (id,flowNo) {
                 $state.go("detailsOfCashier",{id:id,flowNo:flowNo,rechargeCardType:$stateParams.rechargeCardType})
            }
        }])