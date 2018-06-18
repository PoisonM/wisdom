PADWeb.controller('getProductCtrl', function($scope, $stateParams, $state, ngDialog, Archives, GetUserProductInfo, ConsumesUserProduct) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "充值记录";
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

    $scope.shopUserConsumeDTO ={
        clerkId: '',
        consumeId: '',
        consumeNum: 1,
        imageUrl:'',
        consumePrice: ''
    }

    $scope.goConfirmations = function() {
        ConsumesUserProduct.save(
            $scope.shopUserConsumeDTO
        , function(data) {
            if(data.result == '0x00001'){
                $state.go('pad-web.confirmations', { consumeId: data.responseData, shopProjectInfoName: $scope.getproduct.shopProductName })
            }
            else{
                alert(data.errorInfo);
            }
        })
    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }

    $scope.productNumSub = function () {
        if($scope.shopUserConsumeDTO.consumeNum<=0){
            alert("领取数量不能小于0");
            return;
        }
        $scope.shopUserConsumeDTO.consumeNum = $scope.shopUserConsumeDTO.consumeNum -1;
    }
    $scope.changeProductNum = function () {
        if($scope.shopUserConsumeDTO.consumeNum > $scope.productInfo.surplusTimes){
            alert("领取数量大于产品待领取数量");
            return;
        }
    }
    $scope.productNumInc = function () {
        $scope.shopUserConsumeDTO.consumeNum = $scope.shopUserConsumeDTO.consumeNum +1;
    }

    GetUserProductInfo.get({
        userProductInfoId: $state.params.id
    }, function(data) {
        $scope.productInfo = data.responseData;
        $scope.shopUserConsumeDTO.consumeId = data.id;
        $scope.shopUserConsumeDTO.consumeId = $state.params.id;
    })
});