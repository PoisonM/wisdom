angular.module('controllers',[]).controller('AddOutboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "新增出库";

            $scope.selectTheOutboundTypeGo = function(){
                $state.go("selectTheOutboundType")
            }
            $scope.selFamilyGo = function(){
                $state.go("addFamily")
            }
            $scope.productInventoryGo = function(){
                $state.go('productInventory')
            }
            $scope.successfulInventoryGo = function(){
                $state.go('successfulInventory')
            }

        }])
