angular.module('controllers',[]).controller('productCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "产品";
              $scope.goProductDetails=function () {
                  $state.go("productDtails")
              }
        }]);
