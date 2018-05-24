angular.module('controllers',[]).controller('chooseWarehouseCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetBossShopList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetBossShopList,Global) {
            $rootScope.title = "选择仓库";
            GetBossShopList.get({},function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null) {
                   $scope.chooseWarehouse = data.responseData;
                   console.log(data.responseData);
                }
            })

            $scope.funAreaGo = function(){
                $state.go("funArea")
            }
        }])