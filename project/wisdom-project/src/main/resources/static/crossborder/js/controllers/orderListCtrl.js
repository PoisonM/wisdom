angular.module('controllers',[]).controller('orderListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','oneLevelProject',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,oneLevelProject) {
            console.log("orderList")
            $scope.option = {
                inportContent:"goodsList",
                method:oneLevelProject,
                dataList:""
            }

        }]);