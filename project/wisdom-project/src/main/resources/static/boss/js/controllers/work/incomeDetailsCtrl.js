/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('incomeDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "收入明细";

        }])