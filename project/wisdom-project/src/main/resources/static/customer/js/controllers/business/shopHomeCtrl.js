angular.module('controllers',[]).controller('shopHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHomeBannerList','GetOfflineProductList','$ionicSlideBoxDelegate',
        '$ionicLoading','GetBusinessOrderByProductId','Global','$ionicPopup',
        'LoginGlobal','BusinessUtil','CheckTripleMonthBonus','GetTripleMonthBonus','FindProductById','FindProductBargainPriceTimeById','GetUserInfoByOpenId',
        function ($scope,$rootScope,$stateParams,$state,GetHomeBannerList,GetOfflineProductList,$ionicSlideBoxDelegate,
                  $ionicLoading,GetBusinessOrderByProductId,Global,$ionicPopup,
                  LoginGlobal,BusinessUtil,CheckTripleMonthBonus,GetTripleMonthBonus,FindProductById,FindProductBargainPriceTimeById,GetUserInfoByOpenId) {
            $rootScope.title = "美享99触屏版";
            $scope.params="all";/*一进入商城页面默认全部*/
            $scope.param = {
                bannerList:{},
                productList:{},//特殊商品
                product2List:[[]],//普通商品
                promoteProduct:true,
                /*promoteProductId:"MXT99-04",*/
                rookieProduct:true,
               /* rookieProductId:"201712101718100007",*/
                redPackerFlagOne:false,
                redPackerFlagTwo:false,
                redPackerBox:true,
                bonusValue:"",
                timeContent:"",
                checkType:"0"
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
                    /*轮播图排序 按照后台返回来的那个值*/
                    $ionicSlideBoxDelegate.update();
                    $ionicSlideBoxDelegate.loop(true);
                });

                GetOfflineProductList.save({pageNo:0,pageSize:100},function(data){
                    $ionicLoading.hide();
                    $scope.param.checkType="0";
                    $scope.param.productList = data.responseData;
              /*      var partNames = [];
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
                    /!**!/
                    var tempArr = [];
                    for(var i = 0; i < $scope.param.product2List.length; i++){
                        if($scope.param.product2List[i].data[0].productId == "201712101718100007"||$scope.param.product2List[i].data[0].productId == "88888888888"){
                        }else{
                            tempArr.push($scope.param.product2List[i])
                        }
                    }
                    $scope.param.product2List = tempArr;
                    console.log( $scope.param.product2List)*/
                    $scope.repeatList = [];
                    $scope.onePriductList = [];
                    $scope.twoPriductList = [];
                    $scope.threePriductList = [];
                    $scope.fourPriductList = [];
                    $scope.fivePriductList = [];
                    $scope.allPriductList = $scope.param.productList;
                    $scope.repeatList=$scope.param.productList;/*一进入商城默认全部商城*/

                    for(var i = 0; i < $scope.param.productList.length; i++){
                        if($scope.param.productList[i].price == "99.00"){
                            $scope.onePriductList.push($scope.param.productList[i])
                        }else if($scope.param.productList[i].price == "199.00"){
                            $scope.twoPriductList.push($scope.param.productList[i])
                        }else if($scope.param.productList[i].price == "299.00"){
                            $scope.threePriductList.push($scope.param.productList[i])
                        }else if($scope.param.productList[i].price == "399.00"){
                            $scope.fourPriductList.push($scope.param.productList[i])
                        }else if($scope.param.productList[i].price == "499.00"){
                            $scope.fivePriductList.push($scope.param.productList[i])
                        }
                    }

                })

               $scope.checkBg=function (checkType) {
                $scope.param.checkType=checkType;
               }


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
/*
                GetBusinessOrderByProductId.get({productId:$scope.param.rookieProductId},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.rookieProduct=true;
                    }
                    else if(data.result==Global.FAILURE)
                    {
                        $scope.param.rookieProduct=false;
                    }
                });*/

                //判断用户是否是3月的B店店主,如果是，则弹出红包页
              /*  CheckTripleMonthBonus.get(function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.redPackerFlagOne = true;
                        $scope.param.redPackerFlagTwo = false;
                        $scope.param.bonusLeftDay = data.responseData;
                        console.log($scope.param.bonusLeftDay);
                    }
                })*/
            });

            $scope.clickCarousel=function(item){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_BADJ,item);
            };
            $scope.enterDetails=function(item){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ADJ,item);
               /* if(item=="201712101718100004")
                {
                    var alertPopup = $ionicPopup.alert({
                        template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">正在补货中，敬请期待</span>',
                        okText:'确定'
                    });
                    return
                }*/
                $state.go("offlineProductDetail",{productId:item})
            };

            $scope.goPromoteProduct = function(item){
                BusinessUtil.twoParameters(LoginGlobal.MX_SC_ADJ,item);
                $state.go("offlineProductDetail",{productId:item});

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

            /*$scope.redPackerOpen = function () {
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
*/

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

            $scope.allProductFlag = true;
            $scope.checkPrice = function (price) {
                $scope.params=price;
                if(price=='all'){
                    // $scope.repeatList =  $scope.allPriductList
                    $scope.allProductFlag = true

                }else if(price == 99){
                    $scope.repeatList = $scope.onePriductList;
                    $scope.allProductFlag = false
                }else if(price == 199){
                    $scope.repeatList = $scope.twoPriductList;
                    $scope.allProductFlag = false
                }else if(price == 299){
                    $scope.repeatList = $scope.threePriductList;
                    $scope.allProductFlag = false
                }else if(price == 399){
                    $scope.repeatList = $scope.fourPriductList;
                    $scope.allProductFlag = false
                }else if(price == 499){
                    $scope.repeatList = $scope.fivePriductList;
                    $scope.allProductFlag = false
                }
                console.log( $scope.repeatList)
            }
        }]);
