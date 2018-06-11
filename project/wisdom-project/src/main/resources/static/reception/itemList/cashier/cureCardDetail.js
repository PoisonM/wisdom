PADWeb.controller('cureCardDetailCtrl', function($scope, $stateParams, $state, ngDialog) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "划卡记录";
    $scope.$parent.$parent.param.headerCash.title = "疗程卡详情";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
});