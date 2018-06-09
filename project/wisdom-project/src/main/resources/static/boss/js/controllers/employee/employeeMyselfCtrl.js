/**
 * Created by Administrator on 2018/5/31.
 */

angular.module('controllers',[]).controller('employeeMyselfCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "我的";

            /*点击头像跳转*/
            $scope.modificationInformationGo=function () {
                $state.go("employeeInformation")
            };

            /*点击我的门店跳转*/
            $scope.myStoreGo=function () {
                $state.go("employeeMyStore")
            };

            /*点击设置*/
            $scope.setInformationGo=function () {
                $state.go("employeeSet")
            };
           
        }]);