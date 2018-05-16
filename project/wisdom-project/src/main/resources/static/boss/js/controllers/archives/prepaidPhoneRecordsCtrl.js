angular.module('controllers',[]).controller('prepaidPhoneRecordsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserConsumeByFlowId',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserConsumeByFlowId) {
            $rootScope.title = "充值记录";
            $scope.accountDetailsGo=function () {
                $state.go("accountDetails")
            }
            GetUserConsumeByFlowId.get({
                consumeType:"1",
                flowId:"3dcd258cd2ab464b81f32e072e6bee62"
            },function(data){

            })
        }]);