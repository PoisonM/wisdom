PADWeb.controller('confirmationsCtrl', function($scope, $stateParams, $state, ngDialog, Archives, ImageBase64UploadToOSS, UpdateConsumeRecord) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    // $scope.$parent.param.headerCash.leftContent = "档案(9010)"
    $scope.$parent.param.headerCash.leftAddContent = "添加档案"
    $scope.$parent.param.headerCash.backContent = "充值记录"
    $scope.$parent.param.headerCash.leftTip = "保存"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    /*$scope.flagFn = function(bool) {
    //左
    $scope.mainLeftSwitch.peopleListFlag = bool
    $scope.mainLeftSwitch.priceListFlag = !bool
    //头
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
    $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
}*/
    /*打开收银头部/档案头部/我的头部*/
    /*$scope.flagFn(true)*/

    var $signature = $("#signConfirmRight").jSignature({
        'height': 900,
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
    $scope.shopProjectInfoName = $state.params.shopProjectInfoName;

    $scope.$parent.$parent.leftTipFn = function() {
        ImageBase64UploadToOSS.save({
            imageStr: $("#signConfirmRight").jSignature("getData")
        }, function(data) {
            UpdateConsumeRecord.get({
                consumeId: $state.params.consumeId,
                image: data.responseData,
            }, function(data) {
                $state.go("pad-web.left_nav.personalFile");
            })

        })
    }
});