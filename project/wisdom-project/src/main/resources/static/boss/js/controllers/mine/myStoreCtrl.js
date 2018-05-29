/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('myStoreCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossAllShopList',
        function ($scope,$rootScope,$stateParams,$state,GetBossAllShopList) {

            $rootScope.title = "我的门店";
            $scope.branchShopGo=function (sysShopId) {
                $state.go("branchShop",{sysShopId:sysShopId})
            };
             /*点击价目表跳转到相应页面*/
             $scope.goPrices=function () {
                 $state.go("prices")
             };
            GetBossAllShopList.get(function (data) {
                $scope.shopList=data.responseData;/*根据type类型区分是美容院还是分店*/
            })
        }]);