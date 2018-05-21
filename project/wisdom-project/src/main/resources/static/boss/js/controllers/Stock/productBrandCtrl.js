/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('productBrandCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveProductTypeInfo',
        function ($scope,$rootScope,$stateParams,$state,SaveProductTypeInfo) {

            $rootScope.title = "产品品牌";

            $scope.addSeriesGo = function(){
                $state.go("addSeries")
            };
            $scope.addBrandOneGo = function(){
                $state.go("productSetting");
              /*  SaveProductTypeInfo.get({productTypeName:"",status:""},function (data) {
                    console.log(data)
                })*/
            };
            $scope.checkProduct=function () {
                $state.go("productSetting")
            };
            $scope.checkSeries=function () {
                $state.go("addSeries")
            };

        }]);