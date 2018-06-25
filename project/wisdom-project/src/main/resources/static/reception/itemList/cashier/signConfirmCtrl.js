PADWeb.controller('signConfirmCtrl', function($scope, $stateParams
    , $state, ngDialog, Archives, SearchRechargeConfirm, RechargeCardSignConfirm
    , ImageBase64UploadToOSS, GetShopUserRecentlyOrderInfo,ConsumeFlowNo,PaySignConfirm
    ,GetOrderConsumeDetailInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.param.headerPrice.title = "签字确认";
    $scope.$parent.param.headerPrice.saveContent = ""
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

    var $signature = $("#signConfirmRight").jSignature({
        'height': 500,
    });
    $signature.jSignature('reset')

    //获取数据 
    var data = $signature.jSignature('getData', 'default')
    //图片展示
    var img = new Image()
    img.src = data
    $(img).appendTo($('#signimg'))



    //将数据显示在文本框
    if ($state.params.transactionId != '') {
        $scope.rechargeConsumeFlag = true
        SearchRechargeConfirm.get({//充值签字确认
            transactionId: $state.params.transactionId,
        }, function(data) {
            $scope.responseData = data.responseData;
        })
    } else if ($state.params.orderId != '') {
        $scope.rechargeConsumeFlag = false
        /*ConsumeFlowNo.get({
            consumeFlowNo:$stateParams.orderId
        },function (data) {
            $scope.consumeListInfo = data.responseData
        })*/

        GetOrderConsumeDetailInfo.get({
            orderId:$stateParams.orderId
        },function (data) {
            $scope.userInfo = data.responseData.userInfo
            $scope.shopUserPayDTO = data.responseData.shopUserPayDTO
            if(data.responseData.groupList != undefined){
                $scope.groupList = data.responseData.groupList//套卡
            }else {
                $scope.groupList = []
            }
            if(data.responseData.periodProjectList != undefined){
                $scope.periodProjectList = data.responseData.periodProjectList//疗程
            }else{
                $scope.periodProjectList = []
            }
            if(data.responseData.productList != undefined){
                $scope.productList = data.responseData.productList//产品
            }else{
                $scope.productList = []
            }
            if(data.responseData.timeProjectList != undefined){
                $scope.timeProjectList = data.responseData.timeProjectList//单次
            }else{
                $scope.timeProjectList = []
            }
        })
    }

    $scope.clickOk = function() {
        ImageBase64UploadToOSS.save({ imageStr: $("#signConfirmRight").jSignature("getData") }, function(data) {
            if($state.params.transactionId != ''){
                RechargeCardSignConfirm.get({
                    transactionId: $state.params.transactionId,
                    imageUrl: data.responseData,
                }, function(data) {
                    if(data.result == "0x00001"){
                        $state.go("pad-web.left_nav.blankPage");
                    }else{
                        alert(data.errorInfo);
                        $state.go("pad-web.left_nav.blankPage");
                    }

                })
            }else if($state.params.orderId != ''){
                PaySignConfirm.save({
                    orderId:$stateParams.orderId,
                    imageUrl: data.responseData,
                    signUrl: data.responseData,
                },function (data) {
                    if(data.result == "0x00001"){
                        $state.go("pad-web.left_nav.blankPage");
                    }
                })
            }

        })
    }


    $scope.$parent.priceListBlackFn = function () {
        window.history.go(-1)
    }
});