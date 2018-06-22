PADWeb.controller('rechargeCardDetailCtrl', function($scope, $stateParams, $state, ngDialog,GetConsumeDetail,ConsumeFlowNo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "账户明细";
    $scope.$parent.$parent.param.headerCash.title = "充值卡详情";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;

    $scope.$parent.param.selectSty = $stateParams.userId
    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    if($stateParams.id != ""){
        //普通卡
        GetConsumeDetail.save({
            consumeType:"0",
            flowId:$stateParams.id,
            goodsType:"2"//产品详情传4 充值卡订单详情传2
        },function (data) {
            if(data.result == "0x00001"){
                $scope.dataInfo = data.responseData
            }
        })
    }else if($stateParams.flowNo != ""){
        //特殊卡
        ConsumeFlowNo.get({
            consumeFlowNo:$stateParams.flowNo
        },function (data) {
            if(data.result == "0x00001"){
                $scope.dataInfo = data.responseData
            }
        })
    }

});