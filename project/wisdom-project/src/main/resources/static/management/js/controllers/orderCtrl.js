angular.module('controllers',[]).controller('orderCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryAllBusinessOrders','QueryBusinessOrderByParameters','ExportExcelToOSS','ManagementUtil','$filter','InsertOrderCopRelation',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryAllBusinessOrders,QueryBusinessOrderByParameters,ExportExcelToOSS,ManagementUtil,$filter,InsertOrderCopRelation) {
            var orderPayStartTime =document.querySelector(".order .orderPayStartTime");
            var orderPayEndTime =document.querySelector(".order .orderPayEndTime");
            $scope.orderReference = "";
            $scope.orderUserAccount = "";
            $scope.id = '';
            var type = "0";
            /*$scope.lisFlag = false;*/
            $scope.mum = true;
            $scope.status = "";
            var pageTrue = true;

 /*日期插件*/
            laydate.render({
                elem: '#orderPayStartTime' //指定元素
           })
            laydate.render({
                elem: '#orderPayEndTime' //指定元素
            })
/*展示所有*/
/*订单状态，
0表示未支付；
1表示已支付，未收货；待发货
2表示已经支付，已收货;
 del表示订单已经删除；
 3表示货品放入了购物车中;
 4表示已经发货，但是用户没收到货 待收货*/
            $scope.statusGet = function(){
                $scope.orderComplete = orderPayStartTime.value+orderPayEndTime.value;
                if($scope.orderComplete !=""){
                    $scope.startTime = orderPayStartTime.value;
                    $scope.endTime = orderPayEndTime.value;
                    $scope.type = "1"
                }else{
                    $scope.startTime = "";
                    $scope.endTime = "";
                    $scope.type = "0"
                }
            }

            $scope.loadPageList = function(){

                   /* if($scope.status==""&&$scope.orderReference == ""&&$scope.orderUserAccount == ""&& $scope.orderComplete==""&&$scope.orderPay==""){
                         var pageParamVoDTO = {
                             pageNo:$scope.pageNo,
                             pageSize:$scope.pageSize
                         };
                        QueryAllBusinessOrders.save(pageParamVoDTO,function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.result == Global.SUCCESS){
                                theSame(data)
                                $scope.mum = false;
                            }else{
                                $scope.orderLis =[];
                            }
                        });
                    }else{*/
                        $timeout(function(){
                            if(window.location.hash.indexOf("true") != -1 && pageTrue == true ){
                                $scope.pageNo = $stateParams.pageNo;
                                $scope.status = $stateParams.status;
                                orderPayStartTime.value=$stateParams.startTime;
                                orderPayEndTime.value=$stateParams.endTime;
                                $scope.orderReference = $stateParams.orderReference;
                                $scope.orderUserAccount = $stateParams.orderUserAccount;
                                pageTrue = false;
                            }
                            $scope.statusGet();
                            $scope.PageParamVoDTO = {
                                pageNo:$scope.pageNo,
                                pageSize:$scope.pageSize,
                                startTime:$scope.startTime,
                                endTime:$scope.endTime,
                                timeType:$scope.type,
                                param:$scope.orderReference+$scope.orderUserAccount,
                                requestData:{
                                    status:$scope.status
                                },
                                isExportExcel:"N"
                            };
                            QueryBusinessOrderByParameters.save($scope.PageParamVoDTO,function(data){
                                ManagementUtil.checkResponseData(data,"");
                                if(data.result == Global.SUCCESS){
                                    if( data.responseData.totalCount ==0){
                                        alert("未查出相应结果");
                                    }
                                    theSame(data);
                                    $scope.mum = false;
                                }else{
                                    $scope.orderLis =[];
                                }
                            })
                        },10);

                 /*   }*/
                };



            var theSame = function(data){
                ManagementUtil.checkResponseData(data,"");
                    var orderLis = data.responseData.responseData;
                    if(data.responseData.responseData == undefined){
                        $scope.orderLis =[];
                        return
                    }
                    for(var i=0;i<orderLis.length;i++){
                        if(orderLis[i].status == "0"){
                            orderLis[i].status = "未付款"
                        }else if(orderLis[i].status == "1"){
                            orderLis[i].status = "待发货"
                        }else if(orderLis[i].status == "2"){
                            orderLis[i].status = "已完成"
                        }else if(orderLis[i].status == "3"){
                            orderLis[i].status = "物品在购物车中"
                        }else if(orderLis[i].status == "4"){
                            orderLis[i].status = "待收货"
                        }else if(orderLis[i].status == "del"){
                            orderLis[i].status = "订单已删除"                                                                           }
                    }
                    $scope.orderLis =orderLis;
                    $scope.orderLis = data.responseData.responseData;
                    $scope.counnt='';
                    $scope.response = {};
                    $scope.response.count = data.responseData.totalCount;
                    $scope.pageSize = 5;
                    $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                    $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
            };
/*未发货 确认收货....的搜索*/
            $scope.bgChangeAndSearch = function(type){
                orderPayStartTime.value="";
                orderPayEndTime.value='';
                $scope.orderReference = '';
                $scope.orderReference = '';
                $scope.active = 'active';
                $scope.status = type;
                $scope.loadPageList();
                $scope.choosePage(1)
            };
/*搜索*/
            $scope.searchOrder = function(){
                $scope.choosePage(1)
            };
 /*导出列表*/
            $scope.educeLis = function(){
                if (confirm("确认要导出？")) {
                    if($scope.status == "1"){
                        pageParamVoDTO ={};
                        ExportExcelToOSS.save(pageParamVoDTO,function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.errorInfo == Global.SUCCESS){
                                /*simulateBtn.href=data.result;
                                 simulateBtn.download = "导出列表"*/
                                var $eleForm = $("<form method='get'></form>");
                                $eleForm.attr("action",data.result);
                                $(document.body).append($eleForm);
                                $eleForm.submit();
                                $scope.loadPageList();
                            }
                        })
                    }else{
                        $scope.statusGet();
                        var PageParamVoDTO = {
                            pageNo:$scope.pageNo,
                            pageSize:$scope.pageSize,
                            startTime:$scope.startTime,
                            endTime:$scope.endTime,
                            timeType:$scope.type,
                            param:$scope.orderReference+$scope.orderUserAccount,
                            requestData:{
                                status:$scope.status
                            },
                            isExportExcel:"Y"
                        };
                        QueryBusinessOrderByParameters.save(PageParamVoDTO,function(data){
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
            };

 /*输入运单号*/
                      $scope.waybillNumFlag = false;
            $scope.orderCopRelationDTO = {
                orderId:"",
                waybillNumber:"",
                transactionId:""
            };
            $scope.waybillNum = function(businessOrderId,transactionId,num){
                $scope.waybillNumFlag=!$scope.waybillNumFlag;
                $scope.orderCopRelationDTO.orderId = businessOrderId;
                $scope.orderCopRelationDTO.transactionId = transactionId;
                $scope.orderCopRelationDTO.waybillNumber = num;
            };
            $scope.waybillNumSave = function(){
                if($scope.orderCopRelationDTO.waybillNumber ==""){alert("运单号不能为空");return};
                $scope.waybillNumFlag = false;
                InsertOrderCopRelation.save($scope.orderCopRelationDTO,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        $scope.orderCopRelationDTO.waybillNumber = "";
                        $scope.loadPageList();
                        alert(data.errorInfo)
                    }else{
                        alert(data.errorInfo)
                    }
                })

            };
            $scope.bgAll = function(){
                $scope.waybillNumFlag = false;
            }
        }]);

