PADWeb.controller('loginCtrl', function ($scope, $stateParams, ngDialog, BeautyLogin, GetUserValidateCode) {

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
    // if($scope.codeFlag){

        $scope.getCode = function () {
            if($scope.param.phone == ""){
                alert("请输入手机号")
                return
            }

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
    // }


    $scope.login = function () {
        if($scope.param.phone == ""){
            alert("请输入手机号")
            return
        }
        if($scope.param.code == ""){
            alert("请输入验证码")
            return
        }
        BeautyLogin.save({
            userPhone: $scope.param.phone,
            code: $scope.param.code
        }, function (data) {
            if (data.result == "0x00001") {
                if(data.responseData.beautyUserLoginToken!="0x00006")
                {
                    window.localStorage.removeItem("beautyUserLoginToken");
                    window.localStorage.setItem("beautyUserLoginToken",data.responseData.beautyUserLoginToken);
                }
                if(data.responseData.beautyBossLoginToken!="0x00006")
                {
                    window.localStorage.removeItem("beautyBossLoginToken");
                    window.localStorage.setItem("beautyBossLoginToken",data.responseData.beautyBossLoginToken);
                }
                if(data.responseData.beautyClerkLoginToken!="0x00006")
                {
                    window.localStorage.removeItem("beautyClerkLoginToken");
                    window.localStorage.setItem("beautyClerkLoginToken",data.responseData.beautyClerkLoginToken);
                }
            }
        })
    }

});