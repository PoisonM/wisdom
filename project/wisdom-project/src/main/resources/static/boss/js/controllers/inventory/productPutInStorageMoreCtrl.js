angular.module('controllers',[]).controller('productPutInStorageMoreCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择更多产品入库";

        }])