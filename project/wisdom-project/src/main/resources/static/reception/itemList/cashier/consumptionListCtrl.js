PADWeb.controller('consumptionListCtrl', function($scope, $stateParams, ngDialog, Archives) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.param.headerCash.backContent = "充值记录";
    $scope.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.flagFn = function(bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.mainSwitch.headerCashFlag.leftFlag = bool;
        $scope.$parent.mainSwitch.headerCashFlag.middleFlag = bool;
        $scope.$parent.mainSwitch.headerCashFlag.rightFlag = bool;
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

});