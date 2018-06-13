PADWeb.controller('getProductRecordCtrl', function($scope, $stateParams, $state, ngDialog,GetProductDrawRecord) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "产品的领取记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "消费记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.param.selectSty = $stateParams.userId

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    //跳转消费记录
    $scope.$parent.$parent.leftTipFn = function () {
        $state.go("pad-web.left_nav.consumeCardDetail",{
            userId:$stateParams.userId,
            id:$stateParams.id,
            type:"cp"
        })
    }
    //
    $scope.goCashProductDetails = function () {
        $state.go("pad-web.left_nav.cashProductDetails",{userId:$stateParams.userId})
    }

    GetProductDrawRecord.save({
        flowId:$stateParams.id,
        goodsType:"4"
    },function (data) {
        if(dat.result == "0x00001"){
            $scope.dataList = data.responseData
        }
    })

});