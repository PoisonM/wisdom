angular.module('controllers',[]).controller('libraryTubeSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "库管设置";
            $scope.warehousePeopleGo = function () {
                $state.go('warehousePeople')
            }
            

        }])
