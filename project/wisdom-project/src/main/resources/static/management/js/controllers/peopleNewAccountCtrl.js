angular.module('controllers',[]).controller('peopleNewAccountCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {
            $scope.peopleTrue = function(){
                $state.go("peopleAccounts")
            };
            console.log($stateParams.test);
           $scope.jurisdictionName = $stateParams.test;
        }]);