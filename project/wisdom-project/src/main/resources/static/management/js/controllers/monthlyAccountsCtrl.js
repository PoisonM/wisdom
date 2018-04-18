angular.module('controllers',[]).controller('monthlyAccountsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserIncomeByParameters','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserIncomeByParameters,ManagementUtil) {
            var startTime = document.querySelector(".MStart");
            var endTime = document.querySelector(".MEnd");
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.MAccount = "";
            $scope.status="instance";
            $scope.agencyIndex = 1;
            $scope.stateIndex = "allState";
           /* $scope.mum = true;*/
            var pageTrue = true;

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
           /* $scope.haha = {
                meme:"false",
                type:"open"
            }*/
            var a = 1;
            /*点击查看按钮*/
            $scope.details = function(sysUserId,createDate,incomeType,transactionId,index){
                if($scope.status == "instance"||$scope.status == "recommends"){
                 /*   if(index == $scope.haha.meme){
                        $scope.haha.meme = "false"
                    }else{
                        $scope.haha.meme = index
                    }*/
                    for(var i = 0; i < $scope.MonthlyBalanceLis.length; i++ ){
                        $scope.MonthlyBalanceLis[i].states = "1"
                    }
                    a++;
                    if(a%2 ==0){
                        $scope.MonthlyBalanceLis[index].states = "2"
                    }else if (a%2==1){
                        $scope.MonthlyBalanceLis[index].states = "1"
                    }
                    /* $state.go("forthwithAward",{transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status})*/
                }else if($scope.status=="month"){
                    $state.go("abschluss",{id:sysUserId,time:createDate,transactionId:transactionId,MAccount:$scope.MAccount,startTime:startTime.value,endTime:endTime.value,pageNo:$scope.pageNo,status:$scope.status})
                }
            };
/*月结  即时提现*/
            $scope.loadPageList = function(){
             /*   $timeout(function(){
                    if(window.location.hash.indexOf("true") != -1 && pageTrue == true ){
                        $scope.MAccount = $stateParams.MAccount;
                        startTime.value = $stateParams.startTime;
                        endTime.value = $stateParams.endTime;
                        $scope.pageNo= $stateParams.pageNo;
                        $scope.status = $stateParams.status;
                        pageTrue = false;
                    }
                    QueryUserIncomeByParameters.get({
                        phoneAndIdentify:$scope.MAccount,
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        incomeType:$scope.status,
                        isExportExcel:"N"
                    },function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result == Global.SUCCESS){
                            if(data.responseData.responseData == undefined){
                                $scope.MonthlyBalanceLis=[];
                            }else{
                                var MonthlyBalanceLis = data.responseData.responseData;
                                for(var i=0;i<MonthlyBalanceLis.length;i++){
                                    if(MonthlyBalanceLis[i].incomeType == "instance"){
                                        MonthlyBalanceLis[i].incomeType ="即时奖励"
                                    }else if(MonthlyBalanceLis[i].incomeType == "month"){
                                        MonthlyBalanceLis[i].incomeType ="月度结算"
                                    }else if(MonthlyBalanceLis[i].incomeType == "recommend"){
                                        MonthlyBalanceLis[i].incomeType ="同级推荐"
                                    }
                                    if(MonthlyBalanceLis[i].status == "0"){
                                        MonthlyBalanceLis[i].status ="不可提现"
                                    }else if(MonthlyBalanceLis[i].status == "1"){
                                        MonthlyBalanceLis[i].status ="可提现"
                                    }else if(MonthlyBalanceLis[i].status == "2"){
                                        MonthlyBalanceLis[i].status ="冻结中"
                                    }
                                }
                                $scope.MonthlyBalanceLis = MonthlyBalanceLis;
                            }

                            $scope.MonthlyBalanceLis = data.responseData.responseData;
                            $scope.counnt='';
                            $scope.response = {};
                            $scope.response.count = data.responseData.totalCount;
                            $scope.pageSize = 5;
                            $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                            $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                            $scope.mum = false;
                            for(var i=0;i<$scope.MonthlyBalanceLis.length;i++){
                                $scope.MonthlyBalanceLis[i].userType = $scope.MonthlyBalanceLis[i].userType.substring(9,10)+"级";
                            }
                            /!*  $scope.MAccount = "";
                             startTime.value='';
                             endTime.value='';*!/

                        }
                    })
                },10);*/
                };

/*按钮的切换*/
            $scope.bgChangeAndSearch = function(type){
                startTime.value ="";
                endTime.value='';
                $scope.MAccount = "";
                $scope.active = 'active';
                $scope.status =type;
                $scope.loadPageList();
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

                $scope.loadPageList();
                $scope.choosePage(1)
            };
 /*导出列表*/
            $scope.educeLis = function(){
                if (confirm("确认要导出？")) {
                    QueryUserIncomeByParameters.get({
                        phoneAndIdentify:$scope.MAccount,
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        incomeType:$scope.status,
                        isExportExcel:"Y"
                    },function(data){
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
            };

            /*筛选已完成的订单*/
            $scope.completedOrders=function () {
                if (confirm("是否筛选已完成的订单？")) {
                }
            };
            /*审核*/
            $scope.examine=function () {
                alert("确认无误审核通过")
            }

            /*状态按钮的切换*/
            $scope.stateBox = function(type){
                startTime.value ="";
                endTime.value='';
                $scope.MAccount = "";
                $scope.actives = 'actives';
                $scope.stateIndex =type;
                $scope.loadPageList();
                $scope.choosePage(1)
            };
            /*假数据*/
            $scope.MonthlyBalanceLis=[
                {
                    nickName:"汤艳春",
                    mobile:"17600522543",
                    userType:"A",
                    commission:"20",
                    createDate:"2018.4.17",
                    type:"及时奖励",
                    present:"可提现",
                    orderType:"已完成",
                    status:"1",
                    states:'1',//states为1时表示点击查看，点击查看出来的商品状态为2.
                    types:[
                        {
                        nickName:"汤艳春1",
                        mobile:"17600522543"
                       },
                        {
                            nickName:"陈桂红2",
                            mobile:"17600522543"
                        }
                    ]
                },
                {
                    nickName:"陈桂红",
                    mobile:"17600522543",
                    userType:"A",
                    commission:"20",
                    createDate:"2018.4.17",
                    type:"及时奖励",
                    present:"可提现",
                    orderType:"已完成",
                    status:"1",
                    states:'1',
                    types:[
                        {
                            nickName:"陈桂红1",
                            mobile:"17600522543"
                        },
                        {
                            nickName:"陈桂红2",
                            mobile:"17600522543"
                        }
                    ]

                },
                {
                    nickName:"苗向阳",
                    mobile:"17600522543",
                    userType:"A",
                    commission:"20",
                    createDate:"2018.4.17",
                    type:"及时奖励",
                    present:"可提现",
                    orderType:"已完成",
                    status:"1",
                    states:'1',
                    types:[
                        {
                            nickName:"苗向阳1",
                            mobile:"17600522543"
                        },
                        {
                            nickName:"苗向阳2",
                            mobile:"17600522543"
                        }
                    ]
                },
                {
                    nickName:"陶世文",
                    mobile:"17600522543",
                    userType:"A",
                    commission:"20",
                    createDate:"2018.4.17",
                    type:"及时奖励",
                    present:"可提现",
                    orderType:"已完成",
                    status:"1",
                    states:'1',
                    types:[
                        {
                            nickName:"陶世文1",
                            mobile:"17600522543"
                        },
                        {
                            nickName:"陶世文2",
                            mobile:"17600522543"
                        }
                    ]
                },
                {
                    nickName:"小明",
                    mobile:"17600522543",
                    userType:"A",
                    commission:"20",
                    createDate:"2018.4.17",
                    type:"及时奖励",
                    present:"可提现",
                    orderType:"已完成",
                    status:"1",
                    states:'1',
                    types:[
                        {
                         nickName:"小明2",
                            mobile:"17600522543"
                        },
                        {
                            nickName:"小明1",
                            mobile:"17600522543"
                        }
                    ]
                },

            ]
            }]);

