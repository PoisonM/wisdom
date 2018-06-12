angular.module('controllers',[]).controller('detailsOfCashierCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','ConsumeFlowNo','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,ConsumeFlowNo,Global) {
            $rootScope.title = "收银详情   ";
           /* ConsumeFlowNo.get({
                consumeFlowNo:$stateParams.flowNo
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.details = data.responseData;

                }
            });*/
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });

            })
        }]);
