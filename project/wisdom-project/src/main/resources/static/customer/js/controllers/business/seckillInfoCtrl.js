var seckillInfo = angular.module('controllers',[]).controller('seckillInfoCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicPopup',
        '$ionicSlideBoxDelegate','$ionicLoading',"$interval",'$timeout','IsLogin','SeckillInfo','CreateSeckillOrder','Global','PutNeedPayOrderListToRedis',
        function ($scope,$rootScope,$stateParams,$state,$ionicPopup,
                  $ionicSlideBoxDelegate,$ionicLoading,$interval,$timeout,IsLogin,SeckillInfo,CreateSeckillOrder,Global,PutNeedPayOrderListToRedis) {

            $rootScope.title = "秒杀详情";
            $scope.model=false;
            $scope.myObj = {
                background:"red",
                padding: "5px 20px",
            };
            $scope.showFlag = function (type) {
                $scope.model = type;
                if(!type){
                    $scope.param.checkFlag=""
                }
            };

            $scope.confirmProductSpec = function(spec) {
                $scope.param.checkFlag = spec
            };

            $scope.concealment=function () {
                $scope.showFlag(false);
                $scope.param.checkFlag = "";
                $scope.param.productNum = 1
            };

            $scope.chooseSpec = function () {
                $scope.model = true
            };

            $scope.viewInstructions=function(){
                $scope.explain= true;
            };

            $scope.know=function(){
                $scope.explain=false;
            };

            $scope.goPay = function(){
                $scope.loginCart();
                /*根据商品状态来判断商品是否为下架商品*/
                if($scope.model){
                    if($scope.param.checkFlag=="")
                    {
                        $scope.model=true;
                    }
                        if($scope.param.productNum=="0"){
                            alert("请选择正确的数量");
                            return
                        }
                        if($scope.param.productNum>$scope.param.product.productAmount){
                            alert("库存不足~");
                            return;
                        }
                        else
                        {

                            showToast("加载中");
                            CreateSeckillOrder.save(
                                {businessProductId:$scope.param.product.productId,
                                productSpec:$scope.param.checkFlag,
                                businessProductNum: $scope.param.productNum,
                                type:$scope.param.product.productType,
                                id:$scope.param.product.fieldId+"",
                                },function (data) {
                                if(data.result==Global.FAILURE)
                                {
                                    showToast("交易失败");
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
                                        productSpec:$scope.param.checkFlag
                                    };
                                    needPayOrderList.push(payOrder);
                                    //将needPayOrderList数据放入后台list中
                                    PutNeedPayOrderListToRedis.save({needPayOrderList:needPayOrderList},function(data){
                                        if(data.result==Global.SUCCESS)
                                        {
                                            hideToast()
                                            $scope.showFlag(false);
                                            $scope.param.checkFlag = "";
                                            $scope.param.productNum = 1;
                                            window.location.href = "orderPay.do?productType=seckill&random="+Math.random();
                                        }else if(data.result==Global.FAILURE){
                                            alert("购买失败");
                                            hideToast()
                                            $scope.showFlag(false);
                                        }

                                    })
                                }
                            })

                        }
                        }
                }

            $scope.addProductNum = function(){
                $scope.param.productNum=$scope.param.productNum+1;
                if($scope.param.productNum>$scope.param.product.productNum){
                    $("#Car").css("background","grey");
                    $("#goPay").css("background","grey");
                    $(".ion-ios-minus-outline").attr('disabled','disabled').addClass("grey");
                }else{
                    $("#goPay").css("background","red");
                }
            };

            $scope.minusProductNum = function(){
                if($scope.param.productNum>1){
                    $scope.param.productNum= $scope.param.productNum-1;
                }else{
                    $(".ion-ios-minus-outline").attr('disabled','disabled').addClass("grey");
                }
                if($scope.param.productNum<=$scope.param.product.productNum){
                    $("#goPay").css("background","red");
                }else{
                    $("#goPay").css("background","grey");
                }
            };


            var showToast = function (content) {
                $ionicLoading.show({
                    template: content
                });
            };

            var hideToast = function () {
                $timeout(function () {
                    $ionicLoading.hide();
                }, 1000);
            };

            $scope.loginCart = function(){
                IsLogin.save(function(data){
                    if(data.responseData=="failure"){
                        showToast("请先登录账号");
                        hideToast();
                        $state.go("login");
                    }
                })
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

                SeckillInfo.get({activtyId:$stateParams.id+""},function (data){
                    $ionicLoading.hide();
                    $scope.param.product = data;
                    $scope.param.checkFlag = $scope.param.product.productDetail.spec[0];
                    if($scope.param.product.productNum <= 0){
                        $("#add").css("background","grey");
                        $("#go").css("background","grey");
                    }
                    $ionicSlideBoxDelegate.update();
                    $ionicSlideBoxDelegate.loop(true);
                    $interval(function(){
                        $scope.param.currentIndex =  $ionicSlideBoxDelegate.currentIndex()+1;
                        $scope.param.totalIndex =  $ionicSlideBoxDelegate.slidesCount()
                    },100);
                    console.log(data)
                })

            })

        }])


seckillInfo.directive('timerBtn', function() { // 倒计时按钮
    return {
        restrict: 'A',
        replace: true,
        scope: {
            startTime: '=startTime',
            getData: '&getData'
        },
        template: '<span class="btn btn-danger" ng-disabled="startTime> 0" ng-bind="startTime > 0 ? \'距离活动结束:\' +showTime : \'\'" ng-click="getData()"></span>',
        controller: function($scope, $interval) {
            var formatTime = function(sys_second) {
                if (sys_second > 0) {
                    sys_second -= 1;
                    var day = Math.floor((sys_second / 3600) / 24);
                    if (day < 0) {
                        day = 0;
                    }
                    var hour = Math.floor((sys_second / 3600) % 24);
                    if (hour < 0) {
                        hour = 0;
                    }
                    var minute = Math.floor((sys_second / 60) % 60);
                    if (minute < 0) {
                        minute = 0;
                    }
                    var second = Math.floor(sys_second % 60);
                    if (second < 0) {
                        second = 0;
                    }
                    return (hour < 10 ? "0" + hour : hour) + "小时 " + (minute < 10 ? "0" + minute : minute) + "分钟" + (second < 10 ? "0" + second : second)+"秒";
                }
            }

            var timer = $interval(function() {
                $scope.startTime -= 1;
                $scope.showTime = formatTime($scope.startTime);
                if($scope.startTime < 1) {
                    $interval.cancel(timer);
                };
            }, 1000);

        }
    };
});