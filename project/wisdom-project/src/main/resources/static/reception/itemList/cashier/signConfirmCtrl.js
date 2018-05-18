PADWeb.controller('signConfirmCtrl', function($scope, $stateParams, $state, ngDialog, Archives, SearchRechargeConfirm, RechargeCardSignConfirm, ImageUploadToOSS) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.param.headerCash.backContent = "充值记录";
    $scope.$parent.param.headerCash.leftTip = "保存";
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    /*$scope.flagFn = function(bool) {
    //左
    $scope.mainLeftSwitch.peopleListFlag = bool
    $scope.mainLeftSwitch.priceListFlag = !bool
    //头
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool
    $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool
    $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }*/
    /*打开收银头部/档案头部/我的头部*/
    /*$scope.flagFn(true)*/

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
    SearchRechargeConfirm.get({
        transactionId: $state.params.transactionId,
    }, function(data) {
        $scope.responseData = data.responseData;
    })

    function convertBase64UrlToBlob(urlData, type) {
        var bytes = window.atob(urlData.split(',')[1]);
        //去掉url的头，并转换为byte
        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
            ia[i] = bytes.charCodeAt(i);
        }
        return new Blob([ab], { type: 'image/' + type });
    }

    $scope.clickOk = function() {

        /*RechargeCardSignConfirm.get({
            transactionId: $state.params.transactionId,
            //图片base64流是data
            imageUrl: 'http://dizmix.com/upload/534bef3095584e9893511c45753e5c21_WechatIMG96.jpeg',
        }, function(data) {
            $state.go("pad-web.left_nav.personalFile");
        })*/
    }
});