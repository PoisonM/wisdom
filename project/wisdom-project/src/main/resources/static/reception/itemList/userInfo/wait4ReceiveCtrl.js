PADWeb.controller('wait4ReceiveCtrl', function($scope, $stateParams,GetWaitReceiveDetail) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title=$stateParams.name
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false

    $scope.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = true

    GetWaitReceiveDetail.get({
        sysUserId:$stateParams.id
    },function (data) {
        $scope.dataList = data.responseData
        console.log(data)
    })
});