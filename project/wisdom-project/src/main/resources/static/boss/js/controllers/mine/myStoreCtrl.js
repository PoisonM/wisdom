/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('myStoreCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "我的门店";
            $scope.branchShopGo=function () {
                $state.go("branchShop")
            };
             /*点击价目表跳转到相应页面*/
             $scope.goPrices=function () {
                 $state.go("prices")
             }
        }]);