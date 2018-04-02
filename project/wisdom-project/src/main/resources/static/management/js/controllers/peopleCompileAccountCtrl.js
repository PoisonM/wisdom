angular.module('controllers',[]).controller('peopleCompileAccountCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {
            $scope.peopleTrue = function(){
                    $state.go("peopleAccounts")
            }

        }])