/**
 * Created by Administrator on 2018/5/31.
 */
angular.module('controllers',[]).controller('employeeCanceledCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {
            $scope.confirmedGo = function(){
                $state.go("employeeConfirmed")
            };
            $scope.employeeReservation = function(){
                $state.go("employeeReservation")
            }

        }]);