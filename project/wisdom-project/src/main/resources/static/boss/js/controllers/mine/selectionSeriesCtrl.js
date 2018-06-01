/**
 * Created by Administrator on 2018/5/4.
 */

angular.module('controllers',[]).controller('selectionSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','TwoLevelProduct','Global',
        function ($scope,$rootScope,$stateParams,$state,TwoLevelProduct,Global) {

            $rootScope.title = "选择系列";
            $scope.param ={
                id:$stateParams.id
            }
            TwoLevelProduct.get({
               id:$rootScope.settingAddsome.product.productTypeOneId
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.selectionSeries = data.responseData
                }
            })
            $scope.series = function (productTypeName,id) {
                $state.go($stateParams.url)
                $rootScope.settingAddsome.product.productTypeTwoName =productTypeName
                $rootScope.settingAddsome.product.productTypeTwoId =id

            }

        }]);
