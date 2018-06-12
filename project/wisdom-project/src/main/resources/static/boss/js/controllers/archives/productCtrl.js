angular.module('controllers',[]).controller('productCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserProductList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserProductList,Global) {
            $rootScope.title = "产品";
              $scope.goProductDetails=function (id) {
                  $state.go("productDtails",({flowId:id}))
              };
            GetUserProductList.get({sysShopId: $stateParams.sysShopId,sysUserId:$stateParams.sysUserId},function (data) {
                if(data.result==Global.SUCCESS&&data.responseData!=null) {
                    $scope.product = data.responseData;
                }
            })
        }]);
