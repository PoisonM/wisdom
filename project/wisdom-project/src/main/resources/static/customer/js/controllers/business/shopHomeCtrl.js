angular.module('controllers',[]).controller('shopHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHomeBannerList','GetOfflineProductList','$ionicSlideBoxDelegate',
        '$ionicLoading','GetBusinessOrderByProductId','Global','$ionicPopup',
        'LoginGlobal','BusinessUtil','CheckTripleMonthBonus','GetTripleMonthBonus','FindProductById','FindProductBargainPriceTimeById',
        function ($scope,$rootScope,$stateParams,$state,GetHomeBannerList,GetOfflineProductList,$ionicSlideBoxDelegate,
                  $ionicLoading,GetBusinessOrderByProductId,Global,$ionicPopup,
                  LoginGlobal,BusinessUtil,CheckTripleMonthBonus,GetTripleMonthBonus,FindProductById,FindProductBargainPriceTimeById) {
            $rootScope.title = "美享99触屏版";
            $scope.param = {
                bannerList:{},
                productList:{},//特殊商品
                product2List:[[]],//普通商品
                promoteProduct:true,
                promoteProductId:"MXT99-04",
                rookieProduct:true,
                rookieProductId:"201712101718100007",
                redPackerFlagOne:false,
                redPackerFlagTwo:false,
                redPackerBox:true,
                bonusValue:"",
                timeContent:""
            };
            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });

                GetHomeBannerList.save(function(data){
                    $scope.param.bannerList = data.responseData;
                    $ionicSlideBoxDelegate.update();
                    $ionicSlideBoxDelegate.loop(true);
                });

                GetOfflineProductList.save({pageNo:0,pageSize:100},function(data){
                    $ionicLoading.hide();
                    $scope.param.productList = data.responseData;
                    var partNames = [];
                    angular.forEach($scope.param.productList,function(value1,index1){
                        var same = false;
                        angular.forEach(partNames,function(value2,index){
                            if(value2==parseInt(value1.price)) {
                                same = true;
                            }
                        });
                        if(!same) {
                            partNames.push(parseInt(value1.price))
                        }
                    });
                    partNames.sort(function(a,b){return a-b});
                    var index=0;
                    angular.forEach(partNames,function (value1,index1) {
                        $scope.param.product2List[index] = {
                            name:value1,
                            data:[]
                        };
                        angular.forEach($scope.param.productList,function(value2,index2){
                            if(value1==parseInt(value2.price)) {
                                $scope.param.product2List[index].data.push(value2);
                            }
                        });
                        index++;
                    });
                    console.log( $scope.param.product2List);
                    /**/
                    var tempArr = [];
                    for(var i = 0; i < $scope.param.product2List.length; i++){
                        if($scope.param.product2List[i].data[0].productId == "201712101718100007"||$scope.param.product2List[i].data[0].productId == "88888888888"){
                        }else{
                            tempArr.push($scope.param.product2List[i])
                        }
                    }
                    $scope.param.product2List = tempArr;
                    console.log( $scope.param.product2List)

                })



                //判断用户是否购买过新人大礼包产品
                /*GetBusinessOrderByProductId.get({productId:$scope.param.promoteProductId},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.promoteProduct = true;
                    }
                    else if(data.result==Global.FAILURE)
                    {
                        $scope.param.promoteProduct = false;
                    }
                })*/

                GetBusinessOrderByProductId.get({productId:$scope.param.rookieProductId},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.rookieProduct=true;
                    }
                    else if(data.result==Global.FAILURE)
                    {
                        $scope.param.rookieProduct=false;
                    }
                })

                //判断用户是否是3月的B店店主,如果是，则弹出红包页
                CheckTripleMonthBonus.get(function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.redPackerFlagOne = true;
                        $scope.param.redPackerFlagTwo = false;
                        $scope.param.bonusLeftDay = data.responseData;
                        console.log($scope.param.bonusLeftDay);
                    }
                })
            });

            $scope.clickCarousel=function(item){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_BADJ,item);
            };

            $scope.enterDetails=function(item2){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ADJ,item2);
                if(item2=="201712101718100004")
                {
                    var alertPopup = $ionicPopup.alert({
                        template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">正在补货中，敬请期待</span>',
                        okText:'确定'
                    });
                    return
                }
                $state.go("offlineProductDetail",{productId:item2})
            };

            $scope.goPromoteProduct = function(item){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ADJ,item);
                $state.go("offlineProductDetail",{productId:$scope.param.promoteProductId});

               /* if($scope.param.promoteProduct&&$scope.param.promoteProductId==item)
                {
                    var alertPopup = $ionicPopup.alert({
                        template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">你已经购买过促销产品，不能再次购买</span>',
                        okText:'确定'
                    });
                }
                else if(!$scope.param.promoteProduct&&$scope.param.promoteProductId==item)
                {
                    $state.go("offlineProductDetail",{productId:$scope.param.promoteProductId});

                }*/

               /* if($scope.param.rookieProduct&&$scope.param.rookieProductId==item)
                {
                    var alertPopup = $ionicPopup.alert({
                        template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">你已经购买过促销产品，不能再次购买</span>',
                        okText:'确定'
                    });
                }
                else if(!$scope.param.rookieProduct&&$scope.param.rookieProductId==item)
                {
                    $state.go("offlineProductDetail",{productId:$scope.param.rookieProductId});

                }*/

            };

            $scope.redPackerClose = function () {

              $scope.param.redPackerBox=false;
              /*  $scope.param.redPackerFlagOne = false;
                $scope.param.redPackerFlagTwo = false*/
            };

            $scope.redPackerOpen = function () {
                //可以领取
                if($scope.param.redPackerFlagOne)
                {
                    //看看用户能否获取红包
                    GetTripleMonthBonus.get(function(data){

                        if(data.result==Global.SUCCESS)
                        {
                            $scope.param.redPackerFlagTwo = true;
                            $scope.param.bonusValue = data.responseData;
                            console.log($scope.param.bonusValue);
                        }
                        else if(data.result==Global.FAILURE)
                        {
                            $state.go("shopActivity");
                        }

                    });
                }
            }


            function convertDateFromString(dateString) {
                if (dateString) {
                    var arr1 = dateString.split(" ");
                    var sdate = arr1[0].split('-');
                    var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
                    return date;
                }
            }

            FindProductBargainPriceTimeById.get({
                productId:'MXT99-02'
            },function (data) {

                //当前时间
                $scope.nowTime =convertDateFromString(data.responseData.nowTime).getTime();
                //下架时间
                $scope.soldOutTime = convertDateFromString( data.responseData.soldOutTime).getTime();

                timeInterval($scope.nowTime,$scope.soldOutTime)
            });
            function timeInterval(nowTime,soldOutTime){
                var timer = setInterval(function () {
                    if (isNaN(soldOutTime) || isNaN(nowTime)) {
                        return
                    } else {
                        nowTime += 1000;
                        var limit = (soldOutTime - nowTime) / 1000;
                        var resultD = parseInt(limit / (24 * 60 * 60));
                        var resultH = parseInt(limit / (60 * 60) % 24);
                        var resultM = parseInt(limit / 60 % 60) + resultH * 60;
                        var resultS = parseInt(limit % 60);
                        $scope.param.timeContent = resultD;
                        if(limit <= 0){
                            $scope.param.timeContent = 0;
                            clearInterval(timer)
                        }
                    }

                }, 1000)
            }


        }]);
