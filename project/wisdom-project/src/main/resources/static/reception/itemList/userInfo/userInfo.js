PADWeb.controller('userInfoCtrl', function($scope, $state, $stateParams, ngDialog
    , GetProductRecord, GetClerkAchievement,ClerkInfo,BeautyLoginOut,GetCurrentLoginUserInfo) {
    /*-------------------------------------------定义头部信息|----------------------------------------------*/
    $scope.$parent.param.top_bottomSelect = "wo";
    $scope.$parent.param.headerCash.leftContent = "我"
    $scope.$parent.param.headerCash.leftAddContent = ""
    $scope.$parent.param.headerCash.backContent = "今日收银记录"
    // $scope.$parent.param.headerCash.leftTip = "筛选"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.flagFn = function(bool) {
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.mainSwitch.headerCashFlag.leftFlag = bool
            $scope.$parent.mainSwitch.headerCashFlag.middleFlag = bool
            $scope.$parent.mainSwitch.headerCashFlag.rightFlag = bool
        $scope.$parent.mainSwitch.footerBoxFlag = true

    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);

    /*-----------------------------------------------接口---------------------------------------------------*/
    GetClerkAchievement.get({
    }, function(data) {
        if (data.result == "0x00001") {
            $scope.todayPerformance = data.responseData
        }else if(data.result == "0x00002"){//判断店员是否
            alert("登录已经失效,请重新登录")
            setTimeout(function () {
                $state.go("pad-web.login")
            },1000)
        }
    })

    /*个人信息*/
    GetCurrentLoginUserInfo.get({},function (data) {
        if(data.result="0x00001")
        {
            ClerkInfo.query({
                clerkId: data.responseData.id
            }, function(data) {
                $scope.tempArr = []
                $scope.userInfoData = data
                //计算资料完成度
                for (var key in $scope.userInfoData[0]) {
                    $scope.tempArr.push($scope.userInfoData[0][key]); //属性
                }
                var tempLength = 0
                for(var i = 0; i < $scope.tempArr.length; i++){
                    if($scope.tempArr[i] != null){
                        tempLength+=1
                    }
                }
                $scope.userInfoData[0].completeness = Number(tempLength/$scope.tempArr.length*100).toFixed(0)+"%";
                /*操作dom*/
                $(".col_pink").width(($(".bg_gray").width()*Number(tempLength/$scope.tempArr.length*100).toFixed(1))/100)
            })
        }
    })

    /*----------------------------------------------方法-------------------------------------------------------------*/
    //今日业绩
    $scope.goTodayPerformance = function() {
        $state.go("pad-web.userInfo.todayPerformance")
    }
    //修改资料
    $scope.goModificationData = function() {
        $state.go("pad-web.userInfo.modificationData")
    }
    //使用帮助
    $scope.goUsingHelp = function() {
        $state.go("pad-web.userInfo.usingHelp")
    }
    //意见反馈
    $scope.goFeedback = function() {
        $state.go("pad-web.userInfo.feedback")
    }
    /*-------------------------------------------------弹窗----------------------------------------------------------------*/
    $scope.openService = function() {
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
            'height': 106,
            'border-radius': 10,
        });
    }
    
    
    $scope.loginOut = function () {
        BeautyLoginOut.get({},function (data) {
            alert("退出成功")
            window.localStorage.removeItem("beautyUserLoginToken")
            window.localStorage.removeItem("beautyBossLoginToken")
            window.localStorage.removeItem("beautyClerkLoginToken")
            setTimeout(function () {
                $state.go("pad-web.login")
            },1000)
        })
    }
});