/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('basicSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "基础资料设置";
            /*点击美容院设置*/
            $scope.beautySettingGo=function () {
                $state.go("beautySetting")
            };
            /*点击编辑分店*/
            $scope.shopListGo=function () {
                $state.go("shopList")
            }
             /*添加项目*/
            $scope.addProjectGo=function () {
                $state.go("addProject")
            }
            /*修改删除项目*/
            $scope.projectListGo=function () {
                $state.go("projectList")
            }
        }]);