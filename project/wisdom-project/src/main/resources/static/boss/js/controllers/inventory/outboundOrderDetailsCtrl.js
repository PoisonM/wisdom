angular.module('controllers',[]).controller('outboundOrderDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "出库单详情";

        }])
