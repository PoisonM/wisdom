/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('detailedPerformanceCtrl',
    ['$scope','$rootScope','$stateParams','$state',"GetBossPerformanceList","Global",'GetClerkPerformanceListClerk','$ionicScrollDelegate',
        function ($scope,$rootScope,$stateParams,$state,GetBossPerformanceList,Global,GetClerkPerformanceListClerk,$ionicScrollDelegate) {

            $rootScope.title = "业绩明细";
            $scope.flag = false;
            $scope.userConsumeRequest = {
                pageSize:1000,
                searchFile:$stateParams.searchFile
            };
            if ($stateParams.sysClerkId != "") {
                $scope.userConsumeRequest.sysClerkId = $stateParams.sysClerkId
                GetClerkPerformanceListClerk.get($scope.userConsumeRequest,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $scope.list = data.responseData;
                        $scope.detailedPerformance = data.responseData;
                    }

                })
            }
            if ($stateParams.sysShopId != "") {
                $scope.userConsumeRequest.sysShopId = $stateParams.sysShopId;
                GetBossPerformanceList.get($scope.userConsumeRequest,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $scope.list = data.responseData;
                        $scope.detailedPerformance = data.responseData;
                    }

                })
            }

            $scope.expenditureDetailsGo = function(flowNo){
                $state.go("details",{flowNo:flowNo})
            }
            $scope.sel = function(){
                $ionicScrollDelegate.$getByHandle('dashboard').scrollTop(false);
                $scope.flag = true;
            };
            $scope.all=function () {
                $scope.flag = false
            }
            /*type 0 全部*/
            /*type 1 消费*/
            /*type 2 充值*/
            $scope.getInfo = function(type){
                $scope.detailedPerformance=[]
                if(type!='-1'){
                    for( var i=0;i<$scope.list.length;i++){
                        if($scope.list[i].type==type){
                            $scope.detailedPerformance.push($scope.list[i])
                        }
                    }
                }else{
                    $scope.detailedPerformance=$scope.list
                }
                $scope.flag = false

            };

        }])