angular.module('controllers',[]).controller('partialFilesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "全院档案";
            $scope.searchCont = "";


        }])