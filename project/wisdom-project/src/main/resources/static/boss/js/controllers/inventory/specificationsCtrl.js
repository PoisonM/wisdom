angular.module('controllers',[]).controller('specificationsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "规格";

            $scope.param = {
                num:"",
                spec:""
            }
            if($rootScope.settingAddsome.product.productSpec !=''){
                var num = $rootScope.settingAddsome.product.productSpec.replace(/[^0-9]/ig,"");
                var spec = $rootScope.settingAddsome.product.productSpec.replace(/[^a-z]+/ig,"");
                $scope.param.num =num;
                $scope.param.spec =spec;
            }
            $scope.selSpec=function (type) {
                $scope.param.spec = type
            }
            $scope.num = function () {
                $scope.param.num = $scope.param.num.replace(/[^\d]/g,'')
            }
            $scope.save=function () {
                    $state.go($stateParams.url)
                    $rootScope.settingAddsome.product.productSpec = $scope.param.num+$scope.param.spec

            }

        }])