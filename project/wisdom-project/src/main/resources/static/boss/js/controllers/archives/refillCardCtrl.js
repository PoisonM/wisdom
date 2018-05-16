angular.module('controllers',[]).controller('refillCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserRechargeCardList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserRechargeCardList) {
            $rootScope.title = "基本信息";
            $scope.param={
                flag:false
            }
           $scope.prepaidPhoneRecordsGo=function () {
               $state.go("prepaidPhoneRecords")
           }
            GetUserRechargeCardList.get({
                sysUserId:'11',
                sysShopId:'11'
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