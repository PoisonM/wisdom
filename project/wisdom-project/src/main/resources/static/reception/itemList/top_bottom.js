PADWeb.controller("mallCtrl", function($scope, $state, $stateParams) {
    console.log("top")

    /*------------------------------------初始化参数-----------------------------------------------*/
    $scope.param = {
        top_bottomSelect:"yuyue",
        tabSty:'day',
        headerCash:{
            backContent:"账户明细",
            title:"详情"
        },
        headerPrice:{
            backContent:"",
            title:"项目",
            blackBackContent:"",
            blackTitle:"黑色项目",
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
        headerCashAllFlag:false,
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
        headerPriceListAllFlag:true,
        headerPriceListBlackFlag:true,
        headerPriceListFlag:{

        },
        //我的头部开关

        //登录头部开关
        headerLoginFlag:false,

        //尾部总开关
        footerBoxFlag:true
    }
    /*--------------------------------------方法-------------------------------------------------*/
    $scope.switchType = function (type) {
        $scope.param.tabSty = type
    }
    $scope.selectSty = function (type) {
        $scope.param.top_bottomSelect = type
    }
})