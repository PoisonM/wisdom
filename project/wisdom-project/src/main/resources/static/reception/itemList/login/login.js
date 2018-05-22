PADWeb.controller('loginCtrl', function ($scope, $stateParams, ngDialog, ClerkLogin, GetUserValidateCode) {

    /*------------------------------------------头部开关--------------------------------------------------*/

    $scope.$parent.mainSwitch.headerLoginFlag = true
    $scope.$parent.mainSwitch.headerReservationAllFlag = false
    $scope.$parent.mainSwitch.headerCashAllFlag = false
    $scope.param = {
        code: "",
        phone: "",
        timeContent:"获取",
        codeFlag:true
    }
    if($scope.codeFlag){
        if($scope.param.phone == ""){
            alert("请输入手机号")
            return
        }
        $scope.getCode = function () {
            $scope.codeFlag = false
            $scope.param.timeContent = 60
            GetUserValidateCode.get({mobile: $scope.param.phone}, function (data) {
                if (data.result == "0x00001") {
                    $scope.timer = setInterval(function () {
                        $scope.param.timeContent--
                        $scope.$apply();
                        if($scope.param.timeContent <= 0){
                            $scope.codeFlag = true
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
            alert("请输入验证码")
            return
        }
        ClerkLogin.save({
            userPhone: $scope.param.phone,
            code: $scope.param.code
        }, function (data) {
            if (data.result == "0x00001") {
                window.localStorage.removeItem("beautylogintoken");
                window.localStorage.setItem("beautylogintoken", data.responseData);
            }
        })
    }

});