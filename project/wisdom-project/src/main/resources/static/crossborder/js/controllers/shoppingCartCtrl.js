angular.module('controllers',[]).controller('shoppingCartCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductOrderList','DeleteOrderFromBuyCart',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductOrderList,DeleteOrderFromBuyCart) {
            GetBorderSpecialProductOrderList.get({status:"all"},function (data) {
                if(data.result == Global.SUCCESS){
                    $scope.cartList = data.responseData;
                }else{
                    alert("获取信息失败")
                }
                console.log(data)
            })

            // updateBusinessOrderStatus
            //删除此订单
            $scope.delete=function(item2){
                DeleteOrderFromBuyCart.get({orderId:item2.orderId},function(data){
                    if(data.result==Global.SUCCESS){
                        
                    }
                })
            };
        }]);