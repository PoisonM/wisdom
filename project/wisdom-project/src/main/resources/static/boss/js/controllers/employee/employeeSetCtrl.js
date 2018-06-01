/**
 * Created by Administrator on 2018/5/31.
 */

angular.module('controllers',[]).controller('employeeSetCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "设置";
            /*点击关于我们跳转到关于我们页面*/
            $scope.aboutMineGo=function () {
                $state.go("aboutMine")
            };
            /*点击退出登录*/
            $scope.exitLogon=function () {
                $state.go("login")
            }
        }]);