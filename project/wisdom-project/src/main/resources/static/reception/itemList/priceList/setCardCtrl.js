/**
 * Created by Administrator on 2018/4/16.
 */
PADWeb.controller("setCardCtrl", function($scope, $state, $stateParams,$rootScope) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.headerPrice.blackTitle = "充值卡"
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = !bool
        $scope.$parent.mainLeftSwitch.priceListFlag = bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerPriceListBlackFlag = bool

    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)
    console.log("setCardCtrl");
    $rootScope.title="充值卡";
    $scope.goCardDetails=function () {
        $state.go("pad-web.cardDetails")
    }
})