angular.module('controllers',[]).controller('monthlyAccountsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserIncomeByParameters','ManagementUtil','GetIncomeRecordByPageParam',"CheckIncomeRecordManagement",'QueryIncomeInfoByIncomeId','ExportExcelIncomeRecord',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserIncomeByParameters,ManagementUtil,GetIncomeRecordByPageParam,CheckIncomeRecordManagement,QueryIncomeInfoByIncomeId,ExportExcelIncomeRecord) {
            var startTime = document.querySelector(".MStart");
            var endTime = document.querySelector(".MEnd");
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.MAccount = "";
            $scope.status="instance";
            $scope.agencyIndex = -1;
            $scope.checkStatus = "";
            $scope.mum = true;
            var pageTrue = true;
            $scope.auditFlag=false;


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
            };

            var a = [];
            /*点击查看按钮*/
            $scope.details = function(sysUserId,createDate,incomeType,transactionId,index,id){
                $scope.agencyIndex = index;
                if($scope.status == "instance"||$scope.status == "recommend"){
                   for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].statesLook = "1"
                    }
                    a[index]++;
                    if(a[index]%2 ==0){
                        $scope.mum = true;
                        $scope.MonthlyBalanceLis[index].statesLook = "2";
                        QueryIncomeInfoByIncomeId.get({
                            incomeId:id
                        },function(data){
                            ManagementUtil.checkResponseData(data,"");
                            $scope.mum = false;
                            $scope.user=data.responseData.user;
                            $scope.IncomeList=data.responseData.IncomeList;
                            if(data.errorInfo == Global.SUCCESS) {
                                $scope.mum = false;
                                $scope.user=data.responseData.user;
                                $scope.IncomeList=data.responseData.IncomeList;
                                $scope.orderIdFun($scope.IncomeList);
                                $scope.userType($scope.user);
                                for (var i = 0; i <  $scope.IncomeList.length; i++) {
                                    if( $scope.IncomeList[i].status == "0"){
                                        $scope.IncomeList[i].status = "未付款"
                                    }else if( $scope.IncomeList[i].status == "1"){
                                        $scope.IncomeList[i].status = "待发货"
                                    }else if( $scope.IncomeList[i].status == "2"){
                                        $scope.IncomeList[i].status = "已完成"
                                    }else if( $scope.IncomeList[i].status == "3"){
                                        $scope.IncomeList[i].status = "物品在购物车中"
                                    }else if( $scope.IncomeList[i].status == "4"){
                                        $scope.IncomeList[i].status = "待收货"
                                    }else if( $scope.IncomeList[i].status == "del"){
                                        $scope.IncomeList[i].status = "订单已删除"
                                    }
                                }
                                for(var i=0;i<$scope.user.length;i++){
                                    if($scope.user[i].parentRelation.indexOf("business") !=-1 ){
                                        $scope.user[i].parentRelation = "直接关系"
                                    }else if($scope.user[i].parentRelation.indexOf("A1") !=1 || $scope.user[i].parentRelation.indexOf("B1") !=1){
                                        $scope.user[i].parentRelation = "间接关系"
                                    }else if($scope.user[i].parentRelation.indexOf("self") !=1){
                                        $scope.user[i].parentRelation = "本人"
                                    }
                                }

                            }
                        })
                    }else if (a[index]%2==1){
                        $scope.MonthlyBalanceLis[index].statesLook = "1";
                        $scope.agencyIndex =-1;
                    }


                    /* $state.go("forthwithAward",{transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status})*/
                }else if($scope.status=="month"){
                    $state.go("abschluss",{id:sysUserId,time:createDate,transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status,checkStatus:$scope.checkStatus})
                }
            };
            $scope.orderIdFun = function(MonthlyBalanceLis){
                for (var i = 0; i < MonthlyBalanceLis.length; i++) {
                    if(MonthlyBalanceLis[i].orderStatus == "0"){
                        MonthlyBalanceLis[i].orderStatus = "未付款"
                    }else if(MonthlyBalanceLis[i].orderStatus == "1"){
                        MonthlyBalanceLis[i].orderStatus = "待发货"
                    }else if(MonthlyBalanceLis[i].orderStatus == "2"){
                        MonthlyBalanceLis[i].orderStatus = "已完成"
                    }else if(MonthlyBalanceLis[i].orderStatus == "3"){
                        MonthlyBalanceLis[i].orderStatus = "物品在购物车中"
                    }else if(MonthlyBalanceLis[i].orderStatus == "4"){
                        MonthlyBalanceLis[i].orderStatus = "待收货"
                    }else if(MonthlyBalanceLis[i].orderStatus == "del"){
                        MonthlyBalanceLis[i].orderStatus = "订单已删除"
                    }

                }


            };
            $scope.userType = function(MonthlyBalanceLis){
                for(var i=0;i<MonthlyBalanceLis.length;i++){
                    MonthlyBalanceLis[i].userType = MonthlyBalanceLis[i].userType.substring(9,10)+"级";
                    MonthlyBalanceLis[i].userTypeNow = MonthlyBalanceLis[i].userTypeNow.substring(9,10)+"级";
                    MonthlyBalanceLis[i].nextUserTypeNow = MonthlyBalanceLis[i].nextUserTypeNow.substring(9,10)+"级";
                    MonthlyBalanceLis[i].nextUserType = MonthlyBalanceLis[i].nextUserType.substring(9,10)+"级";
                }

            }


