/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('cancelDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "取消预约的详情";

        }]);