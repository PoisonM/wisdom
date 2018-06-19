angular.module('controllers',[]).controller('funAreaCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "汉方美业";

            $scope.putInStorageGo = function(){
                $state.go('putInStorage',{name:$stateParams.name})
            }
            $scope.outboundGo = function(){
                $state.go('outbound',{name:$stateParams.name})
            }
            $scope.inventoryDetailsGo = function(){
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

            /*库管设置*/
            $scope.libraryTubeSettingGo = function(){
                $state.go('libraryTubeSetting',{shopStoreId:$rootScope.shopInfo.shopStoreId})
            }
 }]);