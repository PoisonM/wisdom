PADWeb.controller('cureCardRecordsCtrl', function($scope, $stateParams, $state, ngDialog,TreatmentAndGroupCardRecordList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "疗程卡划卡记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "消费记录";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.param.selectSty = $stateParams.userId

    $scope.$parent.$parent.leftTipFn = function () {
        $state.go('pad-web.left_nav.consumeCardDetail',{type:"lck",id:$stateParams.id,userId:$stateParams.userId})//去消费记录
    }
    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    $scope.goCureCardDetail = function (flowNo) {
        $state.go('pad-web.left_nav.cureCardDetail',{userId:$stateParams.userId,flowNo:flowNo})//
    }
    //1、商品类型为疗程卡 ；3、商品类型为套卡
    TreatmentAndGroupCardRecordList.save({
        flowId:$stateParams.id,
        // flowIds:"",套卡名称
        goodsType:"1",
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData
        }
    })

});