PADWeb.controller('selectRechargeCardCtrl', function($scope,$rootScope, $state, $stateParams
    , ngDialog, Archives, CardInfo, UserRechargeConfirm,GetShopClerkList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "选择充值卡";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.flagFn = function(bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool;
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool;
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool;
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);
    $scope.$parent.param.selectSty = $stateParams.userId//选中店员控制样式
    // $scope.staffListNames = $rootScope.staffListNames//关联员工
    // $scope.staffListIds = $rootScope.staffListIds
    $scope.housekeeperFlag = false

    $scope.discount = {
        timeDiscount:'',
        periodDiscount:'',
        productDiscount:''
    }


    $scope.select = 0;
    $scope.tabclick = function(e) {
        $scope.select = e;
    }
    $scope.goChooseGifts = function() {
        $state.go('pad-web.left_nav.chooseGifts');
    }

    $scope.goCustomerSignature = function() {
        if($scope.responseData.amount<=0){
            alert("余额充值不能小于或等于0，请核对后提交(^_^)");
            return false
        }
        $scope.responseData.sysUserId = $stateParams.userId;
        $scope.responseData.surplusPayPrice = $scope.responseData.amount - $scope.responseData.cashPay;
        if($scope.responseData.surplusPayPrice<0){
            alert("剩余支付金额为负数啦(^_^)");
            return false;
        }else if($scope.responseData.surplusPayPrice > 0 && (null == $scope.responseData.payType || ''==$scope.responseData.payType)){
            alert("请选择支付方式");
            return false;
        }
        $scope.responseData.timeDiscount=('无'==$scope.discount.timeDiscount?'1':$scope.discount.timeDiscount);
        $scope.responseData.periodDiscount=('无'==$scope.discount.periodDiscount?'1':$scope.discount.periodDiscount);
        $scope.responseData.productDiscount=('无'==$scope.discount.productDiscount?'1':$scope.discount.productDiscount);

        UserRechargeConfirm.save($scope.responseData, function(data) {
            if(data.result=="0x00001"){
                // $rootScope.staffListNames=[]//保存清除关联员工
                // $rootScope.staffListIds=[]
                $state.go('pad-web.signConfirm', {
                    transactionId: data.responseData.transactionId,
                    userId:$stateParams.userId
                });
            }

        })
    }
    // localStorage.setItem("payType","11")

    $scope.checkBoxChek = function(e) {
        localStorage.setItem("payType",e)
        $scope.responseData.payType = localStorage.getItem("payType");
    }
    CardInfo.get({ id: $state.params.type,sysUserId: $stateParams.userId}, function(data) {
        $scope.responseData = data.responseData;
        //如果是特殊充值卡
        if($stateParams.rechargeCardType == '0'){
            $scope.discount.timeDiscount= $scope.responseData.timeDiscount!='1'?$scope.responseData.timeDiscount:'无';
            $scope.discount.periodDiscount= $scope.responseData.periodDiscount!='1'?$scope.responseData.periodDiscount:'无';
            $scope.discount.productDiscount= $scope.responseData.productDiscount!='1'?$scope.responseData.productDiscount:'无';
            $scope.responseData.payType = '';//localStorage.getItem("payType") 默认为空
            $scope.responseData.amount = '0';//默认为0
        }else{
            $scope.discount.timeDiscount= $scope.responseData.timeDiscount!='1'?$scope.responseData.timeDiscount:'无';
            $scope.discount.periodDiscount= $scope.responseData.periodDiscount!='1'?$scope.responseData.periodDiscount:'无';
            $scope.discount.productDiscount= $scope.responseData.productDiscount!='1'?$scope.responseData.productDiscount:'无';
        }
    })

    $scope.$parent.$parent.backHeaderCashFn = function () {
        // $state.go("pad-web.left_nav.personalFile")
        window.history.go(-1)
    }


    //获取员工列表
    GetShopClerkList.get({
        pageNo: "1",
        pageSize: "100"
    }, function(data) {
        if (data.result == "0x00001") {
            $scope.UserList = data.responseData
        }
    })



    $scope.userIdList = []
    $scope.userNameList = []
    $scope.housekeeperCheck = function (index,userId,name) {
        if($scope.userIdList.indexOf(userId) != -1){
            $scope.userIdList.remove(userId)
            $scope.userNameList.remove(name)
        }else {
            $scope.userIdList.push(userId)
            $scope.userNameList.push(name)
        }
    }
    
    
    
    $scope.goHousekeeper = function() {
        $scope.housekeeperFlag = true;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false;
    }


    //保存关联员工
    $scope.$parent.$parent.leftTipFn = function() {
        $scope.housekeeperFlag = false
        if($scope.userIdList == undefined){
            $scope.responseData.sysClerkId = "";
            $scope.responseData.sysClerkName = ""
        }else {
            $scope.responseData.sysClerkId = $scope.userIdList.join(";");
            $scope.responseData.sysClerkName = $scope.userNameList.join(";");
        }
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    }
});