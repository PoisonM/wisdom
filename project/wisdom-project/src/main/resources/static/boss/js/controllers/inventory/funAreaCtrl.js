angular.module('controllers',[]).controller('funAreaCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "汉方美业";
            $scope.putInStorageGo = function(){
                $state.go('putInStorage')
            }
            $scope.outboundGo = function(){
                $state.go('outbound')
            }
            $scope.inventoryDetailsGo = function(){
                alert($rootScope.shopInfo.shopStoreId);
                $state.go('inventoryDetails',{shopStoreId:$rootScope.shopInfo.shopStoreId})
            }
            /*盘点*/
            $scope.inventoryGo = function(){
                $state.go('inventory')
            }
            /*设置*/
            $scope.inventorySettingGo = function(){
                $state.go('inventorySetting')
            }
 }]);