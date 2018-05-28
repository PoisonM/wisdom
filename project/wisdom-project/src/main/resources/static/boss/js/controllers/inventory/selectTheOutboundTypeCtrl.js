angular.module('controllers',[]).controller('selectTheOutboundTypeCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择出库类型";
            $scope.AddOutboundGo = function(stockType){
                $rootScope.shopInfo.outShopStockType = stockType;
                $state.go("AddOutbound")
            }

        }])