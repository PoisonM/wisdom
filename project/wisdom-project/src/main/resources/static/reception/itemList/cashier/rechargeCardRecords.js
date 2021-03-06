PADWeb.controller('rechargeCardRecordsCtrl', function($scope, $stateParams, $state, ngDialog) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "充值卡账户明细";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;

    $scope.$parent.param.selectSty = $stateParams.userId

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }
    $scope.goRechargeCardDetail = function () {
        $state.go("pad-web.left_nav.rechargeCardDetail",{userId:$stateParams.userId})
    }
});