angular.module('controllers',[]).controller('orderListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductOrderList','PutNeedPayOrderListToRedis',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductOrderList,PutNeedPayOrderListToRedis) {
            console.log("orderList")
            // $scope.option = {
            //     inportContent:"goodsList",
            //     method:oneLevelProject,
            //     dataList:""
            // }
            GetBorderSpecialProductOrderList.get({status:"all"},function (data) {
                if(data.result == Global.SUCCESS){
                    $scope.orderList = data.responseData;
                }else{
                    alert("获取信息失败")
                }
                console.log(data)
            })


            $scope.goPay = function(item){
                var needPayOrderList = [];
                var payOrder = {
                    orderId:item.businessOrderId,
                    productFirstUrl:item.businessProductFirstUrl,
                    productId:item.businessProductId,
                    productName:item.businessProductName,
                    productNum:item.businessProductNum,
                    productPrice:item.businessProductPrice,
                    productSpec:item.productSpec,
                    productPrefecture:item.productPrefecture,
                    productStatus:item.productStatus
                };
                needPayOrderList.push(payOrder);
                //将needPayOrderList数据放入后台list中
                PutNeedPayOrderListToRedis.save({needPayOrderList:needPayOrderList},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $state.go('scanPay')

                    }else if(data.result==Global.FAILURE){
                        if(data.errorInfo=="failure"){
                        }else{
                            alert(data.errorInfo);
                        }
                    }
                })
            };
        }]);