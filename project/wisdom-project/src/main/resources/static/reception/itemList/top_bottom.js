PADWeb.controller("mallCtrl", function($scope, $state, $stateParams) {
    console.log("top")

    /*------------------------------------初始化参数-----------------------------------------------*/
    $scope.param = {
        top_bottomSelect:"yuyue",
        tabSty:'day',
        headerCash:{
            leftContent:"档案(9010)",
            leftAddContent:"添加档案",
            backContent:"账户明细",
            title:"详情",
            leftTip:"保存"
        },
        headerPrice:{
            backContent:"",
            title:"项目",
            blackBackContent:"",
            blackTitle:"产品",
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
        headerPriceListAllFlag:false,
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
        if(type == "yuyue"){
            $state.go("pad-web.dayAppointment")
        }else if(type == "shouyin"){
            $state.go("pad-web.left_nav.addRecord")
        }else if(type == "kaoqin"){
            $state.go("pad-web.attendance")
        }else if(type =="jiamubiao"){
            $state.go("pad-web.left_nav.project")
        }else if(type =="wo"){
            $state.go("pad-web.userInfo.unclaimedAll")
        }
    }
})