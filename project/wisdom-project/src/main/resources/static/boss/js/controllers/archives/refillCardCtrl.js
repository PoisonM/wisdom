angular.module('controllers',[]).controller('refillCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserRechargeCardList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserRechargeCardList) {
            $rootScope.title = "基本信息";
            $scope.param={
                flag:false
            }
           $scope.prepaidPhoneRecordsGo=function (id) {
               $state.go("accountDetails",{id:id})
           }
            GetUserRechargeCardList.get({
                sysUserId:'11',/*$stateParams.sysUserId*/
                sysShopId:'11'/*$stateParams.sysShopId*/
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