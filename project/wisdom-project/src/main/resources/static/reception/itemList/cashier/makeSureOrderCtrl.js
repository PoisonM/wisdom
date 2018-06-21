PADWeb.controller('makeSureOrderCtrl', function($scope,$rootScope,$stateParams, $state
    , ngDialog, Archives, SaveShopUserOrderInfo, GetShopUserRecentlyOrderInfo
    , UpdateVirtualGoodsOrderInfo, UpdateShopUserOrderInfo,GetShopClerkList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.$parent.param.headerCash.title = "消费"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
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
    $scope.flagFn(true)
    $scope.$parent.param.selectSty = $stateParams.userId//选中店员控制样式
    $scope.staffListNames = $rootScope.staffListNames//关联员工
    $scope.staffListIds = $rootScope.staffListIds
    $scope.housekeeperFlag = false

    $scope.car = 1;


    $scope.goOrderListm = function() {
        if($scope.orderPrice == 0){
            alert("未选择产品或项目")
            return false
        }
        $scope.importData = {
            orderId: $scope.orderId,
            projectGroupRelRelationDTOS: $scope.projectGroupRelRelationDTOS,//套卡
            shopUserProductRelationDTOS: $scope.shopUserProductRelationDTOS,//产品
            shopUserProjectRelationDTOS: $scope.shopUserProjectRelationDTOS,//项目
            status: 1,
            shopUserRechargeCardDTO: $scope.shopUserRechargeCardDTO,
            orderPrice: $scope.orderPrice, //总金额
            sysClerkId:"",
            sysClerkName:""
        }
        if($scope.staffListIds == undefined){
            $scope.importData.sysClerkId = "";
            $scope.importData.sysClerkName = ""
        }else {
            $scope.importData.sysClerkId = $scope.staffListIds.join(";");
            $scope.importData.sysClerkName = $scope.staffListNames.join(";");
        }
        UpdateShopUserOrderInfo.save($scope.importData, function(data) {
            $state.go('pad-web.left_nav.orderList', { orderId: $scope.orderId,userId:$stateParams.userId })
        })
    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }
    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType', { type: 1,userId:$stateParams.userId });
    }

    SaveShopUserOrderInfo.save({ userId: $stateParams.userId }, function(data) {
        $scope.orderId = data.responseData;
        GetShopUserRecentlyOrderInfo.get({ sysUserId: $stateParams.userId , orderId: data.responseData }, function(data) {
            if(null != data.responseData){
                $scope.projectGroupRelRelationDTOS = data.responseData.projectGroupRelRelationDTOS;
                $scope.shopUserProductRelationDTOS = data.responseData.shopUserProductRelationDTOS;
                $scope.shopUserProjectRelationDTOS = data.responseData.shopUserProjectRelationDTOS;
                $scope.shopUserRechargeCardDTO = data.responseData.shopUserRechargeCardDTO;
                $scope.orderPrice = data.responseData.orderPrice;
            }
        })
    })

    //删除订单中的虚拟商品
    $scope.deleteClick = function(type, item) {
        var shopUserOrderDTO = {
            orderId : $scope.orderId,
            goodsType:type,
            operation:'1',//0添加 1删除
            shopUserProductRelationDTOS : [],
            projectGroupRelRelationDTOS : [],
            shopUserProjectRelationDTOS : [],
        }
        shopUserOrderDTO.orderId = $scope.orderId;
        shopUserOrderDTO.operation = '1'
        if($rootScope.goodsType.product == type){
            shopUserOrderDTO.shopUserProductRelationDTOS.push(item);
        }
        if($rootScope.goodsType.timeCard == type){
            shopUserOrderDTO.shopUserProjectRelationDTOS.push(item);
        }
        if($rootScope.goodsType.groupCard == type){
            shopUserOrderDTO.projectGroupRelRelationDTOS.push(item);
        }
        UpdateVirtualGoodsOrderInfo.save(shopUserOrderDTO, function(data) {
            $scope.reloadOrder();
        })
    }

    $scope.getTotalPrice = function() {}

    $scope.reloadOrder = function(){
        GetShopUserRecentlyOrderInfo.get({ orderId: $scope.orderId}, function(data) {
            $scope.projectGroupRelRelationDTOS = data.responseData.projectGroupRelRelationDTOS;
            $scope.shopUserProductRelationDTOS = data.responseData.shopUserProductRelationDTOS;
            $scope.shopUserProjectRelationDTOS = data.responseData.shopUserProjectRelationDTOS;
            $scope.orderPrice = data.responseData.orderPrice;
        })
    }

    $scope.tempAll = 0;

    //增加购买数量
    $scope.goodsInc = function (type,index) {
        if($rootScope.goodsType.groupCard == type){
            ++$scope.projectGroupRelRelationDTOS[index].projectInitTimes;
        }else if($rootScope.goodsType.project == type){
            ++$scope.shopUserProjectRelationDTOS[index].sysShopProjectInitTimes;
        }else if($rootScope.goodsType.product == type){
            ++$scope.shopUserProductRelationDTOS[index].initTimes;
        }
        $scope.myChangeFn();
    }
    //降低购买数量
    $scope.goodsSub = function (type,index) {
        if($rootScope.goodsType.groupCard == type){
            if($scope.projectGroupRelRelationDTOS[index].projectInitTimes>1){
                --$scope.projectGroupRelRelationDTOS[index].projectInitTimes;
            }else{
                alert("购买套卡数量最少为1个")
            }
        }else if($rootScope.goodsType.project == type){
            if($scope.shopUserProjectRelationDTOS[index].sysShopProjectInitTimes>1){
                --$scope.shopUserProjectRelationDTOS[index].sysShopProjectInitTimes;
            }else{
                alert("购买项目数量最少为1个")
            }
        }else if($rootScope.goodsType.product == type){
            if($scope.shopUserProductRelationDTOS[index].initTimes>1){
                --$scope.shopUserProductRelationDTOS[index].initTimes;
            }else{
                alert("购买产品数量最少为1个")
            }
        }
        $scope.myChangeFn();
    }

    //计算订单的价格
    $scope.myChangeFn = function() {
        //套卡价格
        $scope.orderPrice = 0;
        angular.forEach($scope.projectGroupRelRelationDTOS, function(data){
            $scope.orderPrice = Number(data.projectInitAmount*data.discount*data.projectInitTimes) + Number($scope.orderPrice)
        });
        //项目价格
        angular.forEach($scope.shopUserProjectRelationDTOS, function(data,index,array){
            $scope.orderPrice = Number(data.sysShopProjectInitAmount*data.discount*data.sysShopProjectInitTimes)+ Number($scope.orderPrice)
        });
        //产品价格
        angular.forEach($scope.shopUserProductRelationDTOS, function(data,index,array){
            $scope.orderPrice = Number(data.initAmount*data.discount*data.initTimes)+ Number($scope.orderPrice)
        });

    }

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-2)
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


    $scope.goHousekeeper = function(type,index) {
        $scope.housekeeperFlag = true;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false;
        $scope.paramType = type
        $scope.paramIndex = index

    }

    //保存关联员工
    $scope.$parent.$parent.leftTipFn = function() {
        $scope.housekeeperFlag = false
        if($scope.paramType == "group"){
            $scope.projectGroupRelRelationDTOS[$scope.paramIndex].sysClerkId = $scope.userIdList.join(";")
            $scope.projectGroupRelRelationDTOS[$scope.paramIndex].sysClerkName = $scope.userNameList.join(";")
        }else if($scope.paramType == "product"){
            $scope.shopUserProductRelationDTOS[$scope.paramIndex].sysClerkId = $scope.userIdList.join(";")
            $scope.shopUserProductRelationDTOS[$scope.paramIndex].sysClerkName = $scope.userNameList.join(";")
        }else if($scope.paramType == "project"){
            $scope.shopUserProjectRelationDTOS[$scope.paramIndex].sysClerkId = $scope.userIdList.join(";")
            $scope.shopUserProjectRelationDTOS[$scope.paramIndex].sysClerkName = $scope.userNameList.join(";")
        }
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
        $scope.userIdList = []
        $scope.userNameList = []
    }
});