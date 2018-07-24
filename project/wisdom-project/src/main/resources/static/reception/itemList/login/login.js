PADWeb.controller('loginCtrl', function ($scope,$state,$stateParams, ngDialog, BeautyLogin, GetUserValidateCode) {

    /*------------------------------------------头部开关--------------------------------------------------*/

    $scope.$parent.mainSwitch.headerLoginFlag = true
    $scope.$parent.mainSwitch.headerReservationAllFlag = false
    $scope.$parent.mainSwitch.headerCashAllFlag = false
    $scope.$parent.mainSwitch.footerBoxFlag = false
    $scope.$parent.mainSwitch.headerPriceListAllFlag = false
    $scope.param = {
        code: "",
        phone: "",
        timeContent:"获取",
        codeFlag:true//60s内不可再点
    }

    $scope.getCode = function () {
        if(!$scope.param.codeFlag){
            return false
        }else {
            $scope.param.codeFlag = false
            $scope.param.timeContent = 5
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
                            $scope.param.timeContent = "获取"
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

                //店员返回user和clerk
                if(data.responseData.beautyClerkLoginToken != "0x00006" && data.responseData.beautyUserLoginToken != "0x00006"){
                    alert("登录成功")
                    setTimeout(function () {
                        $state.go("pad-web.userInfo.todayPerformance")
                    },1000)
                }else {
                    alert("请使用店员帐号登录")
                }
            }
            if(data.result == "0x00015"){
                alert("账号已停用，请联系客服，谢谢")
            }
        })
    }

});