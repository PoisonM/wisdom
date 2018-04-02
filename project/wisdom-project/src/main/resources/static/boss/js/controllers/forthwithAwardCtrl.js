/**
 * Created by Administrator on 2018/1/11.
 */
angular.module('controllers',[]).controller('forthwithAwardCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','UpdateIncomeRecordStatusById','$filter','ManagementUtil','QueryInstanceInfoByTransactionId',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,UpdateIncomeRecordStatusById,$filter,ManagementUtil,QueryInstanceInfoByTransactionId) {

                   var pageParamVoDTO={
                       requestData:{
                           transactionId:$stateParams.transactionId
                       }
                   };
                 $scope.mum = true;
                QueryInstanceInfoByTransactionId.save(pageParamVoDTO,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        $scope.monthlyPar = data.responseData.responseData;
                        for(var i=0;i<$scope.monthlyPar.length;i++){
                            if($scope.monthlyPar[i].status == "0"){
                                $scope.monthlyPar[i].status = "未支付"
                            }else if($scope.monthlyPar[i].status == "1"){
                                $scope.monthlyPar[i].status = "已支付"
                            }else if($scope.monthlyPar[i].status == "2"){
                                $scope.monthlyPar[i].status = "支付失败"
                            }
                        }
                        $scope.mum = false;
                    }
                });
/*审核通过*/
           /* $scope.submit = function(){
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
            }*/

            $scope.back = function(){
                $state.go("monthlyAccounts");
            }

        }]);