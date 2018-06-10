PADWeb.controller('selectRechargeCardCtrl', function($scope, $state, $stateParams, ngDialog, Archives, CardInfo, UserRechargeConfirm) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "选择充值卡";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = false;
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
    $scope.flagFn(true);
    $scope.$parent.param.selectSty = $stateParams.userId//选中店员控制样式


    $scope.select = 0;
    $scope.tabclick = function(e) {
        $scope.select = e;
    }
    $scope.goChooseGifts = function() {
        $state.go('pad-web.left_nav.chooseGifts');
    }
    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper');
    }
    $scope.goCustomerSignature = function() {
        $scope.responseData.sysUserId = $stateParams.userId;

        UserRechargeConfirm.save($scope.responseData, function(data) {
            $state.go('pad-web.signConfirm', {
                transactionId: data.responseData.transactionId,
                userId:$stateParams.userId
            });
        })
    }
    $scope.checkBoxChek = function(e) {
        $scope.responseData.payType = e;
    }
    CardInfo.get({ id: $state.params.type }, function(data) {
        $scope.responseData = data.responseData;
    })

    $scope.$parent.$parent.backHeaderCashFn = function () {
        // $state.go("pad-web.left_nav.personalFile")
        window.history.go(-1)
    }
});