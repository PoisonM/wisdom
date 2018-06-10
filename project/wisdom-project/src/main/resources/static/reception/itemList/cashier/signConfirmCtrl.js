PADWeb.controller('signConfirmCtrl', function($scope, $stateParams
    , $state, ngDialog, Archives, SearchRechargeConfirm, RechargeCardSignConfirm
    , ImageBase64UploadToOSS, GetShopUserRecentlyOrderInfo,ConsumeFlowNo,PaySignConfirm) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
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
        ConsumeFlowNo.get({
            consumeFlowNo:$stateParams.orderId
        },function (data) {
            $scope.consumeListInfo = data.responseData
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
                    }

                })
            }else if($state.params.orderId != ''){
                PaySignConfirm.save({
                    orderId:$stateParams.orderId,
                    imageUrl: data.responseData,
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