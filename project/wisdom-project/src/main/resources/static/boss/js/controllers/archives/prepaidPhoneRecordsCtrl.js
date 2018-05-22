angular.module('controllers',[]).controller('prepaidPhoneRecordsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "充值记录";
            $scope.accountDetailsGo=function () {
                $state.go("accountDetails")
            }
        }]);