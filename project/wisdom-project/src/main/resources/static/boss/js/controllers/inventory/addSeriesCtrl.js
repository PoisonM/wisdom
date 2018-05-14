angular.module('controllers',[]).controller('addSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "添加系列";
            $scope.productBrandGo = function () {
                $state.go("productBrand")
            }

        }])