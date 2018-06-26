PADWeb.controller('selectRechargeCardCtrl', function($scope,$rootScope, $state, $stateParams, ngDialog, Archives, CardInfo, UserRechargeConfirm) {
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
    $scope.staffListNames = $rootScope.staffListNames//关联员工
    $scope.staffListIds = $rootScope.staffListIds

    $scope.timeDiscount='';
    $scope.periodDiscount='';
    $scope.productDiscount='';

    $scope.select = 0;
    $scope.tabclick = function(e) {
        $scope.select = e;
    }
    $scope.goChooseGifts = function() {
        $state.go('pad-web.left_nav.chooseGifts');
    }
    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper');
    }
    $scope.goCustomerSignature = function() {
        if($scope.staffListIds == undefined){
            $scope.responseData.sysClerkId = "";
            $scope.responseData.sysClerkName = ""
        }else {
            $scope.responseData.sysClerkId = $scope.staffListIds.join(";");
            $scope.responseData.sysClerkName = $scope.staffListNames.join(";");
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
        $scope.responseData.timeDiscount=('无'==$scope.timeDiscount?'1':$scope.timeDiscount);
        $scope.responseData.periodDiscount=('无'==$scope.periodDiscount?'1':$scope.periodDiscount);
        $scope.responseData.productDiscount=('无'==$scope.productDiscount?'1':$scope.productDiscount);

        UserRechargeConfirm.save($scope.responseData, function(data) {
            if(data.result=="0x00001"){
                $rootScope.staffListNames=[]//保存清除关联员工
                $rootScope.staffListIds=[]
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
            $scope.timeDiscount= $scope.responseData.timeDiscount!='1'?$scope.responseData.timeDiscount:'无';
            $scope.periodDiscount= $scope.responseData.periodDiscount!='1'?$scope.responseData.periodDiscount:'无';
            $scope.productDiscount= $scope.responseData.productDiscount!='1'?$scope.responseData.productDiscount:'无';
            $scope.responseData.payType = '';//localStorage.getItem("payType") 默认为空
            $scope.responseData.amount = '0';//默认为0
        }else{
            $scope.timeDiscount= $scope.responseData.timeDiscount!='1'?$scope.responseData.timeDiscount:'无';
            $scope.periodDiscount= $scope.responseData.periodDiscount!='1'?$scope.responseData.periodDiscount:'无';
            $scope.productDiscount= $scope.responseData.productDiscount!='1'?$scope.responseData.productDiscount:'无';
        }
    })

    $scope.$parent.$parent.backHeaderCashFn = function () {
        // $state.go("pad-web.left_nav.personalFile")
        window.history.go(-1)
    }
});