angular.module('controllers', []).controller('loginCtrl',
    ['$scope', '$interval', '$rootScope','$ionicPopup', '$stateParams', '$state', 'Global', '$timeout', 'GetUserValidateCode','UserLogin',
        function ($scope, $interval, $rootScope,$ionicPopup, $stateParams, $state, Global, $timeout, GetUserValidateCode,UserLogin) {

            $scope.getValidateCode = function () {
                var phone = $scope.param.userPhone;
                if (phone != "") {
                    $scope.param.validateCodeButtonStatus = false;
                    $scope.param.timeCount = 60;

                    //每隔一秒执行
                    var timer = $interval(function () {
                        $scope.param.timeCount--;
                        if ($scope.param.timeCount < 0) {
                            $interval.cancel(timer);
                            $scope.param.validateCodeButtonStatus = true;
                        }
                    }, 1000);
                    GetUserValidateCode.get({mobile: phone}, function (data) {
                        if (data.result == Global.FAILURE) {
                            var alertPopup = $ionicPopup.alert({
                                template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">验证码获取失败</span>',
                                okText: '确定'
                            });
                        }
                    })
                } else {
                    alert("手机号不能为空！");
                }
            }

            $scope.userLogin = function () {
                var phone = $scope.param.userPhone;
                if (phone != "") {
                    if ($scope.param.validateCode == '') {
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">请输入验证码</span>',
                            okText: '确定'
                        });
                    }
                    else {
                        UserLogin.save({
                            userPhone: $scope.param.userPhone,
                            code: $scope.param.validateCode
                        }, function (data) {
                            if (data.result == Global.FAILURE) {
                                alert(data.errorInfo);
                            }
                            else {
                                window.localStorage.removeItem("logintoken");
                                window.localStorage.setItem("logintoken", data.responseData);
                                sessionStorage.removeItem("logintoken");
                                sessionStorage.setItem("logintoken", data.responseData);

                                if ($stateParams.redirectUrl == '') {
                                    window.location.href = "";
                                }else {
                                    if ($stateParams.redirectUrl.indexOf("businessOrderPay") == 0) {
                                        // $state.go("buyCart");
                                    }
                                    else {
                                        // window.location.href = "#/" + $stateParams.redirectUrl.replace("&", "/");
                                    }
                                }
                            }
                        })
                    }
                } else {
                    alert("手机号不能为空");
                }

            }
        }]);