PADWeb.controller('orderListCtrl', function($scope, $stateParams, $state, ngDialog, Archives, GetShopUserRecentlyOrderInfo, UserPayOpe) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "消费";
    $scope.$parent.$parent.param.headerCash.leftTip = "添加更多";
    $scope.$parent.$parent.param.headerCash.title = "消费"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.flagFn = function(bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool;
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool;
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool;
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

    $scope.$parent.param.selectSty = $stateParams.userId//选中店员控制样式

    $scope.goSignConfirm = function() {
        if($scope.payType == undefined){
            $scope.payType = ""
        }
        if($scope.detail == undefined){
            $scope.detail = ""
        }
        UserPayOpe.save({
            balancePay: $scope.balancePay,
            cashPayPrice: $scope.cashPayPrice,
            orderId: $state.params.orderId,
            payType: $scope.payType,
            // shopUserRechargeCard: $scope.responseData.userPayRechargeCardList,
            surplusPayPrice: $scope.surplusPrice,
            detail: $scope.detail
        }, function(data) {
            $state.go('pad-web.signConfirm', { orderId: $state.params.orderId,userId:$stateParams.userId })
        })
    }

    $scope.checkBoxChek = function(e) {
        $scope.payType = e;
    }

    GetShopUserRecentlyOrderInfo.get({
        sysUserId: $stateParams.userId,
        orderId: $state.params.orderId,
    }, function(data) {
        $scope.responseData = data.responseData;
        $scope.balancePay = $scope.responseData.orderPrice//默认显示
        $scope.countPrice()
    })

    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType', { type: 2 })
    }


    $scope.cashPayPrice = 0//默认显示
    $scope.countPrice = function () {
        //计算剩余支付
        $scope.surplusPrice = $scope.responseData.orderPrice - (parseInt($scope.balancePay) + parseInt($scope.cashPayPrice))

    }


    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }
});