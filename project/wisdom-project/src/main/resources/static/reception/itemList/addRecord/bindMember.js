PADWeb.controller('bindMemberCtrl', function ($scope,$rootScope,$stateParams, $state, getBeautyQRCode, getUserScanInfo) {
    console.log($scope);
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.mainSwitch.headerPriceListAllFlag = true;
    $scope.$parent.mainSwitch.headerCashAllFlag = false;
    $scope.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.mainSwitch.headerPriceListBlackFlag = false;
    $scope.$parent.mainSwitch.footerBoxFlag = false;
    $scope.$parent.param.headerPrice.title = "绑定用户";
    $scope.$parent.param.headerPrice.saveContent = ""
    getBeautyQRCode.get({
        shopId: $stateParams.shopId,
        userId: $stateParams.userId
    }, function (data) {
        $scope.qrCodeSrc = data.responseData
    })

    $scope.queryInfo = function () {
        getUserScanInfo.get({
            shopId: $stateParams.shopId,
            sysUserId: $stateParams.userId,
        }, function (data) {
            if(data.result == "0x00001"){
                if(data.responseData == "alreadyBind"){
                    alert("绑定成功")
                    $state.go("pad-web.left_nav.personalFile")
                    clearInterval($rootScope.timeInfo)
                }else if(data.responseData == "notArchives"){
                    alert("请添加档案");
                }else if(data.responseData=="otherUser"){
                    alert("请让本人扫码");
                }
            }
        })
    }

    $rootScope.timeInfo = setInterval(function () {
        $scope.queryInfo()
    },3000)
    /*去其他页面清除定时器*/
    $scope.priceListBlackFn = function () {
        $state.go("pad-web.left_nav.personalFile")
    }
    $scope.$parent.priceListBlackFn = function () {
        window.history.go(-1)
        clearInterval($rootScope.timeInfo)
    }
});