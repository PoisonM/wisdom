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
            $scope.addBrandOneGo = function(){
                $state.go("addBrandOne")
            };
            $scope.checkProduct=function () {
                $state.go("addBrandOne")
            };
            $scope.checkSeries=function (productTypeOneId) {
                $state.go("addSeries",{productTypeOneId:productTypeOneId})
            }
            SearchShopProductList.get({filterStr:''},function (date) {
                $scope.productBrand = date.responseData.detailLevel

            })



        }]);