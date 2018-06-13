PADWeb.controller('completeCardRecordsCtrl', function($scope, $stateParams, $state, ngDialog,TreatmentAndGroupCardRecordList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "套卡的划卡记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "消费记录";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;

    $scope.$parent.param.selectSty = $stateParams.userId

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    //跳转消费记录
    $scope.$parent.$parent.leftTipFn = function () {
        $state.go("pad-web.left_nav.consumeCardDetail",{
            id:$stateParams.consumeRecordId,
            userId:$stateParams.userId,
            type:"tk"
        })
    }
    //
    $scope.goCompleteCardDetail = function (flowNo) {
        $state.go("pad-web.left_nav.completeCardDetail",{
            userId:$stateParams.userId,
            flowNo:flowNo
        })
    }

    //1、商品类型为疗程卡 ；3、商品类型为套卡
    TreatmentAndGroupCardRecordList.save({
        // flowId:$stateParams.id,
        flowIds:$stateParams.ids.split(","),//
        goodsType:"1",
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData
        }

    })
});