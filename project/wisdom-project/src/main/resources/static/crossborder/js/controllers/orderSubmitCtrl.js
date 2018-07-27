angular.module('controllers',[]).controller('orderSubmitCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','oneLevelProject',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,oneLevelProject) {
            console.log("orderSubmitCtrl")

            $scope.option = {
                inportContent:"goodsList",
                method:oneLevelProject,
                dataList:""
            }

        }]);