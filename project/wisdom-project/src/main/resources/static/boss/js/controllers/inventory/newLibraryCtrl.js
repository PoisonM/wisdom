angular.module('controllers',[]).controller('newLibraryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "新增入库";
            $scope.successfulInventoryGo=function(){
                $state.go("successfulInventory")
            }
            $scope.productPutInStorageMoreGo = function () {
                $state.go("productPutInStorageMore")
            }

        }])
