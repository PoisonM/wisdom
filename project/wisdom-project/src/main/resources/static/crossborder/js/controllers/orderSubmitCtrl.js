angular.module('controllers',[]).controller('orderSubmitCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {
            console.log("orderSubmitCtrl")
            $scope.authentication_flag = false//弹窗
        }]);