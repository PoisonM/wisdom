/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('myselfCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "我的";

        }]);