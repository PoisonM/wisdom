angular.module('controllers',[]).controller('drawCardRecordsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "划卡记录";
           $scope.goDrawCardRecordsDetail=function () {
               $state.go("drawCardRecordsDetail")
           }
        }]);