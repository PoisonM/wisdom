angular.module('controllers',[]).controller('secKillProductCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','FindSeckillActivityList','ManagementUtil','ChangeSecKillActivityStatus',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,FindSeckillActivityList,ManagementUtil,ChangeSecKillActivityStatus) {
            $scope.BalanceAounnt = "";
            $scope.mum = true;
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.seckillActivityDTO = {
                activityNo:'',
                startTime:'',
                endTime:'',
                activityStatus:''
            };
            $scope.seckillActivityDataList = [];

            $scope.details = function(id){
                $state.go("seckillProductDetail",{id:id});
            }

            /*展示*/
            $scope.loadPageList = function(){
                $scope.PageParamVoDTO ={
                    pageNo:$scope.pageNo,
                    pageSize:$scope.pageSize,
                    requestData:{
                        activityNo: $("#activityNo").val(),
                        startTime:$("#startTime").val(),
                        endTime:$("#endTime").val(),
                        activityStatus: $("#activityStatus").val()
                    },
                    isExportExcel:"N"
                };
                FindSeckillActivityList.save(
                    $scope.PageParamVoDTO
                ,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        if( data.responseData.totalCount ==0){
                            alert("未查出相应结果");
                        }

                        $scope.seckillActivityDataList = data.responseData.responseData;
                        $scope.response = {
                        };
                        $scope.response.count = data.responseData.totalCount;
                        $scope.pageSize = 5;
                        $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                        $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                    }
                })
                $scope.mum = false
            }

            $scope.changeStatus = function(id,status){
                if(status==1){
                    var result = confirm("确定要删除活动吗")
                    if(result){
                        ChangeSecKillActivityStatus.get({
                           id:id,
                           status:status
                       },function(data){
                           ManagementUtil.checkResponseData(data,"");
                           if(data.result == Global.SUCCESS){
                               if(data.responseData=='success'){
                                    alert("撤销活动成功！");
                                    $state.reload('app.toMenu');
                               }else{
                                    alert("撤销活动失败！");
                                    $state.reload('app.toMenu');
                               }

                           }else{
                                alert("撤销活动失败！");
                                $state.reload('app.toMenu');
                           }
                       })
                    }
                }else{
                    var result = confirm("确定要启用活动吗")
                    if(result){
                        ChangeSecKillActivityStatus.get({
                           id:id,
                           status:status
                       },function(data){
                           ManagementUtil.checkResponseData(data,"");
                           if(data.result == Global.SUCCESS){
                               if(data.responseData=='success'){
                                    alert("启用活动成功！");
                                    $state.reload('app.toMenu');
                               }else{
                                    alert("启用活动失败！");
                                    $state.reload('app.toMenu');
                               }
                           }else{
                                alert("启用活动失败！");
                                $state.reload('app.toMenu');
                           }
                       })
                    }
                }
            }

            /*搜索*/
            $scope.searchBalance = function(){
                $scope.loadPageList();
                $scope.choosePage(1)
            }

            $scope.addSecKillActivity = function(){
                 $state.go("addSeckillProduct");
            }

        }]);