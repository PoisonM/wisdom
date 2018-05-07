/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('reminderSettingsCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "提醒设置";

        }]);