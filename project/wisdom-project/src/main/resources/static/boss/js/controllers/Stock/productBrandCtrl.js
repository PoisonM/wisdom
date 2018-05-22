/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('productBrandCtrl',
    ['$scope','$rootScope','$stateParams','$state','SearchShopProductList',
        function ($scope,$rootScope,$stateParams,$state,SearchShopProductList) {

            $rootScope.title = "产品品牌";

            $scope.addSeriesGo = function(){
                $state.go("addSeries")
            };
            $scope.checkSeries=function (productTypeOneId) {
                $state.go("addSeries",{productTypeOneId:productTypeOneId})
            };
            $scope.checkProduct=function (type,productTypeOneId,productTypeName,status) {
                $state.go("productSetting",{type:type,productTypeOneId:productTypeOneId,productTypeName:productTypeName,status:status})
            };
            SearchShopProductList.get({filterStr:''},function (date) {
                $scope.productBrand = date.responseData.detailLevel

            })



        }]);