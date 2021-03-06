angular.module('controllers',[]).controller('monthlyAccountsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserIncomeByParameters'
        ,'ManagementUtil','GetIncomeRecordByPageParam',"CheckIncomeRecordManagement",'QueryIncomeInfoByIncomeId'
        ,'ExportExcelIncomeRecord','MonthlyIncomeSignalMT','GetKey','GetUserInfo','GetIncomeShareActivityInfoByIncomeId',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserIncomeByParameters
                  ,ManagementUtil,GetIncomeRecordByPageParam,CheckIncomeRecordManagement,QueryIncomeInfoByIncomeId
                  ,ExportExcelIncomeRecord,MonthlyIncomeSignalMT,GetKey,GetUserInfo,GetIncomeShareActivityInfoByIncomeId) {
            var startTime = document.querySelector(".MStart");
            var endTime = document.querySelector(".MEnd");
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.MAccount = "";
            $scope.status="instance";
            $scope.agencyIndex = -1;
            $scope.checkStatus = "";
            $scope.key = "";
            $scope.value = "false";
            $scope.mum = true;
            var pageTrue = true;
            $scope.auditFlag=false;
            $scope.userMoblie = "";
            $scope.useBusinessType="";
            $scope.shareActivityFlag = false




            var a = [];

            /*获取用户信息*/
            $scope.getUser = function(){
                GetUserInfo.get({},function (data){
                    $scope.userMoblie = data.responseData.mobile;
                })
            }

            $scope.getUser();
            /*点击查看按钮*/
            $scope.details = function(sysUserId,createDate,incomeType,transactionId,index,id){
                $scope.agencyIndex = index;
                if($scope.status == "instance"||$scope.status == "permanent"){
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
                                $scope.userType($scope.user,'userType');
                                $scope.userType($scope.user,'userTypeNow');
                                $scope.userType($scope.user,'nextUserTypeNow');
                                $scope.userType($scope.user,'nextUserType');
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
                        $scope.agencyIndex = -1
                    }


                    /* $state.go("forthwithAward",{transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status})*/
                }else if($scope.status=="month"){
                    //月度查看页面
                    $state.go("abschluss",{id:sysUserId,time:createDate,transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status,checkStatus:$scope.checkStatus})
                }else if ($scope.status=="recommend"){
                     //推荐奖励页面
                     $state.go("recommend",{id:sysUserId,time:createDate,transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status,checkStatus:$scope.checkStatus})
                }else if($scope.status=="shareActivity"){
                    for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].shareActivityFlag = false
                    }
                    GetIncomeShareActivityInfoByIncomeId.get({
                        incomeId:id
                    },function (data) {
                        $scope.MonthlyBalanceLis[index].shareActivityFlag = true;
                        $scope.ShareActivityList=data.responseData;
                    })
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
            $scope.userType = function(MonthlyBalanceLis,type){
                for(var i=0;i<MonthlyBalanceLis.length;i++){
                    if( MonthlyBalanceLis[i][type]!=null){
                        MonthlyBalanceLis[i][type] = MonthlyBalanceLis[i][type].substring(9,10)+"级";
                    }
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

                    $scope.checkStatus = $("#checkStatus").val();
                    $scope.useBusinessType = $("#useBusinessType").val();

                    $scope.pageParamVoDTO = {
                        isExportExcel:"N",
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        requestData:{
                            incomeType:$scope.status,
                            mobile:$scope.MAccount,
                            checkStatus:$scope.checkStatus,
                            useBusinessType:$scope.useBusinessType
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
                                    }  else if (MonthlyBalanceLis[i].incomeType == "permanent") {
                                        MonthlyBalanceLis[i].incomeType = "永久奖励"
                                    }else if (MonthlyBalanceLis[i].incomeType == "shareActivity") {
                                        MonthlyBalanceLis[i].incomeType = "推荐有礼"
                                    }
                                    if (MonthlyBalanceLis[i].checkStatus == "0") {
                                        MonthlyBalanceLis[i].checkStatus = "未通过"
                                    } else if (MonthlyBalanceLis[i].checkStatus == "1") {
                                        MonthlyBalanceLis[i].checkStatus = "已通过"
                                    }
                                    if (MonthlyBalanceLis[i].secondCheckStatus == "0") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "未审核"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "1") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "运营人员已审核"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "2") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "财务人员已审核"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "3") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "双方已审核"
                                    } else if (MonthlyBalanceLis[i].secondCheckStatus == "4") {
                                        MonthlyBalanceLis[i].secondCheckStatus = "审核拒绝"
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

                                    if(MonthlyBalanceLis[i].userType!=null){
                                        MonthlyBalanceLis[i].userType= MonthlyBalanceLis[i].userType.substring(9,10)+"级";
                                    }
                                    if(MonthlyBalanceLis[i].nextUserType !=null){
                                        MonthlyBalanceLis[i].nextUserType = MonthlyBalanceLis[i].nextUserType.substring(9,10)+"级";
                                    }


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


                $scope.choosePage(1)


            };

            /*手动生成月度*/
            $scope.monthlyIncomeSignal = function() {
                var key = $scope.getRandomString();
                $scope.key = key;
                $scope.businessType = $("#businessType").val();
                $scope.startTimeMonth = document.querySelector(".MStartMonth").value;
                $scope.endTimeMonth = document.querySelector(".MEndMonth").value;

                if (confirm("手动生成月度？")) {

                    MonthlyIncomeSignalMT.get({businessType:$scope.businessType,startDateM:$scope.startTimeMonth,endDateM:$scope.endTimeMonth,isPullMessage:'0',key:key},function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result != Global.SUCCESS){
                            alert(data.errorInfo);
                        }
                    })

                }
            }

            $scope.setTimeJianting = function(){
                GetKey.get({
                        key:$scope.key
                    }, function (data) {
                        ManagementUtil.checkResponseData(data, "");
                        value = data.responseData;
                        if(value == "true"){
                            $scope.key="";
                            alert("生成月度完成！");
                            $scope.searchMonthlyBalance();

                        }
                   })
             }

              $scope.timeInfo = setInterval(function () {
                    $scope.setTimeJianting();
                },6000)

            $scope.getRandomString =  function() {
                len = 5 || 32;
                var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
                var maxPos = $chars.length;
                var pwd = '';
                for (i = 0; i < len; i++) {
                    pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            }


 /*搜索*/
            $scope.searchMonthlyBalance = function(){

                /* if($scope.MAccount != ""){
                     if(pattern.test($scope.MAccount) == false && pattern1.test($scope.MAccount)== false){
                         $scope.MAccount='请填写正确的手机号或身份证号';
                         return;
                             }
                 }*/



                $scope.choosePage(1)
            };

 /*导出列表*/
            $scope.educeLis = function() {

                $scope.checkStatus = $("#checkStatus").val();
                $scope.useBusinessType = $("#useBusinessType").val();
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
                            checkStatus:$scope.checkStatus,
                            useBusinessType:$scope.useBusinessType
                        }
                    }
                    ExportExcelIncomeRecord.save($scope.pageParamVoDTO, function (data) {
                        ManagementUtil.checkResponseData(data, "");
                        if (data.errorInfo == Global.SUCCESS) {
                            var $eleForm = $("<form method='get'></form>");
                            $eleForm.attr("action", data.result);
                            $(document.body).append($eleForm);
                            $eleForm.submit();

                        }
                    })

                }
            }
            $scope.bgAll=function(){
                $scope.auditFlag = false;
                if($scope.MonthlyBalanceLis.length>=1){
                    for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].statesLook = "1";
                        $scope.MonthlyBalanceLis[i].shareActivityFlag = false;
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

