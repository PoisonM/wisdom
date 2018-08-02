angular.module('controllers', []).controller('loginCtrl',
    ['$scope', '$interval', '$rootScope', '$stateParams', '$state', 'Global', '$timeout', 'GetUserValidateCode','UserLogin',
        function ($scope, $interval, $rootScope, $stateParams, $state, Global, $timeout, GetUserValidateCode,UserLogin) {

            $scope.param = {
                code: "",
                phone: "",
                timeContent:"发送验证码",
                codeFlag:true//60s内不可再点
            }
            $scope.getCode = function () {
                if(!$scope.param.codeFlag){
                    return false
                }else {
                    $scope.param.codeFlag = false
                    $scope.param.timeContent = 60
                    if($scope.param.phone == ""){
                        alert("请输入手机号")
                        return
                    }
                    GetUserValidateCode.get({mobile: $scope.param.phone}, function (data) {
                        if (data.result == "0x00001") {
                            $scope.timer = setInterval(function () {
                                $scope.param.timeContent--
                                $scope.$apply();
                                if($scope.param.timeContent <= 0){
                                    $scope.param.codeFlag = true
                                    $scope.param.timeContent = "发送验证码"
                                    $scope.$apply();
                                    clearInterval($scope.timer)
                                }
                            },1000)
                        }
                    })
                }
            }


            $scope.login = function () {
                if($scope.param.phone == ""){
                    alert("请输入手机号")
                    return
                }
                if($scope.param.code == ""){
                    alert("请输入密码")
                    return
                }
                UserLogin.save({
                    userPhone: $scope.param.phone,
                    code: $scope.param.code
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    }
                    else {
                        alert(data.errorInfo);
                        $state.go("goodsList")
                        window.localStorage.removeItem("logintoken");
                        window.localStorage.setItem("logintoken", data.responseData);
                        sessionStorage.removeItem("logintoken");
                        sessionStorage.setItem("logintoken", data.responseData);
                        //存入手机号
                        window.localStorage.removeItem("loginPhone");
                        window.localStorage.setItem("loginPhone", $scope.param.phone);
                    }
                })
            }

        }]);