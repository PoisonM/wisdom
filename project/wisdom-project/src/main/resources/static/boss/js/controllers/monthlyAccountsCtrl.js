angular.module('controllers',[]).controller('monthlyAccountsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserIncomeByParameters','QueryAllUserIncome','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserIncomeByParameters,QueryAllUserIncome,ManagementUtil) {
            var startTime = document.querySelector(".MStart");
            var endTime = document.querySelector(".MEnd");
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.MAccount = "";
            $scope.status="instance";
            $scope.agencyIndex = 1;
            $scope.mum = true;
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
            };

$scope.details = function(sysUserId,createDate,incomeType,transactionId){
    if($scope.status == "instance"){
         $state.go("forthwithAward",{transactionId:transactionId})
    }else if($scope.status=="month"){
        $state.go("abschluss",{id:sysUserId,time:createDate,transactionId:transactionId})
    }
}

/*月结  即时提现*/
            $scope.loadPageList = function(){
                $scope.mum = true;
                /*if($scope.status == -1){
                    QueryAllUserIncome.save({
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize
                    },function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result == Global.SUCCESS){
                            $scope.counnt='';
                            $scope.response = {};
                            $scope.response.count = data.responseData.totalCount;
                            $scope.pageSize = 5;
                            $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                            $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                            if( data.responseData.responseData != undefined){
                                var MonthlyBalanceLis = data.responseData.responseData;
                                for(var i=0;i<MonthlyBalanceLis.length;i++){
                                    if(MonthlyBalanceLis[i].status == "0"){
                                        MonthlyBalanceLis[i].status ="同级推荐"
                                    }else if(MonthlyBalanceLis[i].status == "1"){
                                        MonthlyBalanceLis[i].status ="月度结算"
                                    }else if(MonthlyBalanceLis[i].status == "2"){
                                        MonthlyBalanceLis[i].status ="即时奖励"
                                    }
                                }
                            }
                            $scope.MonthlyBalanceLis = MonthlyBalanceLis;
                            $scope.MAccount = "";
                            startTime.value='';
                            endTime.value='';
                            return
                        }
                    })
                }*/
                    QueryUserIncomeByParameters.get({
                        phoneAndIdentify:$scope.MAccount,
                        startTime:startTime.value,
                        endTime:endTime.value,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        incomeType:$scope.status
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
                          /*  $scope.MAccount = "";
                            startTime.value='';
                            endTime.value='';*/

                        }
                    })
                };

/*按钮的切换*/
            $scope.bgChangeAndSearch = function(index,type){
                $scope.agencyIndex = index;
                $scope.active = 'active';
                $scope.status =type;
                $scope.loadPageList()
                $scope.choosePage(1)

            };
 /*搜索*/
            $scope.searchMonthlyBalance = function(){
                $scope.status ="";

                if($scope.MAccount != ""){
                    if(pattern.test($scope.MAccount) == false && pattern1.test($scope.MAccount)== false){
                        $scope.MAccount='请填写正确的手机号或身份证号';
                        return;
                    }
                }

                $scope.loadPageList();
                $scope.choosePage(1)
            }
            }]);

