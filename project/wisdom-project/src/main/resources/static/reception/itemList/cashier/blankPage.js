PADWeb.controller('blankPageCtrl', function($scope, $state, $stateParams, ngDialog, cashConsume) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.title = "";



    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool;
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool;
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListBlackFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = !bool;
    };
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);

    $scope.queryRecordList()

});