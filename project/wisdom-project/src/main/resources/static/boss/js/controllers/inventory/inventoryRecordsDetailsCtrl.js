angular.module('controllers',[]).controller('inventoryRecordsDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "盘点记录详情";

        }])