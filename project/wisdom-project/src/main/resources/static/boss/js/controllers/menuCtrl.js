angular.module('controllers',[]).controller('menuCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ManagementUtil) {

            $scope.jurisdictionName ="";
            $scope.jurisdiction = function(index,name){
                   $scope.jurisdictionIndex = index;
                   $scope.jurisdictionName =  name

               };
            $scope.jurisdictionTureBtn =function(){
                   $state.go("peopleNewAccount",{test:$scope.jurisdictionName})
            }
        }]);