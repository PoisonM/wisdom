PADWeb.controller("mallCtrl", function($scope, $state, $stateParams) {
    console.log("top")

    /*------------------------------------初始化参数-----------------------------------------------*/
    $scope.param = {
        tabSty:'day',
        headerCash:{
            backContent:"账户明细",
            title:"详情"
        }
    }
    //公共部分开关管理
    $scope.mainSwitch = {
        //头部总开关
        headerBoxFlag:true,
        //预约头部开关
        headerReservationAllFlag:false,
        headerReservationFlag:{
            headerReservationLeftFlag:true,
            headerReservationMiddleFlag:true,
            headerReservationRightFlag:true,
        },
        //收银头部开关
        headerCashAllFlag:true,
        headerCashFlag:{
            headerCashLeftFlag:true,
            headerCashRightFlag:{
                leftFlag:true,
                middleFlag:true,
                rightFlag:true
            },
        },
        //考勤头部开关

        //价目表头部开关

        //我的头部开关

        //尾部总开关
        footerBoxFlag:true
    }
    /*--------------------------------------方法-------------------------------------------------*/
    $scope.switchType = function (type) {
        $scope.param.tabSty = type
    }
})