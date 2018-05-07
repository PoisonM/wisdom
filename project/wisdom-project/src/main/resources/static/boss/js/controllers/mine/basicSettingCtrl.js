/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('basicSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "基础资料设置";

        }]);