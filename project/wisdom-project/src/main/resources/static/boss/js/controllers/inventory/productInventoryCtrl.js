angular.module('controllers',[]).controller('productInventoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择更多产品盘点";
            $scope.successfulInventoryGo = function(){
                $state.go('successfulInventory');
            }

        }])