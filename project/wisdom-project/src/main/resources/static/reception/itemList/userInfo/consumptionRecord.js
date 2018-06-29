PADWeb.controller('consumptionRecordCtrl', function($scope, $state,$stateParams,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="消费记录";
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false

    $scope.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = true

    console.log("消费记录");
    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        consumeType:"0", sysUserId:$stateParams.userId, pageSize:"100", goodsType:"7", isCurrentDay:"1"
    },function (data) {
        $scope.dataList = data.responseData
        console.log( data.responseData)
    })
    $scope.goRecordDetail = function (item) {
        // $state.go('pad-web.left_nav.completeCardDetail',{id:item.flowId,userId:})
        $state.go("pad-web.left_nav.completeConsumeDetail",{
            userId:item.sysUserId,
            flowNo:item.flowNo
        })
    }
});