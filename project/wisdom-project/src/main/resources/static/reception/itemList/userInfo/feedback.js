PADWeb.controller('feedbackCtrl', function($scope, $stateParams) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="意见反馈"
    $scope.$parent.param.headerCash.backContent="取消"
    $scope.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false


    console.log("意见反馈");
});