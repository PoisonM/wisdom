PADWeb.controller('addRecordDetailCtrl', function($scope,$state,$stateParams,ArchivesDetail,UpdateArchiveInfo) {
    console.log($scope);
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent="档案(9010)"
    $scope.$parent.$parent.param.headerCash.leftAddContent="添加档案"
    $scope.$parent.$parent.param.headerCash.backContent="返回"
    $scope.$parent.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.$parent.$parent.param.headerCash.title = "档案详情"
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

    $scope.$parent.param.selectSty = $stateParams.userId

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    /*----------------------------------初始化参数------------------------------------------*/
    //档案详情
    ArchivesDetail.get({id:$stateParams.id},function (data) {
        $scope.responseData = data.responseData
        $scope.responseData.select_type=""
        $scope.responseData.openSelectFlag=""
        $scope.responseData.selectContentName=""
        $scope.responseData.selectContentPhone=""
        $scope.responseData.selectContentSex=""
        $scope.responseData.selectContentBirthday=""
        $scope.responseData.selectContentAge=""
        $scope.responseData.selectContentConstellation=""
        $scope.responseData.selectContentBlood=""
        $scope.responseData.selectContentHeight=""
        $scope.responseData.selectContentSource=""
    })

    //打开选择页面
    $scope.openSelect = function (type,content) {
        // $scope.flagFn("添加档案",content,false)
        $scope.responseData.openSelectFlag = true
        $scope.responseData.select_type = type
    }
    //选择完成
    $scope.selectFn = function (type,content) {
        // $scope.flagFn("","添加档案",false)
        $scope.responseData.openSelectFlag = false
        if(type == "sex"){
            $scope.responseData.sex = content
        }else if(type == "birthday"){
            $scope.responseData.sex = content
        }else if(type == "age"){
            $scope.responseData.selectContentAge = content
        }else if(type == "constellation"){
            $scope.responseData.selectContentConstellation = content
        }else if(type == "bloodType"){
            $scope.responseData.selectContentBlood = content
        }else if(type == "height"){
            $scope.responseData.selectContentHeight = content
        }else if(type == "source"){
            $scope.responseData.selectContentSource = content
        }
    }


    /*---------------------------------方法-----------------------------------*/

    $scope.$parent.$parent.leftTipFn = function () {
        UpdateArchiveInfo.save($scope.responseData,function (data) {
            if("0x00001"==data.result){
                alert("更新成功");
            }
        });
    }



});