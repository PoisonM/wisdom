/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('systemSetupCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "系统设置";
            $scope.clearSuccess=function () {
                alert("清除成功")
            }
        }]);