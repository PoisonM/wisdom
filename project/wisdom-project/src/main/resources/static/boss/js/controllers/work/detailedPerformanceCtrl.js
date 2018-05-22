/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('detailedPerformanceCtrl',
    ['$scope','$rootScope','$stateParams','$state',"GetBossPerformance","Global",
        function ($scope,$rootScope,$stateParams,$state,GetBossPerformance,Global) {

            $rootScope.title = "业绩明细";
            $scope.flag = false;
            $scope.userConsumeRequest = {
                sysShopId:$stateParams.shopId,
                pageSize:100

            };
            $scope.expenditureDetailsGo = function(flowNo){
                $state.go("details",{flowNo:flowNo})
            }
            $scope.sel = function(){
                $scope.flag = true
            }
            /*type 0 全部*/
            /*type 1 消费*/
            /*type 2 充值*/
            $scope.getInfo = function(type){
                if(type==0){
                    $scope.userConsumeRequest = {
                        sysShopId:$stateParams.shopId,
                        pageSize:100
                    };
                }else if(type==1){
                    $scope.userConsumeRequest = {
                        sysShopId:$stateParams.shopId,
                        pageSize:100,
                        consumeType:"0",
                        goodType:"6"

                    };
                }else if(type==2){
                    $scope.userConsumeRequest = {
                        sysShopId:$stateParams.shopId,
                        pageSize:100,
                        consumeType:"0",
                        goodType:"2"

                    };
                }
                GetBossPerformance.save($scope.userConsumeRequest,function(data){

                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $scope.detailedPerformance = data.responseData;
                        $scope.flag = false;

                    }

                })
            };

            GetBossPerformance.save($scope.userConsumeRequest,function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.detailedPerformance = data.responseData;

                }

            });




        }])