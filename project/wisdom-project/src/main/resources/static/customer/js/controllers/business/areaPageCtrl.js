/**
 * Created by Administrator on 2018/7/26.
 */
angular.module('controllers',[]).controller('areaPageCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {
            $scope.param={
                Horizontal:true
            };
            $scope.transformation=function () {
                $scope.param.Horizontal=!$scope.param.Horizontal
            };
        }]);