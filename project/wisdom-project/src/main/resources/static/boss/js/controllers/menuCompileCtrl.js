angular.module('controllers',[]).controller('menuCompileCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ManagementUtil) {
            $scope.back = function(){
                history.back()
            }
            $scope.addMenu = function(){
                $state.go("menu")
            }
        }])