/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('modifyProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "修改项目";

        }]);