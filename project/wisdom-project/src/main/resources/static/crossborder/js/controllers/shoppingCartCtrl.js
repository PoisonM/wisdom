angular.module('controllers',[]).controller('shoppingCartCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','oneLevelProject',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,oneLevelProject) {
            console.log("shoppingCart")

            $scope.option = {
                inportContent:"goodsList",
                method:oneLevelProject,
                dataList:""
            }

        }]);