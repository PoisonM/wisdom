angular.module('controllers',[]).controller('productCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserProductList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserProductList) {
            $rootScope.title = "产品";
              $scope.goProductDetails=function () {
                  $state.go("productDtails")
              };
            GetUserProductList.get({sysShopId: $stateParams.sysShopId,sysUserId:$stateParams.sysUserId},function (data) {
                $scope.product=data.responseData;
            })
        }]);
