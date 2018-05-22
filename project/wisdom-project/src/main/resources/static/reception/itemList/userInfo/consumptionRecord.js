PADWeb.controller('consumptionRecordCtrl', function($scope, $state,$stateParams,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="消费记录";
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true


    console.log("消费记录");
    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        sysClerkId:112,
        goodsType:0,
        consumeType:1,
        pageSize:12
    },function (data) {
        $scope.dataList = data.responseData
    })
    
    
    /*$scope.$parent.$parent.backHeaderCashFn = function () {
        $state.go("pad-web.userInfo.todayPerformance")
    }*/
});