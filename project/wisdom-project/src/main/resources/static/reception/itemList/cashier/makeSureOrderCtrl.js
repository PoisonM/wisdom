PADWeb.controller('makeSureOrderCtrl', function($scope,$rootScope,$stateParams, $state, ngDialog, Archives, SaveShopUserOrderInfo, GetShopUserRecentlyOrderInfo, UpdateVirtualGoodsOrderInfo, UpdateShopUserOrderInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.leftTip = "添加更多";
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

    $scope.car = 1;
    $scope.$parent.$parent.leftTipFn = function() {
        $state.go('pad-web.consumptionList');
    }

    //回显关联员工
    // $scope.projectGroupRelRelationDTOSPeople= $rootScope.projectGroupRelRelationDTOSTemp
    // $scope.shopUserProductRelationDTOSPeople= $rootScope.shopUserProductRelationDTOSTemp
    // $scope.shopUserProjectRelationDTOSPeople= $rootScope.shopUserProjectRelationDTOSTemp

    $scope.goHousekeeper = function(type,index,clerkIds,clerkNames) {
        $state.go('pad-web.left_nav.housekeeper',{
            type:type,
            index:index,
            orderId:$scope.orderId,
            tempAll:$scope.tempAll,
            clerkIds:clerkIds,
            clerkNames:clerkNames
        })
    }
    $scope.goOrderListm = function() {
        if($rootScope.projectGroupRelRelationDTOS == null && $rootScope.shopUserProductRelationDTOS == null && $rootScope.shopUserProjectRelationDTOS == null){
            return false
            alert("未选择产品或项目")
        }
        $scope.importData = {
            orderId: $scope.orderId,
            projectGroupRelRelationDTOS: $rootScope.projectGroupRelRelationDTOS,//套卡
            shopUserProductRelationDTOS: $rootScope.shopUserProductRelationDTOS,//产品
            shopUserProjectRelationDTOS: $rootScope.shopUserProjectRelationDTOS,//项目
            status: 1,
            shopUserRechargeCardDTO: $scope.shopUserRechargeCardDTO,
            orderPrice: $scope.tempAll, //总金额
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
                $rootScope.shopUserRechargeCardDTO = $scope.shopUserRechargeCardDTO
                if($rootScope.projectGroupRelRelationDTOS != undefined || $rootScope.shopUserProductRelationDTOS != undefined || $rootScope.shopUserProjectRelationDTOS != undefined){
                }else {
                    $rootScope.projectGroupRelRelationDTOS = $scope.projectGroupRelRelationDTOS
                    $rootScope.shopUserProductRelationDTOS = $scope.shopUserProductRelationDTOS
                    $rootScope.shopUserProjectRelationDTOS = $scope.shopUserProjectRelationDTOS
                }
                $scope.myChangeFn()
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
            $scope.myChangeFn()//重新计算金额
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
    }


    $scope.myChangeFn = function() {
        if('' == $scope.shopUserProductRelationDTOS && null == $scope.shopUserProjectRelationDTOS && null == $scope.projectGroupRelRelationDTOS){
            $scope.tempAll = 0
        }else{
            $scope.tempAll = 0
            var setTimer = setInterval(function() {
                if ($(".xiaoji").length != 0) {
                    clearInterval(setTimer)
                    //计算小计
                    for (var i = 0; i < $(".xiaoji").length; i++) {
                        $(".xiaoji").eq(i).find('input').val($(".xiaoji").eq(i).parent().prev().find('input').val() * $(".xiaoji").eq(i).parent().parent().parent().prev().find("input").val())
                    }
                    //计算总额
                    for (var i = 0; i < $(".xiaoji").length; i++) {
                        if ($(".xiaoji").eq(i).find('input').val() == "") {
                        } else {
                            $scope.tempAll += parseInt($(".xiaoji").eq(i).find('input').val().replace(",", ""))
                        }
                        $(".allPrice").html("总金额:" + $scope.tempAll)
                    }
                }
            }, 100)
        }
    }



    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-2)
    }
});