PADWeb.controller('consumptionListCtrl', function($scope, $stateParams, ngDialog, Archives, SearchShopProjectList, SearchShopProductList, GetShopProjectGroups, ThreeLevelProject, productInfoThreeLevelProject, UpdateVirtualGoodsOrderInfo, SaveShopUserOrderInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.param.headerCash.backContent = "充值记录";
    $scope.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.flagFn = function(bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.mainSwitch.headerCashFlag.leftFlag = bool;
        $scope.$parent.mainSwitch.headerCashFlag.middleFlag = bool;
        $scope.$parent.mainSwitch.headerCashFlag.rightFlag = bool;
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.select = 0;
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
                $scope.getThreeCategories(second[secondkey].projectTypeOneId, second[secondkey].projectTypeTwoId);
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

    $scope.tabclick(0);
    $scope.getThreeCategories = function(one, two) {
        ThreeLevelProject.get({
            pageSize: 100,
            projectName: "",
            projectTypeOneId: one,
            ProjectTypeTwoId: two,
        }, function(data) {
            $scope.threeCategories = data.responseData;
        });
    }
    $scope.getProductInfoThreeLevelProject = function(one, two) {
        productInfoThreeLevelProject.get({
            pageSize: 100,
            productName: "",
            productTypeOneId: one,
            productTypeTwoId: two,
        }, function(data) {
            $scope.threeCategories = data.responseData;
        });

    }
    $scope.updateVirtualGoodsOrderInfo = function() {
        UpdateVirtualGoodsOrderInfo.save({

        }, function() {

        })
    }
    $scope.saveShopUserOrderInfo = function() {
        SaveShopUserOrderInfo.save({

        }, function() {

        })
    }
});