/*月结  即时提现*/

            $scope.loadPageList = function(){
                $timeout(function(){
                    if(window.location.hash.indexOf("true") != -1 && pageTrue == true ){
                        $scope.MAccount = $stateParams.MAccount;
                        startTime.value = $stateParams.startTime;
                        endTime.value = $stateParams.endTime;
                        $scope.pageNo= $stateParams.pageNo;
                        $scope.status = $stateParams.status;
                        $scope.checkStatus = $stateParams.checkStatus;
                        pageTrue = false;
                    }

                    $scope.pageParamVoDTO = {
                        isExportExcel:"N",
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        requestData:{
                            incomeType:$scope.status,
                            mobile:$scope.MAccount,
                            checkStatus:$scope.checkStatus
                        }
                    }

                    GetIncomeRecordByPageParam.save($scope.pageParamVoDTO,function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.errorInfo == Global.SUCCESS){
                            if( data.responseData.count ==0){
                                alert("未查出相应结果");
                            }
                            if(data.responseData.incomeRecordDTOS.length <=0){
                                $scope.MonthlyBalanceLis=[];
                            }else {
                                var MonthlyBalanceLis = data.responseData.incomeRecordDTOS;
                                for (var i = 0; i < MonthlyBalanceLis.length; i++) {
                                    if (MonthlyBalanceLis[i].incomeType == "instance") {
                                        MonthlyBalanceLis[i].incomeType = "即时奖励"
                                    } else if (MonthlyBalanceLis[i].incomeType == "month") {
                                        MonthlyBalanceLis[i].incomeType = "月度结算"
                                    } else if (MonthlyBalanceLis[i].incomeType == "recommend") {
                                        MonthlyBalanceLis[i].incomeType = "店主推荐"
                                    }
                                    if (MonthlyBalanceLis[i].checkStatus == "0") {
                                        MonthlyBalanceLis[i].checkStatus = "未通过"
                                    } else if (MonthlyBalanceLis[i].checkStatus == "1") {
                                        MonthlyBalanceLis[i].checkStatus = "已通过"
                                    }
                                    if (MonthlyBalanceLis[i].secondCheckStatus == "0") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "未审核"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "1") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "运营人员"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "2") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "财务人员"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "3") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "有一方拒绝"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "4") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "双方已审核"
                                    }
                                    if (MonthlyBalanceLis[i].status == "0") {
                                        MonthlyBalanceLis[i].status = "不可提现"
                                    } else if (MonthlyBalanceLis[i].status == "1") {
                                        MonthlyBalanceLis[i].status = "可提现"
                                    } else if (MonthlyBalanceLis[i].status == "2") {
                                        MonthlyBalanceLis[i].status = "用户退货"
                                    }
                                    if(MonthlyBalanceLis[i].checkUserType !=null){
                                         if (MonthlyBalanceLis[i].checkUserType.indexOf("finance") !=-1) {
                                         MonthlyBalanceLis[i].checkUserType = "财务"
                                         } else if (MonthlyBalanceLis[i].checkUserType.indexOf("operation") !=-1) {
                                         MonthlyBalanceLis[i].checkUserType = "运营"
                                         }
                                    }

                                    MonthlyBalanceLis[i].userType = MonthlyBalanceLis[i].userType.substring(9,10)+"级";


                                    $scope.orderIdFun(MonthlyBalanceLis);
                                    $scope.MonthlyBalanceLis = MonthlyBalanceLis;
                                }
                            }
                            if($scope.MonthlyBalanceLis.length >=1){
                                for(var i=0;i< $scope.MonthlyBalanceLis.length;i++){
                                    a[i]=1;
                                    $scope.MonthlyBalanceLis[i].statesLook="1"
                                }
                            }

                            $scope.counnt='';
                            $scope.response = {};
                            $scope.response.count = data.responseData.count;
                            $scope.pageSize = 5;
                            $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                            $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                            $scope.mum = false;
                            /*  $scope.MAccount = "";
                             startTime.value='';
                             endTime.value='';*/

                        }
                    })
                },10);
                };

