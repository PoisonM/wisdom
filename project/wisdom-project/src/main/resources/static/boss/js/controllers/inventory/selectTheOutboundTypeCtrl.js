angular.module('controllers',[]).controller('selectTheOutboundTypeCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择出库类型";

        }])