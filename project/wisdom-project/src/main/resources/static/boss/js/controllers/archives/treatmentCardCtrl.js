angular.module('controllers',[]).controller('treatmentCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "疗程卡";
           $scope.goTreatmentCard=function () {
               console.log(1)
               $state.go("treatmentCardDtails")
           }
        }]);