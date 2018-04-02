/**
 * Created by Administrator on 2018/3/6.
 */
angular.module('controllers',[]).controller('logisticDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetlogisticsQuery','QueryOrderCopRelationById','GetOrderAddressDetail','Global',
        function ($scope,$rootScope,$stateParams,$state,GetlogisticsQuery,QueryOrderCopRelationById,GetOrderAddressDetail,Global) {
            $rootScope.title = "物流详情";
            $scope.param = {
                orderDetailInfo : {},
            }
            QueryOrderCopRelationById.save({
                "orderId":$stateParams.orderId
            },function (data) {
                if(data.result == "0x00001"){
                    $scope.waybillNumber = data.responseData[0].waybillNumber;
                    GetlogisticsQuery.save({
                        logisticCode: $scope.waybillNumber,
                    },function (data) {
                        if(data.code == 200){
                            if(data.data.Traces.length != 0){
                                $scope.state = data.data.State;
                                $scope.logisticCode = data.data.LogisticCode;
                                $scope.dataList = data.data.Traces
                            }else {
                                $scope.state = data.data.Reason
                            }
                        }
                    })
                }else {
                    alert(data.result)
                }
            });
            GetOrderAddressDetail.get({orderId:$stateParams.orderId},function(data){
                if(data.result==Global.SUCCESS)
                {
                    $scope.param.orderDetailInfo = data.responseData;
                    console.log($scope.param.orderDetailInfo);
                    console.log(data)
                }
            });
        }])