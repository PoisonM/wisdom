PADWeb.controller('getProductCtrl', function($scope, $stateParams, $state, ngDialog, Archives
    , GetUserProductInfo, ConsumesUserProduct,ImageBase64UploadToOSS) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
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

    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper')
    }

    $scope.shopUserConsumeDTO ={
        clerkId: '',
        consumeId: '',
        consumeNum: 1,
        imageUrl:'',
        consumePrice: '',
        sysUserId: ''
    }

    var $signature = $("#signConfirmRight").jSignature({
        'height': 400,
    });
    $signature.jSignature('reset')

    //获取数据
    var data = $signature.jSignature('getData', 'default')
    //图片展示
    var img = new Image()
    img.src = data
    $(img).appendTo($('#signimg'))
    //将数据显示在文本框
    $('#text').val(data)

    ImageBase64UploadToOSS.save({
        imageStr: $("#signConfirmRight").jSignature("getData")
    }, function(data) {
       /* UpdateConsumeRecord.get({
            consumeId: $state.params.consumeId,
            image: data.responseData,
        }, function(data) {
            $state.go("pad-web.left_nav.personalFile");
        })*/

    })

    $scope.goConfirmations = function() {
        ImageBase64UploadToOSS.save({
            imageStr: $("#signConfirmRight").jSignature("getData")
        }, function(data) {
            $scope.shopUserConsumeDTO.imageUrl = data.responseData
            ConsumesUserProduct.save(
                $scope.shopUserConsumeDTO
                , function(data) {
                    if(data.result == '0x00001'){
                        alert("领取成功")
                        window.history.go(-1)
                    }
                    else{
                        alert(data.errorInfo);
                    }
                })
        })


    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }

    $scope.productNumSub = function () {
        if($scope.shopUserConsumeDTO.consumeNum<=1){
            $scope.cccFlag = true
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
        $scope.shopUserConsumeDTO.shopProductId = $scope.productInfo.shopProductId;
        $scope.shopUserConsumeDTO.sysUserId = $scope.productInfo.sysUserId;
        $scope.shopUserConsumeDTO.consumeId = $state.params.id;
    })
});