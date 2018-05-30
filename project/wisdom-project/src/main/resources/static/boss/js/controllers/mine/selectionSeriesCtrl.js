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
               id:$stateParams.productTypeOneId
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.selectionSeries = data.responseData
                }
            })
            $scope.series = function (productTypeName,id) {
                if($stateParams.type=='add'){
                    $state.go('addProduct',{series:productTypeName,id:$scope.param.id,twoId:id})
                }else{
                    $state.go('modifyProduct',{series:productTypeName,id:$scope.param.id,twoId:id})
                }
            }

        }]);
