/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('schedulingCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "排班";

        }]);