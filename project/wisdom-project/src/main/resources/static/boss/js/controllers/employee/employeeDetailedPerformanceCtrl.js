/**
 * Created by Administrator on 2018/6/15.
 */
angular.module('controllers',[]).controller('employeeDetailedPerformanceCtrl',
    ['$scope','$rootScope','$stateParams','$state',"GetBossPerformanceList","Global",'GetClerkPerformanceListClerk','$ionicScrollDelegate','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetBossPerformanceList,Global,GetClerkPerformanceListClerk,$ionicScrollDelegate,$ionicLoading) {

            $rootScope.title = "业绩明细";
            $scope.flag = false;/*点击筛选出现的数据div*/
            $scope.param={
                picFlag:false,/*空白页面的显示*/
                date:$stateParams.date
            };
            $scope.userConsumeRequest = {
                pageSize:1000,
                searchFile:$stateParams.searchFile
            };
            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetClerkPerformanceListClerk.get({pageSize:$scope.userConsumeRequest.pageSize,searchFile:$scope.userConsumeRequest.searchFile, startTime:$scope.param.date+" 00:00:00", endTime:$scope.param.date+" 23:59:59"},function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $ionicLoading.hide();
                        $scope.list = data.responseData;
                        $scope.detailedPerformance = data.responseData;
                        if(data.responseData.length<=0){
                            $scope.param.picFlag=true;
                        }
                    }else {
                        $ionicLoading.hide();
                        $scope.param.picFlag=true;
                    }

                })
            });


            $scope.expenditureDetailsGo = function(flowNo){
                $state.go("details",{flowNo:flowNo})
            };
            $scope.sel = function(){
                $ionicScrollDelegate.$getByHandle('dashboard').scrollTop(false);
                $scope.flag = true;
            };
            $scope.all=function () {
                $scope.flag = false
            };
            /*type 0 全部*/
            /*type 1 消费*/
            /*type 2 充值*/
            $scope.getInfo = function(type){
                $scope.detailedPerformance=[];
                if(type!='-1'){
                    for( var i=0;i<$scope.list.length;i++){
                        if($scope.list[i].type==type){
                            $scope.detailedPerformance.push($scope.list[i])
                        }
                    }
                    if( $scope.detailedPerformance.length<=0){
                        $scope.param.picFlag=true;
                        console.log(2);
                    }else {
                        $scope.param.picFlag=false;
                    }
                }else{
                    $scope.detailedPerformance=$scope.list
                }
                $scope.flag = false

            };

        }]);