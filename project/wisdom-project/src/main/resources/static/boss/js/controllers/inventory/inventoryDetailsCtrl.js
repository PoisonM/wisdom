angular.module('controllers',[]).controller('inventoryDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "仓库产品";

            $scope.productInventoryDetailsGo = function(){
                $state.go('productInventoryDetails')
            }

        }])
