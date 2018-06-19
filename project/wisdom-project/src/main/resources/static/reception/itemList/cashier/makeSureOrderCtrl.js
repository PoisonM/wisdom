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

    $scope.goHousekeeper = function(type,index) {
        $state.go('pad-web.left_nav.housekeeper',{type:type,index:index})
    }
    $scope.goOrderListm = function() {
        console.log($scope.car)

        $scope.importData = {
            orderId: $scope.orderId,
            projectGroupRelRelationDTOS: $rootScope.projectGroupRelRelationDTOSTemp,//套卡
            shopUserProductRelationDTOS: $rootScope.shopUserProductRelationDTOSTemp,//产品
            shopUserProjectRelationDTOS: $rootScope.shopUserProjectRelationDTOSTemp,//项目
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
                /*for (var i = 0; i < $scope.projectGroupRelRelationDTOS.length; i++) {
                    $scope.projectGroupRelRelationDTOS[i].ng_markPrice = '';
                }*/
                $scope.shopUserProductRelationDTOS = data.responseData.shopUserProductRelationDTOS;
                /*for (var i = 0; i < $scope.shopUserProductRelationDTOS.length; i++) {
                    $scope.shopUserProductRelationDTOS[i].ng_markPrice = '';
                }*/
                $scope.shopUserProjectRelationDTOS = data.responseData.shopUserProjectRelationDTOS;
                /*for (var i = 0; i < $scope.shopUserProjectRelationDTOS.length; i++) {
                    $scope.shopUserProjectRelationDTOS[i].ng_markPrice = $scope.shopUserProjectRelationDTOS[i].sysShopProjectPurchasePrice * $scope.shopUserProjectRelationDTOS[i].discount;
                    $scope.shopUserProjectRelationDTOS[i].totalPrice = $scope.shopUserProjectRelationDTOS[i].ng_markPrice * $scope.shopUserProjectRelationDTOS[i].sysShopProjectInitTimes;
                }*/
                $scope.shopUserRechargeCardDTO = data.responseData.shopUserRechargeCardDTO;

                if($rootScope.projectGroupRelRelationDTOS != undefined){
                    // return
                }else {
                    $rootScope.projectGroupRelRelationDTOS = $scope.projectGroupRelRelationDTOS
                    $rootScope.shopUserProductRelationDTOS = $scope.shopUserProductRelationDTOS
                    $rootScope.shopUserProjectRelationDTOS = $scope.shopUserProjectRelationDTOS
                }
                $scope.myChangeFn()
            }
        })
    })
    $scope.deleteClick = function(e, id) {
        var virtualGoodsOrderInfo = {
            goodsType: e,
            operation: 1,
            orderId: $scope.orderId,
            shopUserProjectRelationDTOS: [{
                sysShopProjectId: id,
            }],
            shopUserProductRelationDTOS: [{
                shopProductId: id,
            }],
            projectGroupRelRelationDTOS: [{
                shopProjectGroupId: id,
            }]
        }
        switch (e) {
            case 0:
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            case 1:
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            case 3:
                delete virtualGoodsOrderInfo.shopUserProjectRelationDTOS;
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                break;
            case 4:
                delete virtualGoodsOrderInfo.shopUserProjectRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            default:
        }
        UpdateVirtualGoodsOrderInfo.save(virtualGoodsOrderInfo, function(data) {
            GetShopUserRecentlyOrderInfo.get({ sysUserId: $stateParams.userId  }, function(data) {
                $scope.projectGroupRelRelationDTOS = data.responseData.projectGroupRelRelationDTOS;
                $scope.shopUserProductRelationDTOS = data.responseData.shopUserProductRelationDTOS;
                $scope.shopUserProjectRelationDTOS = data.responseData.shopUserProjectRelationDTOS;
            })
        })
    }
    $scope.getTotalPrice = function() {

    }

    $scope.tempAll = 0;
    $scope.myChangeFn = function() {
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
    $scope.myChangeFn()



    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-2)
    }
});