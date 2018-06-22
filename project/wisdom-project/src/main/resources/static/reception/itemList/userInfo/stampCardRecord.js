PADWeb.controller('stampCardRecordCtrl', function($scope,$state, $stateParams, ngDialog,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="划卡记录"
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false

    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        goodsType:5,
        consumeType:1,
        pageSize:1000
    },function (data) {
        $scope.dataList = data.responseData;
        console.log($scope.dataList);
    })
    $scope.goDrawCardRecordsDetail = function (flowNo,sysUserId) {
        $state.go("pad-web.left_nav.drawCardRecordsDetail",{
            userId:sysUserId,
            flowNo:flowNo
        })
    }

});