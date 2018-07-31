angular.module('controllers',[]).controller('orderSubmitCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetNeedPayOrderListToRedis','CheackRealName','CreateSpecialOrderAddressRelation','UpdateBusinessOrderAddress',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetNeedPayOrderListToRedis,CheackRealName,CreateSpecialOrderAddressRelation,UpdateBusinessOrderAddress) {
            console.log("orderSubmitCtrl")
            $scope.authentication_flag = false//弹窗
            $scope.orderIds = new Array();
            $scope.totalPay = 0;
            $scope.params = {
                cardNo:"",
                name:"",
                userAddressInfo :{
                    userNameAddress:"",
                    userPhoneAddress:"",
                    userDetailAddress:"",
                    userCityAddress:"",
                    status:"0"
                }
            }
            $scope.orderAddressRelationDTO = {

            }
            //获取个人订单
            GetNeedPayOrderListToRedis.get({},function (data) {
                if(data.result==Global.SUCCESS)
                {
                    $scope.needPayList = data.responseData;
                    for(var i=0; i<$scope.needPayList.needPayOrderList.length;i++){
                        $scope.orderIds[i] = $scope.needPayList.needPayOrderList[i].orderId;
                        $scope.totalPay += parseInt($scope.needPayList.needPayOrderList[i].productNum)*parseInt($scope.needPayList.needPayOrderList[i].productPrice)
                    }
                }
            })

            $scope.cheackUser = function (data) {
                var loginttoken = window.localStorage.getItem("logintoken");
                $.ajax({
                    url:"/user/customer/queryRealNameAuthentication",// 跳转到 action
                    beforeSend: function(request) {
                        request.setRequestHeader("logintoken", loginttoken);
                    },
                    async:true,
                    type:'get',
                    data:{cardNo:$scope.params.cardNo,name:$scope.params.name,orderIds:$scope.orderIds,specialShopId:""},
                    cache:false,
                    success:function(data) {
                        if($scope.params.name==data.name&&data.result=='匹配')
                        {
                            $scope.authentication_flag = false;
                            CreateSpecialOrderAddressRelation.save($scope.params.userAddressInfo,function (data) {
                                         $state.go('scanPay')
                            })
                        }
                        else
                        {
                            alert("跨境商品收货人的身份证号和姓名不匹配，请重新输入");
                        }
                    }
                })
            }

            $scope.showAuthentication = function () {
                $scope.authentication_flag = true
            }
        }]);