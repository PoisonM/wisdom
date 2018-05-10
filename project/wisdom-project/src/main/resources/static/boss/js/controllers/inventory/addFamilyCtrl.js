angular.module('controllers',[]).controller('addFamilyCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "添加家人";
            $scope.param={
                index:0
            }

            $scope.selEmployees = function(index){
                $scope.param.index = index;
            }


            $scope.addEmployeesGo = function(){
                $state.go('addEmployees')
            }


        }])
