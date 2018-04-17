PADWeb.controller('addRecordCtrl', function($scope, $stateParams, ngDialog) {
    console.log($scope);
/*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.headerCash.leftContent="档案(9010)"
    $scope.$parent.$parent.param.headerCash.leftAddContent="添加档案"
    $scope.$parent.$parent.param.headerCash.backContent="今日收银记录"
    $scope.$parent.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)



/*----------------------------------初始化参数------------------------------------------*/
    $scope.param = {
        select_type:"",
        openSelectFlag:false,//选择页面开关
        selectContentSex:"",
        selectContentBirthday:"",
        selectContentAge:"",
        selectContentConstellation:"",
        selectContentBlood:"",
        selectContentHeight:"",
        selectContentSource:"",
    }
    /*---------------------------------方法-----------------------------------*/
    $scope.flagFn = function (backContent,title,bool) {
        $scope.$parent.$parent.param.headerCash.backContent = backContent
        $scope.$parent.$parent.param.headerCash.title = title
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = bool//关闭返回
    }

    $scope.flagFn("","添加档案",false)


    //打开选择页面
    $scope.openSelect = function (type,content) {
        $scope.flagFn("添加档案",content,true)
        $scope.param.openSelectFlag = true
        $scope.param.select_type = type
    }
    
    //选择完成
    $scope.selectFn = function (type,content) {
        $scope.flagFn("","添加档案",false)
        $scope.param.openSelectFlag = false
        if(type == "sex"){
            $scope.param.selectContentSex = content
        }else if(type == "birthday"){
            $scope.param.selectContentBirthday = content
        }else if(type == "age"){
            $scope.param.selectContentAge = content
        }else if(type == "constellation"){
            $scope.param.selectContentConstellation = content
        }else if(type == "blood"){
            $scope.param.selectContentBlood = content
        }else if(type == "height"){
            $scope.param.selectContentHeight = content
        }else if(type == "source"){
            $scope.param.selectContentSource = content
        }
    }
});