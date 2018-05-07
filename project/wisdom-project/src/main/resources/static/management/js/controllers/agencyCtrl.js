angular.module('controllers',[]).controller('agencyCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ManagementUtil','QueryUserInfoDTOByParameters',
            function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ManagementUtil,QueryUserInfoDTOByParameters) {

            var StartTime = document.querySelector(".startTime");
            var EndTime = document.querySelector(".endTime");
            $scope.phoNum ="";
            $scope.status = "";
            $scope.agencyIndex=0;/*用于按钮的切换*/
            $scope.mum = true;
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
/*此页面一个接口*/
            $scope.loadPageList = function(){
                if($scope.phoNum != "" ){
                    startTime = "";
                    endTime = "";
                }
                $timeout(function(){
                    if(window.location.hash.indexOf("true") != -1 && pageTrue == true ){
                        $scope.pageNo = $stateParams.pageNo;
                        $scope.status = $stateParams.userType;
                        $scope.phoNum = $stateParams.mobile;
                        StartTime.value= $stateParams.startTime;
                        EndTime.value = $stateParams.endTime;
                        pageTrue = false;
                    }
                    $scope.pageParamVoDTO= {
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        requestData:{
                            mobile:$scope.phoNum,
                            userType:$scope.status
                        },
                        startTime:StartTime.value,
                        endTime:EndTime.value,
                        isExportExcel:"N"
                    };
                    QueryUserInfoDTOByParameters.save($scope.pageParamVoDTO,function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result == Global.SUCCESS){
                            if( data.responseData.totalCount ==0){
                                alert("未查出相应结果");
                            }
                            var datas =  data.responseData.responseData;
                            if(data.responseData.responseData == undefined){
                                $scope.agency =[];
                                return
                            }
                            for(var i=0;i<datas.length;i++){
                                if(datas[i].userType.indexOf('manager') !=-1  ){
                                    datas[i].userType = "系统管理员"
                                }else{
                                    datas[i].userType = datas[i].userType.substring(9,10)+"级";
                                }
                            }
                            $scope.agency = datas;
                            $scope.response = {};
                            $scope.response.count = data.responseData.totalCount;
                            $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                            $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                            $scope.mum = false;
                        }

                    })
                },10);

            };
/*全部  A级 B级 按钮*/
            $scope.bgChangeAndSearch = function(type){
                $scope.phoNum = "";
                StartTime.value ='';
                EndTime.value ='';
                $scope.status = type;
                $scope.active = 'active';
                $scope.loadPageList();
                $scope.choosePage(1)
            };
/*搜索*/
            $scope.searchAB = function(){
                $scope.choosePage(1)
            };
//导表

          /*   $scope.educeLis = function(){
                    if (confirm("确认要导出？")) {
                        var pageParamVoDTO= {
                            pageNo:$scope.pageNo,
                            pageSize:$scope.pageSize,
                            requestData:{
                                mobile:$scope.phoNum,
                                userType:$scope.status
                            },
                            startTime:StartTime.value,
                            endTime:EndTime.value,
                            isExportExcel:"Y"
                        };
                        QueryUserInfoDTOByParameters.save(pageParamVoDTO,function(data){
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
                }*/


}]).filter('timeDistance',function(){
            return function(num){
                function NewDate(str) {
                    if(str==null)
                        return false;
                    str = str.split('-');
                    var date = new Date();
                    date.setUTCFullYear(str[0], str[1] - 1, str[2]);
                    date.setUTCHours(0, 0, 0, 0);
                    return date;
                }
                /* 算法：成为A的时间加上一年
                 获取当前时间
                 计算当前的时间与生命周期的时间*/
                //成为A的时间
               /* var dateBegin = new Date(num.replace(/-/g, "/"));*/
                var a = num;
        //一年后到期的时间
                var a1 = new Date(a);
                var year1 = a1.setYear(a1.getFullYear() + 1)/1;
                var c = new Date(year1);

        //当前时间
                var oDate = new Date();
                //时效
                var   runTime = parseInt((c.getTime() - oDate.getTime()) / 1000);
                var year2 = Math.floor(runTime / 86400 / 365);
                runTime = runTime % (86400 * 365);
                var month = Math.floor(runTime / 86400 / 30.41);
                runTime = runTime % (86400 * 30.41);
                var day = Math.floor(runTime / 86400);
                runTime = runTime % 86400;
                var hour = Math.floor(runTime / 3600);
                runTime = runTime % 3600;
                var minute = Math.floor(runTime / 60);
                runTime = runTime % 60;
                var second = runTime;
                if(day>=30){
                    month = month+1;
                    day = 0;
                }
                return month+'个月'+day+'天'
            }
});