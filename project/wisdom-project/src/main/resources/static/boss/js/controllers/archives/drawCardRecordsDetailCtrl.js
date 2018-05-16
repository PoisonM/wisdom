angular.module('controllers',[]).controller('drawCardRecordsDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','ConsumeFlowNo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,ConsumeFlowNo) {
            $rootScope.title = "详情";
            ConsumeFlowNo.get({consumeFlowNo:"20180426160908422"},function (data) {
                $scope.drawCardRecordsDetail=data.responseData;
                console.log( $scope.drawCardRecordsDetail)
            })
        }]);