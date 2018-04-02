angular.module('controllers',[]).controller('peopleCenterCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {
              $scope.back = function(){
                      window.history.back()
              }
        }]);