PADWeb.controller('drawCardConsumptionCtrl', function($scope, $stateParams, $state, ngDialog, Archives, GetShopUserProjectGroupRelRelationInfo, ConsumesDaughterCard) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "还欠款";
    $scope.$parent.$parent.param.headerCash.backContent = "用户档案";
    $scope.$parent.$parent.param.headerCash.leftTip = "保存";
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

    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper')
    }
    $scope.goSignConfirm = function() {
        $state.go('pad-web.signConfirm')
    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }
    if ($state.params.type = 2) {
        GetShopUserProjectGroupRelRelationInfo.get({
            shopUserProjectGroupRelRelationId: $state.params.id
        }, function(data) {
            $scope.responseData = data.responseData;

            console.log($state.responseData)
        })
    }
    $scope.goConfirmations = function() {
        ConsumesDaughterCard.save({
            shopUserConsumeDTO: [{
                clerkId: '',
                consumeId: $scope.responseData.id,
                consumeNum: $scope.responseData.shopProjectGroupNumber,
                consumePrice: $scope.responseData.shopGroupPuchasePrice,
                sysUserId: '110',
            }]
        }, function(data) {
            $state.go('pad-web.confirmations', {
                consumeId: data.responseData,
                shopProjectInfoName: $scope.responseData.shopProjectInfoName,
            })
        })
    }
});