/**
 * Created by Administrator on 2018/7/25.
 */
angular.module('controllers',[]).controller('newlywedsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetOfflineProductList','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetOfflineProductList,$ionicLoading) {

            $rootScope.title = "新人专享";
            $scope.param={
                productList:{}
            };
            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
            $scope.enterDetails=function(item){
                $state.go("offlineProductDetail",{productId:item})
            };
            $scope.PageParamDTO ={
                pageNo:0,
                pageSize:100,
                requestData:{
                    productPrefecture:1
                }
            };
            GetOfflineProductList.save($scope.PageParamDTO,function(data){
                $ionicLoading.hide();
                console.log(data);
                $scope.param.productList=data.responseData;
            })
            });
        }]);