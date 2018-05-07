/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('comprehensiveCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "综合分析";

        }]);