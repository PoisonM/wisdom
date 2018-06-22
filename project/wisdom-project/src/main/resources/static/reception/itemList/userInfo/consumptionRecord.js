PADWeb.controller('consumptionRecordCtrl', function($scope, $state,$stateParams,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="消费记录";
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false

    console.log("消费记录");
    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        goodsType:4,
        consumeType:1,
        pageSize:12
    },function (data) {
        $scope.dataList = data.responseData
    })
    $scope.goRecordDetail = function (item) {
        $state.go('pad-web.left_nav.consumeCardDetail',{type:"cp",id:item.flowId,userId:item.sysUserId})
    }
});