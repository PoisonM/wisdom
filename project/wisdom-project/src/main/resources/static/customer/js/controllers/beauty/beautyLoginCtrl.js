angular.module('controllers',[]).controller('loginCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBeautyUserValidateCode','$interval',
        'Global','UserLogin','$ionicPopup','LoginGlobal','BeautyUtil',
        function ($scope,$rootScope,$stateParams,$state,GetBeautyUserValidateCode,$interval,
                  Global,UserLogin,$ionicPopup,LoginGlobal,BeautyUtil) {

            $rootScope.title = "美享登录";

            $scope.param = {
                userPhone:'',
                validateCode:'',
                validateCodeButtonStatus:true,
                timeCount: 60
            }

            $scope.getValidateCode = function(){

                $scope.param.validateCodeButtonStatus = false;
                $scope.param.timeCount = 60;

                //每隔一秒执行
                var timer= $interval(function(){
                    $scope.param.timeCount--;
                    if($scope.param.timeCount<0){
                        $interval.cancel(timer);
                        $scope.param.validateCodeButtonStatus = true;
                    }
                },1000);

                GetBeautyUserValidateCode.get({mobile:$scope.param.userPhone},function(data){
                    if(data.result == Global.FAILURE)
                    {
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">验证码获取失败</span>',
                            okText:'确定'
                        });
                    }
                })
          }

            $scope.userLogin = function(){

                if($scope.param.validateCode=='')
                {
                    var alertPopup = $ionicPopup.alert({
                        template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">请输入验证码</span>',
                        okText:'确定'
                    });
                }
                else
                {
                    UserLogin.save({userPhone:$scope.param.userPhone,code:$scope.param.validateCode},function(data){
                        if(data.result==Global.FAILURE)
                        {
                            alert(data.errorInfo);
                        }
                        else
                        {
                            window.localStorage.removeItem("beautyLogintoken");
                            window.localStorage.setItem("beautyLogintoken",data.responseData);

                            if($stateParams.redirectUrl=='')
                            {
                                window.location.href = "";
                            }
                            else
                            {
                                if($stateParams.redirectUrl.indexOf("businessOrderPay")==0)
                                {
                                    $state.go("buyCart");
                                }
                                else
                                {
                                    window.location.href = "#/" + $stateParams.redirectUrl.replace("&","/");
                                }
                            }
                        }
                    })
                }
            }

        }])