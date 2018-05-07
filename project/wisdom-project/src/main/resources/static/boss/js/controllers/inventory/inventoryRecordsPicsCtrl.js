angular.module('controllers',[]).controller('inventoryRecordsPicsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "入库记录";

        }])
