/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "添加项目";

        }]);