angular.module('controllers',[]).controller('productDtailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "产品详情";
            $scope.param={
                flag:true
            }
            $scope.chooseTab = function (type) {
                if(type =="0"){
                    $scope.param.flag = true
                }else{
                    $scope.param.flag = false;
                }
            }

        }])
