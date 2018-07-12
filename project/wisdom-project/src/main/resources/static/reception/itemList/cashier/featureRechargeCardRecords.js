PADWeb.controller('featureRechargeCardRecordsCtrl', function($scope, $stateParams, $state, ngDialog,GetRechargeRecord,GetUserRechargeCardList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "充值记录";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.param.selectSty = $stateParams.userId

    // $scope.sunAmount = $stateParams.sunAmount

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    $scope.goRechargeCardRecords = function () {
        $state.go("pad-web.left_nav.rechargeCardRecords",{userId:$stateParams.userId})
    }
    $scope.goRechargeCardDetail = function (flowNo) {
        $state.go("pad-web.left_nav.rechargeCardDetail",{
            userId:$stateParams.userId,
            flowNo:flowNo
        })
    }

    GetRechargeRecord.get({
        flowId:$stateParams.id,
        pageSize:"100"
    },function (data) {
        if(data.result == "0x00001"){
            $scope.featureData = data.responseData
        }
    })

    GetUserRechargeCardList.get({
        sysUserId:$stateParams.userId,
        cardId:$stateParams.id,
        pageSize:"100"
    },function (data) {
        if(data.result == "0x00001"){
            $scope.sunAmount = data.responseData.specialBalance
        }
    })
});