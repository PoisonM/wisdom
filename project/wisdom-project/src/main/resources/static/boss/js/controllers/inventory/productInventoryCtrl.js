angular.module('controllers',[]).controller('productInventoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "选择更多产品出库";
            $scope.AddOutboundGo = function(){
                $state.go('AddOutbound',{stockStyle:$stateParams.stockStyle});
            }

        }])