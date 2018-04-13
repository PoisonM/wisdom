angular.module('controllers',[]).controller('specialProductListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBusinessProductInfo','Global',
        'GetSpecialProductList','$ionicLoading','LoginGlobal','BusinessUtil','GetSpecialShopInfo',
        function ($scope,$rootScope,$stateParams,$state,GetBusinessProductInfo,Global,
                  GetSpecialProductList,$ionicLoading,LoginGlobal,BusinessUtil,GetSpecialShopInfo) {

            $rootScope.title = "美享99特价专区";

            $scope.param = {
                specialProductList : [],
                specialShopId : $stateParams.specialShopId,
                specialShopInfo : {}
            };

            $scope.enterSpecialProductDetails = function(item2){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ADJ,item2);
                $state.go("offlineProductDetail",{productId:item2})
            }

            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });

                GetSpecialShopInfo.get({specialShopId:$scope.param.specialShopId},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.specialShopInfo = data.responseData;
                        console.log($scope.param.specialShopInfo);
                    }
                });

                GetSpecialProductList.save({pageNo:0,pageSize:100},function(data){
                    $ionicLoading.hide();
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.specialProductList = data.responseData;
                    }
                })
            });

}])