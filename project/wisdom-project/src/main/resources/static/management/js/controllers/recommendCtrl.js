/**
 * Created by Administrator on 2018/1/11.
 */
angular.module('controllers',[]).controller('recommendCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ExportNextUserInfoControl','UpdateIncomeRecordStatusById','$filter','ManagementUtil','QueryMonthPayRecordByUserId',"FindNextUserInfoControl","ExportExcelMonthTransactionRecordByUserId","SelectNextMonthTransactionRecordByUserId",
        function (
    $scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ExportNextUserInfoControl,UpdateIncomeRecordStatusById,$filter,ManagementUtil,QueryMonthPayRecordByUserId,FindNextUserInfoControl,ExportExcelMonthTransactionRecordByUserId,SelectNextMonthTransactionRecordByUserId) {
            $scope.mum = true;
            $scope.count=0;
            $scope.param = {
                recommend : {}

            };


                /*导出列表*/
                 $scope.educeLis=function () {
                   if (confirm("是否导出列表？")) {
                        var page = {
                            pageNo: $scope.pageNum,
                            pageSize: $scope.pageSize,
                            isExportExcel:"Y",
                            requestData: {
                                sysUserId: $stateParams.id
                            },
                        };
                        ExportNextUserInfoControl.save(page,
                            function(data){
                                ManagementUtil.checkResponseData(data,"");
                                if (data.errorInfo == Global.SUCCESS) {
                                    var $eleForm = $("<form method='get'></form>");
                                    $eleForm.attr("action", data.result);
                                    $(document.body).append($eleForm);
                                    $eleForm.submit();
                                }
                            });
                        }
                    }

            $scope.detailPageList= function () {
                var page = {
                    pageNo:$scope.pageNum,
                    pageSize:$scope.pageSize,
                    requestData:{
                        sysUserId:$stateParams.id
                    },
                    isExportExcel:"N",
                };
                FindNextUserInfoControl.save(page,function(data){

                    ManagementUtil.checkResponseData(data,"");

                    if(data.result == Global.SUCCESS){


                        $scope.param.recommend = data.responseData.responseData;
                        for(var i =0;i<$scope.param.recommend.length;i++){
                            if( $scope.param.recommend[i].userInfoDTO.userType !=null){
                                $scope.param.recommend[i].userInfoDTO.userType = $scope.param.recommend[i].userInfoDTO.userType.substring(9,10)+"级";
                            }


                        }

                        $scope.count = data.responseData.totalCount;
                        if(data.responseData.totalCount == 0){
                            data.responseData.totalCount=1;
                        }
                        if($scope.pageNum>=Math.ceil($scope.count/$scope.pageSize)){
                            $scope.hint="none"
                        }
                        $scope.mum = false;
                    }else{
                        $scope.count = 1;
                    }
                });
            };

            $scope.back = function(){
                $state.go("monthlyAccounts",{true:'true',MAccount:$stateParams.MAccount,startTime:$stateParams.startTime,endTime:$stateParams.endTime,pageNo:$stateParams.pageNo,status:$stateParams.status,checkStatus:$stateParams.checkStatus});
            }

}]);