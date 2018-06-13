angular.module('controllers',[]).controller('refillCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserRechargeCardList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserRechargeCardList) {
            $rootScope.title = "基本信息";
            $scope.param={
                flag:false
            }
           $scope.prepaidPhoneRecordsGo=function (id,rechargeCardType) {
               if(rechargeCardType == '0'){
                   $state.go("accountDetails",{id:id,rechargeCardType:rechargeCardType})
               }else{
                   $state.go("detailsOfCashier",{id:id})
               }
           }
            GetUserRechargeCardList.get({
                sysUserId:$stateParams.sysUserId,
                sysShopId:$stateParams.sysShopId,
            },function(data) {
                $scope.refillCard = data.responseData
            })
            $scope.help = function(){
                $scope.param.flag=true;
            }
            $scope.disNone = function(){
                $scope.param.flag=false;
            }
        }]);