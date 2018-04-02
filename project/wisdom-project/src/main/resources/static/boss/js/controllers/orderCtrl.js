angular.module('controllers',[]).controller('orderCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryAllBusinessOrders','QueryBusinessOrderByParameters','ExportExcelToOSS','ManagementUtil','$filter',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryAllBusinessOrders,QueryBusinessOrderByParameters,ExportExcelToOSS,ManagementUtil,$filter) {
            var orderPayStartTime =document.querySelector(".order .orderPayStartTime");
            var orderPayEndTime =document.querySelector(".order .orderPayEndTime");
            var orderShipmentsStartTime =document.querySelector(".order .orderShipmentsStartTime");
            var orderShipmentsEndTime =document.querySelector(".order .orderShipmentsEndTime");
            $scope.orderReference = "";
            $scope.orderUserAccount = "";
            $scope.id = '';
            var type = "0";
            /*$scope.lisFlag = false;*/
            $scope.agencyIndex=0;
            $scope.mum = true;
            $scope.status = "";

 /*日期插件*/
            $scope.dataS =  function(id){
                !function(id){
                    laydate.skin('danlan');
                    laydate({elem: id});
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
            };

            // /*发货*/
            // $scope.shipments=function(state,id){
            //     $scope.loadPageList = function(){
            //         if(state!="未发货")return;
            //         $scope.id=id;
            //         var order = $scope.order[0].order;
            //         $scope.shipmentsFlag = !$scope.shipmentsFlag;
            //     }
            //
            // };
            // $scope.tracking=function(){
            //     var order = $scope.order[0].order;
            //     console.log($scope.id)
            //     for(var i=0;i<order.length;i++){
            //         if(order[i].id == $scope.id){
            //             order[i].state="待收货";
            //             console.log(order[i])
            //         }
            //     }
            //     $scope.shipmentsFlag=false;
            // };
            // /*退款*/
            // $scope.refund = function(state,id,index){
            //     if(state=="退货"||state=="退款"||state=="0")return;
            //     $scope.refundFlag =!$scope.refundFlag;
            //     $scope.refundIndex = index;
            //     console.log(index)
            //
            // };
            // $scope.refundLast = function(){
            //     $scope.loadPageList = function(){
            //         $scope.order[0].order[$scope.refundIndex].state = "退款";
            //
            //         $scope.refundFlag = false;
            //     }
            //     $scope.loadPageList()
            //
            // };
            // /*退货*/
            // $scope.salesReturnFlag = false;
            // $scope.showFlag = true;
            // $scope.hideFlag = false;
            // $scope.salesReturn = function(status,salesReturnName,salesReturnIndex){
            //     if(status == "2" || status == "4") {
            //         $scope.salesReturnFlag = !$scope.salesReturnFlag;
            //
            //     }else if(status == "退货中"){
            //         $scope.refundFlag =!$scope.refundFlag
            //     }else{
            //         return
            //     }
            //     console.log($scope.salesReturnFlag);
            //     $scope.salesReturnName = salesReturnName;
            //     $scope.salesReturnIndex = salesReturnIndex;
            //     $scope.showFlag = false;
            //     $scope.hideFlag = true;
            //
            // };
            // $scope.salesReturnTrue = function(){
            //     $scope.loadPageList = function(){
            //         if( $scope.salesReturnName == "退货"){
            //             $scope.salesReturnFlag = false;
            //             $scope.order[0].order[$scope.salesReturnIndex].salesReturn = "确认退货";
            //             $scope.order[0].order[$scope.salesReturnIndex].refund = "";
            //             $scope.order[0].order[$scope.salesReturnIndex].state = "退货中";
            //
            //         }else if($scope.salesReturnName == "确认退货"){
            //             console.log(1)
            //             $scope.refundFlag = false;
            //             $scope.order[0].order[$scope.salesReturnIndex].state = "已退货";
            //         }
            //         $scope.showFlag = true;
            //         $scope.hideFlag = false;
            //     }
            //     $scope.loadPageList()
            // };


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
                $scope.orderPay = orderShipmentsStartTime.value+orderShipmentsEndTime.value;
                if($scope.orderComplete !=""&&$scope.orderPay!=""){
                    $scope.startTime = orderPayStartTime.value;
                    $scope.ndTime = orderPayEndTime.value;
                    $scope.type = "1"
                }else if($scope.orderComplete !=""){
                    $scope.startTime =  orderPayStartTime.value;
                    $scope.endTime = orderPayEndTime.value;
                    $scope.type = "1"
                }else if($scope.orderPay!=""){
                    $scope.startTime = orderShipmentsStartTime.value;
                    $scope.endTime = orderShipmentsEndTime.value;
                    $scope.type = "2"
                }else{
                    $scope.startTime = "";
                    $scope.endTime = "";
                    $scope.type = "0"
                }
            }

            $scope.loadPageList = function(){

                $scope.statusGet()

                    if($scope.status==""&&$scope.orderReference == ""&&$scope.orderUserAccount == ""&& $scope.orderComplete==""&&$scope.orderPay==""){
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
                    }else{
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
                            isExportExcel:"N"
                        };
                        QueryBusinessOrderByParameters.save(PageParamVoDTO,function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.result == Global.SUCCESS){
                                theSame(data)
                                $scope.mum = false;
                            }else{
                                $scope.orderLis =[];
                            }
                        })
                    }
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
                            orderLis[i].status = "订单已删除"                                                                    }
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
            $scope.bgChangeAndSearch = function(index,type){
                $scope.agencyIndex = index;
                $scope.active = 'active';
                $scope.status = type;
                $scope.loadPageList();
                $scope.choosePage(1)
            };
/*搜索*/
            $scope.searchOrder = function(){
                $scope.loadPageList();
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
            }

        }]);

