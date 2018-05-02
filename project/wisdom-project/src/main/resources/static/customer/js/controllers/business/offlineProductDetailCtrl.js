angular.module('controllers',[]).controller('offlineProductDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetOfflineProductDetail',
        'AddProduct2BuyCart','BusinessUtil','GetProductNumFromBuyCart','$ionicPopup',
        '$ionicSlideBoxDelegate','CreateBusinessOrder','PutNeedPayOrderListToRedis','Global','$ionicLoading',"$interval",'LoginGlobal','$timeout','IsLogin',
        function ($scope,$rootScope,$stateParams,$state,GetOfflineProductDetail,
                  AddProduct2BuyCart,BusinessUtil,GetProductNumFromBuyCart,$ionicPopup,
                  $ionicSlideBoxDelegate,CreateBusinessOrder,PutNeedPayOrderListToRedis,Global,$ionicLoading,$interval,LoginGlobal,$timeout,IsLogin) {

            $rootScope.title = "美享99产品详情";

            $scope.explain=false;// 点击24小时发货显示说明

            $scope.model=false;

            $scope.myObj = {
                background:"red",
                padding: "5px 20px",
            }

            $scope.showFlag = function (type) {
                $scope.model = type;
                if(!type){
                    $scope.param.checkFlag=""
                }
            }

            $scope.confirmProductSpec = function(spec) {
                $scope.param.checkFlag = spec
            }

            $scope.concealment=function () {
                $scope.showFlag(false);
                $scope.param.checkFlag = "";
                $scope.param.productNum = 1
            }

            $scope.chooseSpec = function () {
                $scope.model = true
            }

            $scope.viewInstructions=function(){
                $scope.explain= true;
            }

            $scope.know=function(){
                $scope.explain=false;
            };

            $scope.addBuyCart = function(){
                if($scope.model){
                    BusinessUtil.twoParameters(LoginGlobal.MX_SC_AGW,$stateParams.productId);
                    if($scope.param.product.productDetail.spec.length == 1){
                        $scope.param.checkFlag = $scope.param.product.productDetail.spec[0]
                    }

                    //没有选择属性
                    if($scope.param.checkFlag=="")
                    {
                        $scope.model=true;
                    }else{
                        showToast("加载中...");
                        AddProduct2BuyCart.get({productId:$stateParams.productId,productSpec:$scope.param.checkFlag,productNum: $scope.param.productNum},function(data){
                            BusinessUtil.checkResponseData(data,'offlineProductDetail&'+$stateParams.productId);
                            if(data.result==Global.FAILURE){
                                showToast("加入购物车失败");
                                hideToast()
                            }else{
                                showToast("加入购物车成功");
                                hideToast();
                                $scope.param.productNum = 1;
                                $scope.showFlag(false);
                                $scope.param.checkFlag = "";
                                GetProductNumFromBuyCart.get(function(data){
                                    $scope.param.productUnPaidNum = data.responseData;
                                });
                            }
                        })
                    }
                }else{
                    $scope.model = true
                }
            }

            $scope.goPay = function(){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ACJ,$stateParams.productId);

                if($scope.model){
                    if($scope.param.product.productDetail.spec.length == 1){
                        $scope.param.checkFlag = $scope.param.product.productDetail.spec[0]
                    }
                    if($scope.param.checkFlag=="")
                    {
                        $scope.model=true;
                    }
                    else
                    {
                        showToast("加载中");
                        //先将此商品生成订单
                        CreateBusinessOrder.save({businessProductId:$scope.param.product.productId,
                            productSpec:$scope.param.checkFlag,
                            businessProductNum: $scope.param.productNum,
                            type:$scope.param.product.type},function (data) {
                            BusinessUtil.checkResponseData(data,'offlineProductDetail&'+$scope.param.product.productId);
                            if(data.result==Global.FAILURE)
                            {
                                showToast("请先登录账号")
                                hideToast()
                            }
                            else
                            {
                                //生成订单后再直接前往支付页面
                                var needPayOrderList = [];
                                var payOrder = {
                                    orderId:data.responseData,
                                    productFirstUrl:$scope.param.product.firstUrl,
                                    productId:$scope.param.product.productId,
                                    productName:$scope.param.product.productName,
                                    productNum:$scope.param.productNum,
                                    productPrice:$scope.param.product.price,
                                    productSpec:$scope.param.productSpec
                                }
                                needPayOrderList.push(payOrder);
                                //将needPayOrderList数据放入后台list中
                                PutNeedPayOrderListToRedis.save({needPayOrderList:needPayOrderList},function(data){
                                    if(data.result==Global.SUCCESS)
                                    {
                                        $scope.showFlag(false);
                                        $scope.param.checkFlag = ""
                                        $scope.param.productNum = 1
                                        if($scope.param.product.type=='offline')
                                        {
                                            window.location.href = "orderPay.do?productType=" + $scope.param.product.type + "&random="+Math.random();
                                        }
                                        else if($scope.param.product.type=='special')
                                        {
                                            window.location.href = "orderPay.do?productType=" + $scope.param.product.type
                                                + "&specialShopId=" + $rootScope.specialShopId
                                                + "&random="+Math.random();
                                        }
                                    }
                                })
                            }

                        })

                    }
                }else{
                    $scope.model = true
                }
            }

            $scope.addProductNum = function(){
                $scope.param.productNum= $scope.param.productNum+1;
            }

            $scope.minusProductNum = function(){
                if($scope.param.productNum>1){
                    $scope.param.productNum= $scope.param.productNum-1;
                }else{
                    $(".ion-ios-minus-outline").attr('disabled','disabled').addClass("grey");
                }
            }

            var showToast = function (content) {
                $ionicLoading.show({
                    template: content
                });
            }

            var hideToast = function () {
                $timeout(function () {
                    $ionicLoading.hide();
                }, 1000);
            };



            $scope.$on('$ionicView.enter', function(){
                $scope.param = {
                    product:{},
                    productSpec:"",
                    productUnPaidNum : "0",
                    currentIndex:0,
                    totalIndex:0,
                    productNum:1,
                    checkFlag:""
                };
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetOfflineProductDetail.get({productId:$stateParams.productId},function(data){
                    $ionicLoading.hide();
                    $scope.param.product = data.responseData;
                    $ionicSlideBoxDelegate.update();
                    $ionicSlideBoxDelegate.loop(true);
                    $interval(function(){
                        $scope.param.currentIndex =  $ionicSlideBoxDelegate.currentIndex()+1;
                        $scope.param.totalIndex =  $ionicSlideBoxDelegate.slidesCount()
                    },100);
                });

                GetProductNumFromBuyCart.get(function(data){
                    $scope.param.productUnPaidNum = data.responseData;
                });
            });

}])

