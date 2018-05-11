/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('myselfCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "我的";
            /*点击头像跳转*/
            $scope.modificationInformationGo=function () {
                $state.go("modificationInformation")
            };
            /*点击我的门店跳转*/
            $scope.myStoreGo=function () {
                $state.go("myStore")
            };
            /*点击设置*/
            $scope.setInformationGo=function () {
                $state.go("setInformation")
            };
        }]);