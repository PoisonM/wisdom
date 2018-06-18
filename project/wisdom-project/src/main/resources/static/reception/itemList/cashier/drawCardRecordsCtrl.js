PADWeb.controller('drawCardRecordsCtrl', function($scope, $state, $stateParams, ngDialog, cashConsumes) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)"
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案"
    $scope.$parent.$parent.param.headerCash.backContent = "返回"
    $scope.$parent.$parent.param.headerCash.title = "划卡记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false
    $scope.flagFn = function(bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

    cashConsumes.save({
        consumeType: "1",
        goodsType: "5",
        sysUserId: $stateParams.userId,
        pageSize: "100"
    }, function(data) {
        $scope.itemList = data.responseData
    })

    $scope.goDrawCardRecordsDetail = function (flowNo) {
        $state.go("pad-web.left_nav.drawCardRecordsDetail",{
            userId:$stateParams.userId,
            flowNo:flowNo
        })
    }
});