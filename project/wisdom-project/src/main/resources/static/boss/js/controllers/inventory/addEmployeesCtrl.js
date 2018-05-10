angular.module('controllers',[]).controller('addEmployeesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "添加家人";
            $scope.param={
                flag:false,
            }
            $scope.shop = function(){
                $scope.param.flag = true;
            }

            $scope.selShop = function(){
                $scope.param.flag = false;

            }
            $scope.all = function(){
                $scope.param.flag = false;
            }
            $scope.save = function () {
                $state.go("addFamily")
            }
            $scope.importAddressBookGo=function () {
                $state.go("importAddressBook")
            }

        }])
