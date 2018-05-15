PADWeb.controller('selectRechargeTypeCtrl', function($scope, $state, $stateParams, ngDialog, Archives, GetUserRechargeCardList, UpdateVirtualGoodsOrderInfo, SaveShopUserOrderInfo, GetRechargeCardList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "充值记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
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

    //获得订单号
    SaveShopUserOrderInfo.save({
        userId: '110'
    }, function(data) {
        $scope.orderId = data.responseData;
    })

    $scope.goSelectRechargeCard = function(id, eid, name) {
        if ($state.params.type == 1) {
            $scope.updateVirtualGoodsOrderInfo(id, eid, name, ($state.go('pad-web.left_nav.makeSureOrder')));

        } else {
            $state.go('pad-web.left_nav.selectRechargeCard', { type: id });
        }
    }

    console.log($state.params.type)

    if ($state.params.type == 1) {
        GetUserRechargeCardList.get({
            sysUserId: 110
        }, function(data) {
            $scope.RechargeCardList = data.responseData;
        })
    } else {
        GetRechargeCardList.get({
            pageSize: 100,
        }, function(data) {
            $scope.RechargeCardListAll = data.responseData;
        })
    }

    $scope.updateVirtualGoodsOrderInfo = function(id, eid, name, callback) {
        UpdateVirtualGoodsOrderInfo.save({
            goodsType: '2',
            orderId: $scope.orderId,
            shopUserRechargeCardDTO: {
                id: eid,
                shopRechargeCardId: eid,
                shopRechargeCardName: name,
            },
        }, function(data) {
            console.log(data)

        })
    }
});