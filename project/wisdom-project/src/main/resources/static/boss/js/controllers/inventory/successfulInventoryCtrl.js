angular.module('controllers',[]).controller('successfulInventoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "成功入库";
            $scope.entryDetailsGo = function(){
                $state.go('entryDetails',{entryId:$stateParams.entryId});
            }
}])