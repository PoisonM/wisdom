/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('beautyBranchCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "美容院分店";
            $scope.param={
                current: "1"
            };
            $scope.setCurrent=function (param) {
                $scope.param.current=param

            };

        }]);