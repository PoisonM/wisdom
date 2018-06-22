/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("cardDetailsCtrl", function($scope, $state, $stateParams,$rootScope,Detail) {

    console.log("cardDetailsCtrl")

    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.headerPrice.title = "套卡详情"
    $scope.flagFn = function (bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = !bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.mainSwitch.headerPriceListBlackFlag = !bool
    }

    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

    $scope.param={
        cardDetail:{}
    }

    Detail.get({id:$state.params.id},function (data) {
        $scope.param.cardDetail = data.responseData;
        console.log($scope.param.cardDetail);
    })

    $scope.$parent.priceListBlackFn = function () {
        $state.go("pad-web.left_nav.rechargeableCard")
    }

})