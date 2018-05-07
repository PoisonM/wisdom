/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addrechargeCardCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "添加充值卡";
            $scope.param={
                showSingle:false
            };
            $scope.single=function () {
                console.log(1)
               $scope.param.showSingle=true;
            }

        }]);