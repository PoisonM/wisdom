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
            $scope.checkSeries=function (id) {
                $state.go("addSeries",{id:id})
            };
            $scope.checkProduct=function (type,id,productTypeName,status) {
                $state.go("productSetting",{type:type,id:id,productTypeName:productTypeName,status:status})
            };
            SearchShopProductList.get({filterStr:''},function (date) {
                $scope.productBrand = date.responseData.detailLevel

            })



        }]);