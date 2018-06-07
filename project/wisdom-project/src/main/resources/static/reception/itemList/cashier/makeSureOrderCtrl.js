PADWeb.controller('makeSureOrderCtrl', function($scope, $stateParams, $state, ngDialog, Archives, SaveShopUserOrderInfo, GetShopUserRecentlyOrderInfo, UpdateVirtualGoodsOrderInfo, UpdateShopUserOrderInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "充值记录";
    $scope.$parent.$parent.param.headerCash.leftTip = "添加更多";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
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
    $scope.car = 1;
    $scope.$parent.$parent.leftTipFn = function() {
        $state.go('pad-web.consumptionList');
    }
    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper')
    }
    $scope.goOrderListm = function() {
        console.log($scope.car)
        UpdateShopUserOrderInfo.save({
            orderId: $scope.orderId,
            projectGroupRelRelationDTOS: $scope.projectGroupRelRelationDTOS,
            shopUserProductRelationDTOS: $scope.shopUserProductRelationDTOS,
            shopUserProjectRelationDTOS: $scope.shopUserProjectRelationDTOS,
            status: 1,
            shopUserRechargeCardDTO: $scope.shopUserRechargeCardDTO,
            orderPrice: $scope.tempAll, //总金额
        }, function(data) {
            $state.go('pad-web.left_nav.orderList', { orderId: $scope.orderId })
        })
    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }
    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType', { type: 1 });
    }

    SaveShopUserOrderInfo.save({ userId: '110' }, function(data) {
        $scope.orderId = data.responseData;
    })

    GetShopUserRecentlyOrderInfo.get({ sysUserId: '110' }, function(data) {
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
            GetShopUserRecentlyOrderInfo.get({ sysUserId: '110' }, function(data) {
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
                    // $(".xiaoji").eq(i).parent().prev().find('input').val()//数量
                    // $(".xiaoji").eq(i).parent().parent().parent().prev().find("input").val()//折扣价

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
});