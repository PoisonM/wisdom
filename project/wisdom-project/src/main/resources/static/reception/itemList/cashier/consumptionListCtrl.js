PADWeb.controller('consumptionListCtrl', function($scope, $state, $stateParams, ngDialog, Archives, SearchShopProjectList, SearchShopProductList, GetShopProjectGroups, ThreeLevelProject, productInfoThreeLevelProject, UpdateVirtualGoodsOrderInfo, SaveShopUserOrderInfo, GetConsumeDisplayIds) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.param.headerPrice.title = "消费";
    $scope.$parent.param.headerPrice.saveContent = "确认"
    $scope.flagFn = function (bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.mainSwitch.headerCashAllFlag = !bool;
        $scope.$parent.mainSwitch.headerPriceListAllFlag = bool;
        $scope.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.mainSwitch.headerPriceListBlackFlag = !bool
    };
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);
    $scope.$parent.priceListBlackFn = function () {
        window.history.go(-1)
    }


    //获取订单ID
    SaveShopUserOrderInfo.save({
        userId: $stateParams.userId
    }, function(data) {
        $scope.orderId = data.responseData;
        GetConsumeDisplayIds.get({
            orderId: data.responseData
        }, function(data) {
            $scope.theSelected = data.responseData;
            if ($scope.theSelected.periodCard == undefined) {
                $scope.theSelected.periodCard = {
                    "periodCardSize": 0,
                    "periodCardIds": []
                }
            }
            if ($scope.theSelected.product == undefined) {
                $scope.theSelected.product = {
                    "productIds": [],
                    "productSize": 0
                }
            }
            if ($scope.theSelected.timeCard == undefined) {
                $scope.theSelected.timeCard = {
                    "timeCardSize": 0,
                    "timeCardIds": []
                }
            }
            if ($scope.theSelected.groupCard == undefined) {
                $scope.theSelected.groupCard = {
                    "groupSize": 0,
                    "groupIds": []
                }
            }
        })
    })


    $scope.select = 1;
    $scope.tabclick = function(e) {
        if (e == 3) {
            $scope.consumptionListUlShow = false;
            $scope.pl100 = '{"padding-left": "100px"}';
        } else {
            $scope.consumptionListUlShow = true;
            $scope.pl100 = '{}'
        }
        $scope.select = e;
        if (e == 0 || e == 1) {
            SearchShopProjectList.get({
                filterStr: '',
                useStyle: e,
            }, function(data) {
                $scope.secondCategory = data.responseData.detailLevel;
                //默认展示第一个
                $scope.timeInt = setInterval(function () {
                    if($(".two_project_cla").length != 0){
                        $(".two_project_cla").eq(0).trigger('click')
                        clearInterval($scope.timeInt)
                    }
                },100)
            })
        } else if (e == 2) {
            SearchShopProductList.get({
                pageSize: "100",
                filterStr: '',
            }, function(data) {
                $scope.secondCategory = data.responseData.detailLevel;
                var first = data.responseData.detailLevel[0];
                var firstkey = '',
                    secondkey = '';
                for (var key in first) {
                    firstkey = key;
                    break
                }
                var second = first[firstkey];
                for (var key in second) {
                    secondkey = key;
                    break
                }
                $scope.getProductInfoThreeLevelProject(second[secondkey].productTypeOneId, second[secondkey].productTypeTwoId);
            })
        } else if (e == 3) {
            GetShopProjectGroups.get({
                projectGroupName: '',
                pageSize: 100
            }, function(data) {
                $scope.threeCategories = data.responseData;
            })
        }
    }

    $scope.tabclick(1);
    $scope.getThreeCategories = function(one, two,id) {
        $scope.redFontFlag = id
        ThreeLevelProject.get({
            pageSize: 100,
            projectName: "",
            projectTypeOneId: one,
            ProjectTypeTwoId: two,
            useStyle: $scope.select,
        }, function(data) {
            $scope.threeCategories = data.responseData;
        });
    }
    $scope.getProductInfoThreeLevelProject = function(one, two ,index) {
        $scope.redFontFlag = index
        productInfoThreeLevelProject.get({
            pageSize: 100,
            productName: "",
            productTypeOneId: one,
            productTypeTwoId: two,
        }, function(data) {
            $scope.threeCategories = data.responseData;
        });
    }
    $scope.updateVirtualGoodsOrderInfo = function(e, res) {


        Array.prototype.indexOf = function(val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) return i;
            }
            return -1;
        };
        Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
        var bar = 0;
        switch (e) {
            case 0:
                if ($scope.theSelected.timeCard.timeCardIds.indexOf(res.id) != -1) { bar = 1; }
                break;
            case 1:
                if ($scope.theSelected.periodCard.periodCardIds.indexOf(res.id) != -1) { bar = 1; }
                break;
            case 3:
                if ($scope.theSelected.groupCard.groupIds.indexOf(res.id) != -1) { bar = 1; }
                break;
            case 4:
                if ($scope.theSelected.product.productIds.indexOf(res.id) != -1) { bar = 1; }
                break;
            default:
        }
        var virtualGoodsOrderInfo = {
            goodsType: e,
            operation: bar,
            orderId: $scope.orderId,
            shopUserProjectRelationDTOS: [{
                serviceTime: res.serviceTimes,
                sysShopProjectId: res.id,
                sysShopProjectPurchasePrice: res.marketPrice,
                sysShopProjectInitTimes: '1',
                sysShopProjectName: res.projectName,
                sysUserId: $stateParams.userId,
                useStyle: res.useStyle,
            }],
            shopUserProductRelationDTOS: [{
                purchasePrice: res.marketPrice,
                initTimes: '1',
                shopProductId: res.id,
                shopProductName: res.productName,
                sysUserId: $stateParams.userId,
            }],
            projectGroupRelRelationDTOS: [{
                shopGroupPuchasePrice: res.marketPrice,
                projectInitTimes: '1',
                projectSurplusAmount: '',
                projectSurplusTimes: '',
                shopProjectGroupId: res.id,
                shopProjectGroupName: res.projectGroupName,
                sysUserId: $stateParams.userId,
            }]
        }
        switch (e) {
            case 0:
                if ($scope.theSelected.timeCard.timeCardIds.indexOf(res.id) != -1) { bar = 1; }
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            case 1:
                if ($scope.theSelected.periodCard.periodCardIds.indexOf(res.id) != -1) { bar = 1; }
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            case 3:
                if ($scope.theSelected.groupCard.groupIds.indexOf(res.id) != -1) { bar = 1; }
                delete virtualGoodsOrderInfo.shopUserProjectRelationDTOS;
                delete virtualGoodsOrderInfo.shopUserProductRelationDTOS;
                break;
            case 4:
                if ($scope.theSelected.product.productIds.indexOf(res.id) != -1) { bar = 1; }
                delete virtualGoodsOrderInfo.shopUserProjectRelationDTOS;
                delete virtualGoodsOrderInfo.projectGroupRelRelationDTOS;
                break;
            default:
        }
        UpdateVirtualGoodsOrderInfo.save(virtualGoodsOrderInfo, function() {
            switch (e) {
                case 0:
                    if ($scope.theSelected.timeCard.timeCardIds.indexOf(res.id) == -1) {
                        $scope.theSelected.timeCard.timeCardIds.push(res.id);
                        $scope.theSelected.timeCard.timeCardSize++;
                    } else {
                        $scope.theSelected.timeCard.timeCardIds.remove(res.id);
                        $scope.theSelected.timeCard.timeCardSize--;
                    }
                    break;
                case 1:
                    if ($scope.theSelected.periodCard.periodCardIds.indexOf(res.id) == -1) {
                        $scope.theSelected.periodCard.periodCardIds.push(res.id);
                        $scope.theSelected.periodCard.periodCardSize++;
                    } else {
                        $scope.theSelected.periodCard.periodCardIds.remove(res.id);
                        $scope.theSelected.periodCard.periodCardSize--;
                    }
                    break;
                case 3:
                    if ($scope.theSelected.groupCard.groupIds.indexOf(res.id) == -1) {
                        $scope.theSelected.groupCard.groupIds.push(res.id);
                        $scope.theSelected.groupCard.groupSize++;
                    } else {
                        $scope.theSelected.groupCard.groupIds.remove(res.id);
                        $scope.theSelected.groupCard.groupSize--;
                    }
                    break;
                case 4:
                    if ($scope.theSelected.product.productIds.indexOf(res.id) == -1) {
                        $scope.theSelected.product.productIds.push(res.id);
                        $scope.theSelected.product.productSize++;
                    } else {
                        $scope.theSelected.product.productIds.remove(res.id);
                        $scope.theSelected.product.productSize--;
                    }
                    break;
                default:
            }
        })
    }
    $scope.$parent.priceListSaveFn = function () {
        $state.go('pad-web.left_nav.makeSureOrder',{"userId":$stateParams.userId})
    }
    

});