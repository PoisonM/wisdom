angular.module('controllers',[]).controller('shoppingCartCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductOrderList'
        ,'DeleteOrderFromBuyCart','AddProduct2BuyCart','MinusProduct2BuyCart','PutNeedPayOrderListToRedis',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductOrderList
            ,DeleteOrderFromBuyCart,AddProduct2BuyCart,MinusProduct2BuyCart,PutNeedPayOrderListToRedis) {
            $scope.params = {
                checkAll:false,
                listLen:"0"
            }
            /**/
            $scope.getCartList = function () {
                GetBorderSpecialProductOrderList.get({status:"all"},function (data) {
                    if(data.result == Global.SUCCESS){
                        $scope.cartList = data.responseData;
                        for(var i = 0; i < $scope.cartList.length; i++){
                            $scope.cartList[i].checkFlag = false;
                            $scope.cartList[i].xiaoji = $scope.cartList[i].businessProductPrice*$scope.cartList[i].businessProductNum
                        }
                    }else{
                        alert("获取信息失败")
                    }
                    console.log(data)
                })
            }
            $scope.getCartList()
            $scope.addCartFlag = true
            /**/
            $scope.addnum = function (item) {
                item.businessProductNum++
                // item.businessProductId,item.businessProductNum
                item.xiaoji = item.businessProductPrice*item.businessProductNum
                if($scope.addCartFlag){
                    $scope.addCartFlag = false
                    AddProduct2BuyCart.get({
                        productId:item.businessProductId,
                        productNum:1,
                        productSpec:item.productSpec
                    },function (data) {
                        $scope.addCartFlag = true
                        $scope.getCartList()
                        if(data.result==Global.FAILURE){
                            alert("加入购物车失败");
                        }else{
                            // alert("");
                        }
                    })
                }

            }
            $scope.reducenum = function (item) {

                if(item.businessProductNum <= 1){
                    return false
                }else {
                    item.businessProductNum--
                    if($scope.addCartFlag){
                        $scope.addCartFlag = false
                        MinusProduct2BuyCart.get({
                            productId:item.businessProductId,
                            // productNum:goodsNum
                            productSpec:item.productSpec
                        },function (data) {
                            $scope.addCartFlag = true
                            $scope.getCartList()
                            if(data.result==Global.FAILURE){
                                alert("加入购物车失败");
                            }else{
                                // alert("");
                            }
                        })
                    }
                }

            }
            /*$scope.verifyNum = function (id,goodsNum) {
                if($scope.addCartFlag){
                    $scope.addCartFlag = false
                    AddBorderSpecialProduct2ShoppingCart.get({
                        productId:id,
                        productNum:goodsNum
                    },function (data) {
                        $scope.addCartFlag = true
                        $scope.getCartList()
                        if(data.result==Global.FAILURE){
                            alert("加入购物车失败");
                        }else{
                            alert("");
                        }
                    })
                }
            }*/

            // updateBusinessOrderStatus
            //删除此订单
            $scope.delete=function(item2){
                DeleteOrderFromBuyCart.get({orderId:item2.businessOrderId},function(data){
                    if(data.result==Global.SUCCESS){
                        alert("删除成功")
                        $scope.getCartList()
                    }
                })
            };


            $scope.checkAll = function () {
                $scope.params.checkAllFlag = !$scope.params.checkAllFlag;
                for(var i = 0; i < $scope.cartList.length; i++){
                    $scope.cartList[i].checkFlag = $scope.params.checkAllFlag
                }
                $scope.allPrice()
            }


            $scope.allPrice = function () {
                $scope.submitList = [];
                $scope.price = new Number()
                for(var i = 0; i < $scope.cartList.length; i++){
                    if($scope.cartList[i].checkFlag){
                        $scope.submitList.push($scope.cartList[i])
                        $scope.price += parseInt($scope.cartList[i].businessProductPrice*$scope.cartList[i].businessProductNum)
                    }
                }
                console.log($scope.submitList)
                console.log($scope.price)
            }

            $scope.checkOne = function (item) {
                item.checkFlag = !item.checkFlag
                $scope.params.checkAllFlag = true
                for(var i = 0; i < $scope.cartList.length; i++){
                    if(!$scope.cartList[i].checkFlag){
                        $scope.params.checkAllFlag = false;
                    }else {
                    }
                }
                $scope.allPrice()
            }

            $scope.goPay = function () {
                if($scope.submitList!=undefined){
                    PutNeedPayOrderListToRedis.save({
                        needPayOrderList:$scope.submitList
                    },function (data) {
                        if(data.result==Global.SUCCESS){
                           $state.go("orderSubmit")
                        }
                    })
                }else {
                    alert("请选择购买商品")
                }

            }

        }]);