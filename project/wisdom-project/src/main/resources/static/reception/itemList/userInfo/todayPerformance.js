PADWeb.controller('todayPerformanceCtrl', function($scope,$state,$stateParams, ngDialog) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.backContent=""
    $scope.$parent.$parent.param.headerCash.title="今日收银记录"
    $scope.$parent.$parent.param.headerCash.leftTip=""
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false



    /*-------------------------------------------方法----------------------------------------------*/
    //判断店员是否登录
    if(window.localStorage.getItem("beautyClerkLoginToken")==undefined || window.localStorage.getItem("beautyClerkLoginToken")==null){
        $state.go("pad-web.login")
    }


    $scope.goRecord = function (content) {
        $state.go("pad-web.userInfo."+content)
    }
});