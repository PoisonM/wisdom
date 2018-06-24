PADWeb.controller('selectRechargeTypeCtrl', function($scope, $state, $stateParams, ngDialog, Archives, GetShopUserRecentlyOrderInfo, GetUserRechargeCardList, UpdateVirtualGoodsOrderInfo, SaveShopUserOrderInfo, GetRechargeCardList, UpdateShopUserOrderPayInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "选择充值卡"
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

    Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    Array.prototype.delete = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i].id == val) {
                this.splice(i, 1);
                return
            }
        }
    };

    //获得订单号
    $scope.orderId = $state.params.orderId;
    if ($state.params.type == 1) {
        GetUserRechargeCardList.get({
            sysUserId: $stateParams.userId
        }, function(data) {
            $scope.userPayRechargeCardListCheck = [];
            $scope.totalBalance = data.responseData.totalBalance
            $scope.RechargeCardList = data.responseData.userRechargeCardList;
        })
    } else if ($state.params.type == 2) {
        $scope.type = $state.params.type;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
        GetShopUserRecentlyOrderInfo.get({
            orderId: $scope.orderId,
            sysUserId: $stateParams.userId
        }, function(data) {
            $scope.userPayRechargeCardList = data.responseData.userPayRechargeCardList;
            if(null == $scope.userPayRechargeCardList){
                $scope.userPayRechargeCardList = [];
            }
            $scope.userPayRechargeCardListCheck = [];
            if(null != $scope.userPayRechargeCardList){
                for (var i = 0; i < $scope.userPayRechargeCardList.length; i++) {
                    $scope.userPayRechargeCardListCheck.push($scope.userPayRechargeCardList[i].id);
                }
            }
        })
        GetUserRechargeCardList.get({
            sysUserId: $stateParams.userId
        }, function(data) {
            $scope.RechargeCardList = data.responseData.userRechargeCardList;
        })
        $scope.$parent.$parent.leftTipFn = function() {
            UpdateShopUserOrderPayInfo.save({
                orderId: $scope.orderId,
                userPayRechargeCardList: $scope.userPayRechargeCardList,
            }, function(data) {
                $state.go('pad-web.left_nav.orderList', { orderId: $scope.orderId,userId:$stateParams.userId })
            })
        }

    } else {
        GetRechargeCardList.get({
            pageSize: 100,
            sysUserId: $stateParams.userId
        }, function(data) {
            $scope.RechargeCardListAll = data.responseData;
        })
    }

    $scope.goSelectRechargeCard = function(id, eid, name, price) {
        //控制样式
        if ($state.params.type == 1) {
            $scope.updateVirtualGoodsOrderInfo(id, eid, name, function() {
                $state.go('pad-web.left_nav.makeSureOrder', { orderId: $scope.orderId,userId:$stateParams.userId });
            });

        } else if ($state.params.type == 2) {
            if ($scope.userPayRechargeCardListCheck.indexOf(eid) == -1) {
                $scope.userPayRechargeCardList.push({ id: eid, initAmount: price, shopRechargeCardName: name, })
                $scope.userPayRechargeCardListCheck.push(eid);
            } else {
                $scope.userPayRechargeCardList.delete(eid);
                $scope.userPayRechargeCardListCheck.remove(eid);
            }

        } else {
            $state.go('pad-web.left_nav.selectRechargeCard', {
                type: id,
                userId:$stateParams.userId
            });
        }
    }

    $scope.updateVirtualGoodsOrderInfo = function(id, eid, name, callback) {
        UpdateVirtualGoodsOrderInfo.save({
            goodsType: '2',
            orderId: $state.params.orderId,
            shopUserRechargeCardDTO: {
                id: eid,
                shopRechargeCardId: eid,
                shopRechargeCardName: name,
            },
        }, function(data) {
            console.log(data)
            callback && callback();
        })
    }
    
    $scope.$parent.$parent.backHeaderCashFn = function () {
        // $state.go("pad-web.left_nav.personalFile")
        window.history.go(-1)
    }
});