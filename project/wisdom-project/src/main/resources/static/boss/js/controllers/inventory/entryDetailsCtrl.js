angular.module('controllers',[]).controller('entryDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "入库单详情";

            $scope.modifyLibraryGo=function(){
                $state.go('modifyLibrary')
            }

        }])