angular.module('controllers',[]).controller('chooseWarehouseCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择仓库";
            $scope.funAreaGo = function(){
                $state.go("funArea")
            }

        }])