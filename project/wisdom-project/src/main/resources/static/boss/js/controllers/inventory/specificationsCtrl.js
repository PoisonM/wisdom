angular.module('controllers',[]).controller('specificationsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "规格";

            $scope.param = {
                num:"",
                spec:""
            }
            if($stateParams.productSpec !=''){
                var num = $stateParams.productSpec.replace(/[^0-9]/ig,"");
                var spec = $stateParams.productSpec.replace(/[^a-z]+/ig,"");
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
                if($stateParams.type =='add'){
                    $state.go("addProduct",{id:$stateParams.id,spec:$scope.param.num+$scope.param.spec})
                }else{
                    $state.go("modifyProduct",{id:$stateParams.id,spec:$scope.param.num+$scope.param.spec})
                }

            }

        }])