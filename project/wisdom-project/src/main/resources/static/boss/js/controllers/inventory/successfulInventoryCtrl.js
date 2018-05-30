angular.module('controllers',[]).controller('successfulInventoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {

            $scope.param = {
               boundType:$stateParams.type
            }

            if($stateParams.type=='inbound')
            {
                $rootScope.title = "成功入库";
            }
            else if($stateParams.type=='outbound')
            {
                $rootScope.title = "成功出库";
            }

            $scope.detailsGo = function(){
                if($stateParams.type=='inbound')
                {
                    $state.go('entryDetails',{entryId:$stateParams.entryId});
                }
                else if($stateParams.type=='outbound')
                {

                }
            }
}])