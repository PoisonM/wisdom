PADWeb.controller('rechargeRecordCtrl', function($scope, $stateParams, ngDialog,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="充值记录"
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true

    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        goodsType:2,
        consumeType:0,
        pageSize:10
    },function (data) {
        $scope.dataList = data.responseData;
        console.log($scope.dataList);
    })
});