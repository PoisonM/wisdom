PADWeb.controller('orderListCtrl', function($scope, $stateParams, $state, ngDialog, Archives, GetShopUserRecentlyOrderInfo, UserPayOpe,UpdateShopUserOrderPayInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
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
        
        if($scope.surplusPrice<0){
            alert("剩余支付小于0元，请核对(^_^)");
            return false;
        }
        if($scope.responseData.availableBalance < $scope.balancePay){
            alert("余额支付金额不能大约可用余额，请重新输入");
            return false
        }

        if($scope.surplusPrice >0 && ($scope.payType == undefined || $scope.payType == '' ||$scope.payType == null)){
            alert("请选择支付方式(^_^)");
            return false;
        }else if($scope.payType == undefined){
            $scope.payType = ""
        }
        if($scope.detail == undefined){
            $scope.detail = ""
        }
        UpdateShopUserOrderPayInfo.save({
            orderId: $state.params.orderId,
            userPayRechargeCardList: $scope.userPayRechargeCardList,
            detail:$scope.responseData.detail
        }, function(data) {
            if("0x00001" == data.result){
                UserPayOpe.save({
                    balancePay: $scope.balancePay,
                    cashPayPrice: $scope.cashPayPrice,
                    orderId: $state.params.orderId,
                    payType: $scope.payType,
                    surplusPayPrice: $scope.surplusPrice,
                    detail: $scope.detail
                }, function(data) {
                    $state.go('pad-web.signConfirm', { orderId: $state.params.orderId,userId:$stateParams.userId })
                })
            }
        })

    }

    $scope.checkBoxCheck = function(payType) {
        $scope.payType = payType;
    }

    GetShopUserRecentlyOrderInfo.get({
        sysUserId: $stateParams.userId,
        orderId: $state.params.orderId,
    }, function(data) {
        $scope.responseData = data.responseData;
        $scope.userPayRechargeCardList = $scope.responseData.userPayRechargeCardList
        //如果可用余额>支付金额
        if($scope.responseData.availableBalance >= $scope.responseData.orderPrice){
            $scope.balancePay = $scope.responseData.orderPrice//默认显示
        }else{
            $scope.balancePay = $scope.responseData.availableBalance;
        }

        $scope.changeRechargeCard()
    })

    $scope.goSelectRechargeType = function() {
        $scope.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
        $state.go('pad-web.left_nav.selectRechargeType', { type: 2,userId:$stateParams.userId,orderId:$state.params.orderId})
    }


    $scope.cashPayPrice = 0//默认显示

    $scope.changeRechargeCard = function () {
        //计算充值卡抵扣的总金额
        var rechargePrice = 0;
        angular.forEach($scope.responseData.userPayRechargeCardList,function (date) {
            if (date.consumePrice<0) {
                alert('对不起，抵扣金额不能小于0元');
            }
            rechargePrice = Number(date.consumePrice) + rechargePrice;
        })
        //计算余额支付
        // $scope.balancePay = $scope.responseData.orderPrice - rechargePrice;
        //计算剩余支付
        $scope.surplusPrice = keepTwoDecimalFull($scope.responseData.orderPrice - (Number($scope.balancePay) + Number($scope.cashPayPrice)) - rechargePrice);
    }

    $scope.changeBalance = function () {
        //计算充值卡抵扣的总金额
        var rechargePrice = 0;
        angular.forEach($scope.responseData.userPayRechargeCardList,function (date) {
            if (!(/(^[0-9]\d*$)/.test(date.consumePrice))) {
                alert('输入的不是正整数');
            }
            rechargePrice = Number(date.consumePrice) + rechargePrice;
        })
        //计算剩余支付
        $scope.surplusPrice = keepTwoDecimalFull($scope.responseData.orderPrice - (Number($scope.balancePay) + Number($scope.cashPayPrice)) - rechargePrice);
    }

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    function keepTwoDecimalFull(num) {
        var result = parseFloat(num);
        if (isNaN(result)) {
            alert('传递参数错误，请检查！');
            return false;
        }
        result = Math.round(num * 100) / 100;
        var s_x = result.toString();
        var pos_decimal = s_x.indexOf('.');
        if (pos_decimal < 0) {
            pos_decimal = s_x.length;
            s_x += '.';
        }
        while (s_x.length <= pos_decimal + 2) {
            s_x += '0';
        }
        return s_x;
    }

});