angular.module('controllers',[]).controller('buyCartCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBuyCartInfo','AddProduct2BuyCart',
        'MinusProduct2BuyCart','DeleteOrderFromBuyCart','Global','PutNeedPayOrderListToRedis','$ionicLoading',"GetUserInfoByOpenId",
        function ($scope,$rootScope,$stateParams,$state,GetBuyCartInfo,AddProduct2BuyCart,
                  MinusProduct2BuyCart,DeleteOrderFromBuyCart,Global,PutNeedPayOrderListToRedis,$ionicLoading,GetUserInfoByOpenId) {

            $(".cartNull").hide();

            //载入购物车信息
            var loadBuyCartInfo = function(){
                GetBuyCartInfo.get(function(data){

                    $ionicLoading.hide();
                    if (data.responseData=="")
                    {
                        $(".cartNull").show();
                    }else {
                        $(".cartNull").hide();
                        var senderAddressList = [];
                        $scope.param.totalPayPrice = 0;
                        angular.forEach(data.responseData,function(value,index,array){
                            if (value.productStatus == "1"){
                                $scope.param.totalPayPrice = $scope.param.totalPayPrice + parseInt(value.businessProductPrice)*parseInt(value.businessProductNum);
                            }
                            senderAddressList.push(value.senderAddress);
                        });
                        var uniqueSenderAddressList = unique(senderAddressList);
                        angular.forEach(uniqueSenderAddressList,function(value1,index,array){
                            $scope.param.unPaidOrder.push({
                                senderAddress:value1,
                                addressChecked:true,
                                orderList: []
                            })
                        });
                        angular.forEach(data.responseData,function(value2,index,array){
                            angular.forEach($scope.param.unPaidOrder,function(value3,index,array){
                                if(value3.senderAddress==value2.senderAddress)
                                {
                                    value3.orderList.push({
                                        productFirstUrl : value2.businessProductFirstUrl,
                                        productName : value2.businessProductName,
                                        productSpec : value2.productSpec,
                                        productPrice : value2.businessProductPrice,
                                        productNum : value2.businessProductNum,
                                        productAmount:value2.productAmount,
                                        productId : value2.businessProductId,
                                        orderId : value2.businessOrderId,
                                        productStatus:value2.productStatus,
                                        productPrefecture:value2.productPrefecture,
                                        orderChecked:true
                                    })
                                }
                            });
                            /*测试用的*/
                            /* $scope.param.unPaidOrder[0].orderList[0].productStatus='0';*/
                        })
                    }
                })
            };

            var unique = function(arr) {
                var result = [], hash = {};
                for (var i = 0, elem; (elem = arr[i]) != null; i++) {
                    if (!hash[elem]) {
                        result.push(elem);
                        hash[elem] = true;
                    }
                }

                return result;
            };

            var reCalcTotalPayPrice = function() {
                angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                    angular.forEach(value.orderList,function(value1,index,array){
                        if(value1.orderChecked && value1.productStatus == "1")
                        {
                            $scope.param.totalPayPrice = $scope.param.totalPayPrice + parseInt(value1.productPrice)*parseInt(value1.productNum);
                        }
                        console.log(value1.orderChecked);
                        console.log(value1.productStatus)
                    })
                })
            };

            $scope.selectAllOrder = function() {
                $scope.param.selectAll = !$scope.param.selectAll;
                if(!$scope.param.selectAll)
                {
                    angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                        value.addressChecked = false;
                        angular.forEach(value.orderList,function(value1,index,array){
                            value1.orderChecked=false;
                        })
                    })
                }
                else
                {
                    angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                        value.addressChecked = true;
                        angular.forEach(value.orderList,function(value1,index,array){
                            if (value1.productStatus == "1"){
                                value1.orderChecked=true;
                            }
                        });
                        console.log(value.orderList)
                    })
                }
                $scope.param.totalPayPrice = 0;
                reCalcTotalPayPrice();
            };

            $scope.chooseOrder = function(item){
                item.orderChecked = !item.orderChecked;
                $scope.param.totalPayPrice = 0;
                reCalcTotalPayPrice();
            };

            $scope.chooseAddress = function(item){
                item.addressChecked = !item.addressChecked;
                if(!item.addressChecked)
                {
                    angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                        angular.forEach(value.orderList,function(value1,index,array){
                            if(value.senderAddress==item.senderAddress)
                            {
                                value1.orderChecked=false;
                            }
                        })
                    })
                }
                else
                {
                    angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                        angular.forEach(value.orderList,function(value1,index,array){
                            if(value.senderAddress==item.senderAddress)
                            {
                                value1.orderChecked=true;
                            }
                        })
                    })
                }
                $scope.param.totalPayPrice = 0;
                reCalcTotalPayPrice();
            };

            var addButton = true;
            $scope.addProductNum = function(item){
                if(addButton)
                {
                    addButton = false;
                    if(item.productId=="MXT99-14"&&item.productNum+1>1)
                    {
                        alert("对不起，618活动商品只能抢购一套");
                    }
                    else {
                        AddProduct2BuyCart.get({productId:item.productId,productSpec:item.productSpec,productNum:1},function(data){
                            item.productNum = (parseInt(item.productNum)+1).toString();
                            $scope.param.totalPayPrice = 0;
                            reCalcTotalPayPrice();
                            addButton = true;
                        });
                        if(parseInt(item.productNum)>=parseInt(item.productAmount)){
                            $("#greyBox").css("background","grey")
                        }
                    }
                }
            };

            var minusButton =  true;
            $scope.minusProductNum = function(item){
                if(minusButton)
                {
                    minusButton = false;
                    if(parseInt(item.productNum)-1<=0)
                    {
                        $(".ion-ios-minus-outline").attr('disabled','disabled').addClass("grey");
                        minusButton = true;
                    }
                    else
                    {
                        MinusProduct2BuyCart.get({productId:item.productId,productSpec:item.productSpec},function(data){
                            item.productNum = (parseInt(item.productNum)-1).toString();
                            $scope.param.totalPayPrice = 0;
                            reCalcTotalPayPrice();
                            minusButton = true;
                        })
                    }
                    if(parseInt(item.productNum)-1<=parseInt(item.productAmount)){
                        $("#greyBox").css("background","red")
                    }

                }
            };

            //删除此订单
            $scope.delete=function(item2){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                DeleteOrderFromBuyCart.get({orderId:item2.orderId},function(data){
                    if(data.result==Global.SUCCESS){
                        $ionicLoading.hide();
                        $scope.param = {
                            unPaidOrder:[]
                        };
                        loadBuyCartInfo();
                    }
                })
            };
            $scope.goPay = function() {
                var needPayOrderList = [];
                var alertFlag = true;
                angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                    angular.forEach(value.orderList,function(value1,index,array){
                        if(value1.orderChecked&&value1.productStatus == "1")
                        {
                            needPayOrderList.push(value1);
                        }

                        if(value1.productStatus == "1"){
                            alertFlag = false;
                            return;
                        }

                    })
                });

                angular.forEach($scope.param.unPaidOrder,function(value,index,array){
                    angular.forEach(value.orderList,function(value1,index,array){
                        if(value1.productStatus == "0"&&alertFlag){
                            alertFlag = false;
                            alert("商品下架啦哟亲！");
                        }
                    })
                });

                for(var i =0;i<needPayOrderList.length;i++){
                    if(needPayOrderList[i].productNum>parseInt(needPayOrderList[i].productAmount)){
                        alert("库存不足~");
                        return;
                    }
                }
                //将needPayOrderList数据放入后台list中
                PutNeedPayOrderListToRedis.save({needPayOrderList:needPayOrderList},function(data){

                    if(needPayOrderList=="")
                    {
                        $("#greyBox").css("background","grey")
                    }
                    else if(data.result==Global.SUCCESS)
                    {
                        window.location.href = "orderPay.do?productType=offline&random="+Math.random();
                    }
                    else if(data.result==Global.FAILURE){
                        if(data.errorInfo=="failure"){
                            alert("亲！此商品为新用户专享产品");
                        }else{
                            alert("库存不足~,购买失败");
                            $state.go("shopHome");
                        }
                    }
                })

            };

            $scope.$on('$ionicView.enter', function(){
                $scope.param = {
                    unPaidOrder:[],
                    totalPayPrice : 0,
                    selectAll:true
                };
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                loadBuyCartInfo();
            })
        }]);