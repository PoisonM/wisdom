PADWeb.controller('userInfoCtrl', function($scope, $stateParams, ngDialog,GetProductRecord) {
/*-------------------------------------------定义头部信息|----------------------------------------------*/
    $scope.$parent.param.headerCash.leftContent="我"
    $scope.$parent.param.headerCash.leftAddContent=""
    $scope.$parent.param.headerCash.backContent="今日收银记录"
    $scope.$parent.param.headerCash.leftTip="筛选"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.flagFn = function (bool) {
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.mainSwitch.headerCashFlag.leftFlag = bool,
        $scope.$parent.mainSwitch.headerCashFlag.middleFlag = bool,
        $scope.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)


});