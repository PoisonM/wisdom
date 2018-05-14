/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('detailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','ConsumeFlowNo','Global',
        function ($scope,$rootScope,$stateParams,$state,ConsumeFlowNo,Global) {

            $rootScope.title = "详情";
            ConsumeFlowNo.get({
                consumeFlowNo:$stateParams.flowNo
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.details = data.responseData;

                }
            })

        }]);