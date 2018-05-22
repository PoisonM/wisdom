/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('setInformationCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "设置";
               /*点击基础资料设置*/
            $scope.basicSettingGo=function () {
                $state.go("basicSetting")
            };
            /*点击系统设置*/
            $scope.systemSetupGo=function () {
                $state.go("systemSetup")
            };

        }]);