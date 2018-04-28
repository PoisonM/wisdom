PADWeb.controller('userInfoCtrl', function($scope, $state,$stateParams, ngDialog,GetProductRecord,GetClerkAchievement,ClerkInfo) {
/*-------------------------------------------定义头部信息|----------------------------------------------*/
    $scope.$parent.param.headerCash.leftContent="我"
    $scope.$parent.param.headerCash.leftAddContent=""
    $scope.$parent.param.headerCash.backContent="今日收银记录"
    $scope.$parent.param.headerCash.leftTip="筛选"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.flagFn = function (bool) {
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.mainSwitch.headerCashFlag.leftFlag = bool,
        $scope.$parent.mainSwitch.headerCashFlag.middleFlag = bool,
        $scope.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);
/*-----------------------------------------------接口---------------------------------------------------*/
    GetClerkAchievement.get({
        sysClerkId:22
    },function (data) {
        if(data.result == "0x00001"){
            $scope.todayPerformance = data.responseData
        }
    })
    /*个人信息*/
    ClerkInfo.query({
        clerkId:"2"
    },function (data) {
        $scope.userInfoData = data
    })
/*----------------------------------------------方法-------------------------------------------------------------*/
    //今日业绩
    $scope.goTodayPerformance = function () {
        $state.go("pad-web.userInfo.todayPerformance")
    }
    //修改资料
    $scope.goModificationData = function () {
        $state.go("pad-web.userInfo.modificationData")
    }
    //使用帮助
    $scope.goUsingHelp = function () {
        $state.go("pad-web.userInfo.usingHelp")
    }
    //意见反馈
    $scope.goFeedback = function () {
        $state.go("pad-web.userInfo.feedback")
    }
/*-------------------------------------------------弹窗----------------------------------------------------------------*/
    $scope.openService = function () {
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'servicePhone',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.close = function() {
                    $scope.closeThisDialog();
                };



            }],
            className: 'ngdialog-theme-default ngdialog-theme-custom border_radius',
            'width': 270,
            'height':106,
            'border-radius': 10,
        });
    }
});