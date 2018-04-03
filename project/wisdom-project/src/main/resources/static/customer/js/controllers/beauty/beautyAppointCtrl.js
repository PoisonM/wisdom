/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyAppointCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $scope.chooseProject=function () {
                alert("chooseProject");
            }

            $scope.chooseClerk=function () {
                alert("chooseClerk");
            }


        }])