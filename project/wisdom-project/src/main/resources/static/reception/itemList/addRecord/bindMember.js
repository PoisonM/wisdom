PADWeb.controller('bindMemberCtrl', function ($scope,$stateParams, $state, getBeautyQRCode, getUserScanInfo) {
    console.log($scope);
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.mainSwitch.headerPriceListAllFlag = true
    $scope.$parent.mainSwitch.headerCashAllFlag = false
    $scope.$parent.mainSwitch.headerReservationAllFlag = false
    $scope.$parent.mainSwitch.headerPriceListBlackFlag = false
    $scope.$parent.mainSwitch.footerBoxFlag = false
    $scope.$parent.param.headerPrice.title = "绑定用户"
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
                alert("SUCCESS")
                clearInterval($scope.timeInfo)
            }
            if(data.result == "0x00002"){
                alert("FAILURE")
            }
        })
    }

    $scope.timeInfo = setInterval(function () {
        $scope.queryInfo()
    },3000)

});