angular.module('controllers',[]).controller('withdrawCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','UpdateWithdrawById','ManagementUtil','QueryWithdrawsByParameters','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,UpdateWithdrawById,ManagementUtil,QueryWithdrawsByParameters,ManagementUtil) {
            var applyStartTime = document.querySelector(".applyStartTime");
            var applyEndTime = document.querySelector(".applyEndTime");
            var updateStartTime = document.querySelector(".updateStartTime");
            var updateEndTime = document.querySelector(".updateEndTime");
            $scope.counnt ="";
            $scope.mum = true;
            $scope.isdisabled = false;
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
/*日期插件*/
            $scope.dataS =  function(id){
                !function(id){
                    laydate.skin('danlan');//切换皮肤，请查看skins下面皮肤库
                    laydate({elem: id});//绑定元素
                }();

                //日期范围限制
                var start = {
                    elem: '#start',
                    format: 'YYYY-MM-DD',
                    min: laydate.now(), //设定最小日期为当前日期
                    max: '2099-06-16', //最大日期
                    istime: true,
                    istoday: false,
                    choose: function(datas){
                        end.min = datas; //开始日选好后，重置结束日的最小日期
                        end.start = datas //将结束日的初始值设定为开始日
                    }
                };

                var end = {
                    elem: '#end',
                    format: 'YYYY-MM-DD',
                    min: laydate.now(),
                    max: '2099-06-16',
                    istime: true,
                    istoday: false,
                    choose: function(datas){
                        start.max = datas; //结束日选好后，充值开始日的最大日期
                    }
                };
                laydate(start);
                laydate(end);
            }



/*sysUserId：账号
* startTime：提现的开始时间
* endTime：结束时间
* 0:没有时间
* 1：申请时间
* 2：提现时间
* */
            $scope.statusGet=function(){
                $scope.applyTime =applyStartTime.value+applyEndTime.value
                $scope.updateTime = updateStartTime.value+updateEndTime.value
                if($scope.applyTime !="" && $scope.updateTime!=""){
                    $scope.startTime =applyStartTime.value;
                    $scope.endTime = applyEndTime.value;
                    $scope.ype = "1";
                }else if($scope.applyTime !=""){
                    $scope.startTime =applyStartTime.value;
                    $scope.endTime = applyEndTime.value;
                    $scope.type = "1";

                }else if($scope.updateTime !=""){
                    $scope.startTime =updateStartTime.value;
                    $scope.endTime = updateEndTime.value;
                    $scope.type = "2"
                }else{
                    $scope.startTime = "";
                    $scope.endTime = "";
                    $scope.type = "0"
                }
            };

                $scope.loadPageList = function(){
                    $scope.statusGet();
                    var PageParamVoDTO= {
                        startTime:$scope.startTime,
                        endTime:$scope.endTime,
                        param:$scope.counnt,
                        timeType:$scope.type,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        isExportExcel:"N"
                    };
                    QueryWithdrawsByParameters.save(PageParamVoDTO,function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result == Global.SUCCESS){
                            if( data.responseData.totalCount ==0){
                                alert("未查出相应结果");
                            }
                             $scope.copy(data);
                            $scope.mum = false;

                              /* updateStartTime.value= "";
                               updateEndTime.value= "";
                               applyStartTime.value= '';
                               applyEndTime.value= '';
                               $scope.counnt ="";*/
                        }else{
                            $scope.counnt='请填写手机号或身份证号'
                        }
                    })
                };
                $scope.searchWithdraw = function(){
                    $scope.choosePage(1)
                }


/*更改状态*/
/*id:id
* status:状态
* 0：申请中
* 1：提现成功
* 2：拒绝
* */
            $scope.flag = false;
            $scope.sendPrice = function(status,withdrawId,moneyAmount,id,sysUserId,moneyAmount){                                 if(status != "申请中")return;
                    $scope.status=status;
                    $scope.moneyAmount=moneyAmount;
                    $scope.withdrawId=withdrawId;
                    $scope.id = id;
                    $scope.flag = !$scope.flag;
                    $scope.sysUserId = sysUserId;
                    $scope.moneyAmount = moneyAmount;
            };
            $scope.changeWithdraw = function(status){
                $scope.isdisabled = true;
                if(status == "2"){
                    var result = confirm("是否确认拒绝！")
                    if(!result){
                           $scope.isdisabled = false;
                           return;
                    }
                }else{
                    var result = confirm("是否确认同意！")
                    if(!result){
                        $scope.isdisabled = false;
                        return;
                    }
                }

                var  withDrawRecordDTO={
                        withdrawId:$scope.id,
                        status:status,
                        sysUserId:$scope.sysUserId,
                        moneyAmount:$scope.moneyAmount
                };
                UpdateWithdrawById.save(withDrawRecordDTO,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        alert(data.errorInfo);
                        $scope.flag = false;
                        $scope.loadPageList();
                    }else{
                        alert(data.errorInfo);
                        $scope.flag = false;
                    }
                })
            };
            $scope.bgAll=function(){
                $scope.flag = false;
            };
/*状态的转换*/
            $scope.copy = function(data){
                if(data.responseData.totalCount == 0){
                      $scope.withdraw =[];
                      return
                  }
                if(data.responseData.responseData !=undefined){
                    for(var i=0;i<data.responseData.responseData.length;i++){
                        if(data.responseData.responseData[i].status == "0"){
                            data.responseData.responseData[i].status = "申请中"
                        }else if(data.responseData.responseData[i].status == "1"){
                            data.responseData.responseData[i].status = "完成"
                        }else if(data.responseData.responseData[i].status == "2"){
                            data.responseData.responseData[i].status = "拒绝"
                        }
                    }
                    $scope.withdraw = data.responseData.responseData;
                    for(var i=0;i<$scope.withdraw.length;i++){
                        $scope.withdraw[i].userType = $scope.withdraw[i].userType.substring(9,10)+"级";
                    }
                    $scope.response = {};
                    $scope.response.count = data.responseData.totalCount;
                    $scope.pageSize = 5;
                    $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                    $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;                         }

            };
 /*导出列表*/
            $scope.educeLis = function(){
                if (confirm("确认要导出？")) {
                    $scope.statusGet();
                    var PageParamVoDTO= {
                        startTime:$scope.startTime,
                        endTime:$scope.endTime,
                        param:$scope.counnt,
                        timeType:$scope.type,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        isExportExcel:"Y"
                    };
                    QueryWithdrawsByParameters.save(PageParamVoDTO,function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.errorInfo == Global.SUCCESS){
                                var $eleForm = $("<form method='get'></form>");
                                $eleForm.attr("action",data.result);
                                $(document.body).append($eleForm);
                                $eleForm.submit();
                                $scope.loadPageList();
                            }
                        })
                    }
            }
        }]);