/*按钮的切换*/
            $scope.bgChangeAndSearch = function(type){
                startTime.value ="";
                endTime.value='';
                $scope.MAccount = "";
                $scope.active = 'active';
                $scope.status =type;
                if( $scope.MonthlyBalanceLis.length>=1){
                    for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].statesLook = "1"
                    }
                }


              /*  $scope.loadPageList();*/
                $scope.choosePage(1)


            };
 /*搜索*/
            $scope.searchMonthlyBalance = function(){

                /* if($scope.MAccount != ""){
                     if(pattern.test($scope.MAccount) == false && pattern1.test($scope.MAccount)== false){
                         $scope.MAccount='请填写正确的手机号或身份证号';
                         return;
                             }
                 }*/


                /*$scope.loadPageList();*/
                $scope.choosePage(1)
            };

 /*导出列表*/
            $scope.educeLis = function() {
                if (confirm("确认要导出？")) {
                    $scope.pageParamVoDTO = {
                        isExportExcel:"Y",
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        requestData:{
                            incomeType:$scope.status,
                            mobile:$scope.MAccount,
                            checkStatus:$scope.checkStatus
                        }
                    }
                    ExportExcelIncomeRecord.save($scope.pageParamVoDTO, function (data) {
                        ManagementUtil.checkResponseData(data, "");
                        if (data.errorInfo == Global.SUCCESS) {
                            var $eleForm = $("<form method='get'></form>");
                            $eleForm.attr("action", data.result);
                            $(document.body).append($eleForm);
                            $eleForm.submit();
                          /*  $scope.loadPageList();*/

                        }
                    })

                }
            }
            $scope.bgAll=function(){
                $scope.auditFlag = false;
                if($scope.MonthlyBalanceLis.length>=1){
                    for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].statesLook = "1";
                    }
                }

                $scope.agencyIndex =-1;
            };
            /*筛选已完成的订单*/
            $scope.completedOrders=function () {
                if (confirm("是否筛选已完成的订单？")) {
                }
            };
            /*审核*/
            $scope.examine=function (id) {
                $scope.auditFlag = !$scope.auditFlag;
                $scope.auditChange = function(status){
                    $scope.auditFlag = false;
                    CheckIncomeRecordManagement.get({
                        incomeRecordId:id,
                        status:status
                    },function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.errorInfo == Global.SUCCESS){
                            alert(data.result)

                            $scope.loadPageList();
                        }
                    })
                }
            }

            /*状态按钮的切换*/
            $scope.stateBox = function(type){
                startTime.value ="";
                endTime.value='';
                $scope.MAccount = "";
                $scope.actives = 'actives';
                $scope.checkStatus =type;

                $scope.choosePage(1)
            };

            }]);

