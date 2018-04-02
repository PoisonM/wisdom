/**
 * Created by Administrator on 2018/1/11.
 */
angular.module('controllers',[]).controller('abschlussCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryMonthTransactionRecordByIncomeRecord','UpdateIncomeRecordStatusById','$filter','ManagementUtil','QueryMonthPayRecordByUserId',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryMonthTransactionRecordByIncomeRecord,UpdateIncomeRecordStatusById,$filter,ManagementUtil,QueryMonthPayRecordByUserId) {
            $scope.mum = true;
                 $scope.loadPageList = function () {
                     $scope.mum = true;
                     var page = {
                         pageNo:$scope.pageNo,
                         pageSize:$scope.pageSize,
                         requestData:{
                             sysUserId:$stateParams.id
                         },
                         endTime:$filter("date")($stateParams.time,'yyyy-MM-dd')
                     };
                     QueryMonthTransactionRecordByIncomeRecord.save(page,
                         function(data){
                             ManagementUtil.checkResponseData(data,"");
                             if(data.result == Global.SUCCESS){
                                 $scope.mum = false;
                                 $scope.monthlyPar = data.responseData.responseData;
                                 for(var i=0;i< $scope.monthlyPar.length;i++){

                                     $scope.monthlyPar[i].userType = $scope.monthlyPar[i].userType.substring(9,10)+"级";

                                 }

                                  $scope.response = {};
                                  $scope.response.count = data.responseData.totalCount;
                                  $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                                  $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;

                             }
                         });

                 };
            $scope.detailPageList= function () {


                $scope.mum = true;
                var page = {
                    pageNo:$scope.pageNum,
                    pageSize:$scope.pageSize,
                    requestData:{
                        sysUserId:$stateParams.id
                    },
                    endTime:$filter("date")($stateParams.time,'yyyy-MM-dd')
                };
                QueryMonthPayRecordByUserId.save(page,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        $scope.abschluss = data.responseData.responseData;
                        for(var i=0;i<$scope.abschluss.length;i++){
                            if($scope.abschluss[i].status == "0"){
                                $scope.abschluss[i].status = "未支付"
                            }else if($scope.abschluss[i].status == "1"){
                                $scope.abschluss[i].status = "已支付"
                            }else if($scope.abschluss[i].status == "2"){
                                $scope.abschluss[i].status = "支付失败"
                            }
                        }
                        var count = data.responseData.totalCount;
                        if($scope.pageNum>=count/$scope.pageSize){
                            $scope.hint="none"
                        }
                        $scope.mum = false;
                    }
                });
            };


/*审核通过*/
         /*    $scope.true = function(){

            };
            $scope.cancel = function(){

            };*/
          /*  $scope.submit = function(){
                var  pageParamVoDTO = {
                    requestData:{
                        id:$stateParams.id,
                        status:"1"
                    }
                };
                UpdateIncomeRecordStatusById.save(pageParamVoDTO,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                       $state.go("monthlyAccounts")
                    }
                })
            };*/

            $scope.back = function(){
                $state.go("monthlyAccounts",{true:'true',MAccount:$stateParams.MAccount,startTime:$stateParams.startTime,endTime:$stateParams.endTime,pageNo:$stateParams.pageNo,status:$stateParams.status});
            }

        }]);