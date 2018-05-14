angular.module('controllers',[]).controller('inventoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "盘点";
            /*下一步*/
            $scope.inventoryOverGo = function(){
                $state.go('inventoryOver')
            }
            /*更多*/
            $scope.AddOutboundGo = function(){
                $state.go('AddOutbound')
            }
            /*盘点记录*/
            $scope.inventoryRecordsGo = function(){
                $state.go('inventoryRecords')
            }



        }])