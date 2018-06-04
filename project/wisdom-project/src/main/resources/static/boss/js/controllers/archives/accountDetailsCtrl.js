angular.module('controllers',[]).controller('accountDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserConsumeByFlowId','Global','BossUtil',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserConsumeByFlowId,Global,BossUtil) {
            $rootScope.title = "账户记录";
            GetUserConsumeByFlowId.get({
                flowId:$stateParams.id
            },function(data){
                BossUtil.checkResponseData(data,'accountDetails&'+$stateParams.id);
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.accountDetails =data.responseData
                }
            })
        }])