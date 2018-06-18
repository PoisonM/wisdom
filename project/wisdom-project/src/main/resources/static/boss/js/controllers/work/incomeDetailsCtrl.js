/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('incomeDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetIncomeExpenditureAnalysisDetailList','$ionicLoading','Global',
        function ($scope,$rootScope,$stateParams,$state,GetIncomeExpenditureAnalysisDetailList,$ionicLoading,Global) {

            $rootScope.title = "收入明细";
            $scope.param={
                pageSize:2,
                flag:false
            };
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetIncomeExpenditureAnalysisDetailList.get({sysShopId:11,pageSize:$scope.param.pageSize},function (data) {
                    if(data.result==Global.SUCCESS&&data.responseData!=null) {
                        $ionicLoading.hide();
                        $scope.incomeDetails=data.responseData;
                        $scope.param.flag=false;
                        if(data.responseData.length<=0){
                            $scope.param.flag=true;
                        }
                    }else {
                        $ionicLoading.hide();
                        $scope.param.flag=true;
                    }
                })
            })

        }]);