/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('incomeAnalysisCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "收支分析";

        }]);