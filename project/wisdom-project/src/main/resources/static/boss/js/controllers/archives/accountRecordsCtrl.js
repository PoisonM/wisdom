angular.module('controllers',[]).controller('accountRecordsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "账户记录";
            $scope.goDrawCardRecords=function () {
                   $state.go("drawCardRecords")
               };
            $scope.recordCashierGo=function () {
                $state.go("recordCashier")
            }
        }]);
