/**
 * Created by Administrator on 2018/3/1.
 */
angular.module('controllers',[]).controller('beansCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "我的享豆";

        }])
