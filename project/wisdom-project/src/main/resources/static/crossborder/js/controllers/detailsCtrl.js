angular.module('controllers',[]).controller('detailsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductDetail'
        ,'AddBorderSpecialProduct2ShoppingCart','CreateBusinessOrder','PutNeedPayOrderListToRedis',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductDetail
            ,AddBorderSpecialProduct2ShoppingCart,CreateBusinessOrder,PutNeedPayOrderListToRedis) {
            console.log("details")
            $scope.params = {
                goodsNum:"1",
                minNum:"1"
            }

            GetBorderSpecialProductDetail.get({
                productId:$stateParams.id
            },function (data) {
                if(data.result == Global.SUCCESS){
                    $scope.goodDetails = data.responseData
                }
            })

            /**/
            $scope.addnum = function () {
                if($scope.params.goodsNum >= $scope.goodDetails.productDetail.productAmount){
                    return
                }
                $scope.params.goodsNum++
            }
            $scope.reducenum = function () {
                if($scope.params.goodsNum <= 1){
                    return
                }
                $scope.params.goodsNum--
            }
            $scope.verifyNum = function () {
                if($scope.params.goodsNum <= 1){
                    $scope.params.goodsNum = 1
                    return
                }
                if($scope.params.goodsNum >= $scope.goodDetails.productDetail.productAmount){
                    $scope.params.goodsNum = $scope.goodDetails.productDetail.productAmount
                    return
                }
            }



            var viewSwiper = new Swiper('.view .swiper-container', {
                onSlideChangeStart: function() {
                    updateNavPosition()
                }
            })

            $('.view .arrow-left,.preview .arrow-left').on('click', function(e) {
                e.preventDefault()
                if (viewSwiper.activeIndex == 0) {
                    viewSwiper.swipeTo(viewSwiper.slides.length - 1, 1000);
                    return
                }
                viewSwiper.swipePrev()
            })
            $('.view .arrow-right,.preview .arrow-right').on('click', function(e) {
                e.preventDefault()
                if (viewSwiper.activeIndex == viewSwiper.slides.length - 1) {
                    viewSwiper.swipeTo(0, 1000);
                    return
                }
                viewSwiper.swipeNext()
            })

            var previewSwiper = new Swiper('.preview .swiper-container', {
                visibilityFullFit: true,
                slidesPerView: 'auto',
                onlyExternal: true,
                onSlideClick: function() {
                    viewSwiper.swipeTo(previewSwiper.clickedSlideIndex)
                }
            })

            function updateNavPosition() {
                $('.preview .active-nav').removeClass('active-nav')
                var activeNav = $('.preview .swiper-slide').eq(viewSwiper.activeIndex).addClass('active-nav')
                if (!activeNav.hasClass('swiper-slide-visible')) {
                    if (activeNav.index() > previewSwiper.activeIndex) {
                        var thumbsPerNav = Math.floor(previewSwiper.width / activeNav.width()) - 1
                        previewSwiper.swipeTo(activeNav.index() - thumbsPerNav)
                    } else {
                        previewSwiper.swipeTo(activeNav.index())
                    }
                }
            }

            // $scope.addShoppingCart  = function () {
            //     AddBorderSpecialProduct2ShoppingCart.get(
            //         {
            //             productId:$stateParams.id,
            //             productNum:$scope.params.goodsNum,
            //             productSpec:$scope.goodDetails.productDetail.spec[0]
            //         },
            //         function (data) {
            //             console.log(data)
            //         }
            //     );
            // }

            $scope.addBuyCart = function(){
                /*根据商品状态来判断商品是否为下架商品*/
                if($scope.goodDetails.status == "0"){
                    return;
                }
                    if($scope.goodDetails.productDetail.spec.length == 1){
                        $scope.checkFlag = $scope.goodDetails.productDetail.spec[0]
                    }
                    if($scope.params.goodsNum=="0"){
                        alert("请选择正确的数量");
                        return;
                    }
                    if($scope.params.goodsNum>$scope.goodDetails.productAmount){
                        alert("库存不足~");
                        return;
                    }
                        AddBorderSpecialProduct2ShoppingCart.get({productId:$stateParams.id,productNum: $scope.params.goodsNum, productSpec:$scope.checkFlag},function(data){
                            if(data.result==Global.FAILURE){
                                alert("加入购物车失败");
                            }else{
                                alert("加入购物车成功");
                            }
                        })

            };

            $scope.goPay = function() {
                /*根据商品状态来判断商品是否为下架商品*/
                if ($scope.goodDetails.status == "0") {
                    return;
                }

                    /*根据用户的等级的商品来判断*/
                    if ($scope.params.goodsNum == "0") {
                        alert("请选择正确的数量");
                        return
                    }
                    /*根据商品数量跟库存的对比，数量大于库存及库存不足，结束这一步*/
                    if ($scope.params.goodsNum > $scope.goodDetails.productAmount) {
                        alert("库存不足~");
                        return;
                    } else {
                        //先将此商品生成订单
                        CreateBusinessOrder.save({
                            businessProductId: $scope.goodDetails.productId,
                            productSpec: $scope.goodDetails.productDetail.spec[0],
                            businessProductNum: $scope.params.goodsNum,
                            type: $scope.goodDetails.type
                        }, function (data) {
                            if (data.result == Global.FAILURE) {
                                alert("交易失败");
                            } else {
                                //生成订单后再直接前往支付页面
                                var needPayOrderList = [];
                                var payOrder = {
                                    orderId: data.responseData,
                                    productFirstUrl: $scope.goodDetails.firstUrl,
                                    productId: $scope.goodDetails.productId,
                                    productName: $scope.goodDetails.productName,
                                    productNum: $scope.params.goodsNum,
                                    productPrice: $scope.goodDetails.price,
                                    productSpec:  $scope.goodDetails.productDetail.spec[0]
                                };
                                needPayOrderList.push(payOrder);
                                //将needPayOrderList数据放入后台list中
                                PutNeedPayOrderListToRedis.save({needPayOrderList: needPayOrderList}, function (data) {
                                    if (data.result == Global.SUCCESS) {
                                        // $state.go('scanPay')
                                        $state.go('orderSubmit')
                                    } else if (data.result == Global.FAILURE) {
                                        alert("购买失败");
                                    }

                                })
                            }
                        })
                    }

            }

        }
    ]);