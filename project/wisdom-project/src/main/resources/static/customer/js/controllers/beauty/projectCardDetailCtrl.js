/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('projectCardDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetProjectCardConsumeByFlowId','Global','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetProjectCardConsumeByFlowId,Global,$ionicLoading) {
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                $scope.param = {
                    pageSize: 5000,
                    projectCardConsumes: [],
                    projectCardDetailData: "true"
                };

                GetProjectCardConsumeByFlowId.get({flowId: $stateParams.projectId, consumeType: "1"}, function (data) {
                    $ionicLoading.hide();
                    if (data.result == Global.SUCCESS) {
                        if (data.responseData != null) {
                            $scope.param.projectCardConsumes = data.responseData;
                        } else {
                            $scope.param.projectCardDetailData = "false";
                        }
                    }
                })
            })                

}]);