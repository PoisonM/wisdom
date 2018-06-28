PADWeb.controller('drawCardConsumptionCtrl', function($scope,$rootScope, $stateParams, $state
    , ngDialog, Archives,GetUserCourseProjectList,GetShopUserProjectGroupRelRelationInfo
    ,ConsumeCourseCard, ConsumesDaughterCard,ImageBase64UploadToOSS) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "划卡";
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
    $scope.staffListNames = $rootScope.staffListNames//关联员工
    $scope.staffListIds = $rootScope.staffListIds


    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }
    $scope.goHousekeeper = function() {
        $state.go('pad-web.left_nav.housekeeper')
    }
    $scope.goSignConfirm = function() {
        $state.go('pad-web.signConfirm')
    }
    $scope.checkBoxChek = function(e) {
        $(e.target).children('.checkBox').css('background', '#FF6666')
    }
    //type 1为疗程卡，2为套卡
    $scope.params = {
        type:$state.params.type,
        id : $state.params.id
    }
    //初始划卡参数
    $scope.shopUserConsumeDTO = []
    $scope.shopUserConsumeDTO.push({
        sysClerkId: '',
        consumeId: '1',
        consumeNum: 0,
        consumePrice: 0,
        consumeOncePrice:0,
        imageUrl:'',
        detail:''
    })
    //查询用户套卡
    if ($scope.params.type == 2) {
        GetShopUserProjectGroupRelRelationInfo.get({
            shopUserProjectGroupRelRelationId: $state.params.id
        }, function(data) {
            $scope.responseData = data.responseData;
            $scope.shopUserConsumeDTO[0].consumePrice = Math.round($scope.responseData.projectInitAmount/$scope.responseData.projectInitTimes);
            $scope.shopUserConsumeDTO[0].consumeOncePrice = Math.round($scope.responseData.projectInitAmount/$scope.responseData.projectInitTimes);
            $scope.shopUserConsumeDTO[0].consumeNum = 1;
            $scope.shopUserConsumeDTO[0].consumeId = $scope.responseData.id;
            console.log($state.responseData)
        })
    }
    //查询用户疗程卡
    if($scope.params.type == 1){
        GetUserCourseProjectList.get({
            id: $state.params.id
        }, function(data) {
            $scope.responseData = data.responseData[0];
            $scope.shopUserConsumeDTO[0].consumePrice = Math.round($scope.responseData.sysShopProjectPurchasePrice/$scope.responseData.serviceTime);
            $scope.shopUserConsumeDTO[0].consumeOncePrice = Math.round($scope.responseData.sysShopProjectPurchasePrice/$scope.responseData.serviceTime);
            $scope.shopUserConsumeDTO[0].consumeNum = 1;
            $scope.shopUserConsumeDTO[0].consumeId = $scope.responseData.id;
            console.log($state.responseData)
        })
    }
    //购买价格
    $scope.changePrice = function () {
        if($scope.shopUserConsumeDTO[0].consumeNum <= 1){
            return
        }
        $scope.shopUserConsumeDTO[0].consumeNum = $scope.shopUserConsumeDTO[0].consumeNum-1
        $scope.shopUserConsumeDTO[0].consumePrice = Math.round($scope.shopUserConsumeDTO[0].consumeNum*$scope.shopUserConsumeDTO[0].consumeOncePrice);
    }

    //点击按钮，购买数量+1
    $scope.consumeNumInc = function () {
        $scope.shopUserConsumeDTO[0].consumeNum = $scope.shopUserConsumeDTO[0].consumeNum+1;
        $scope.shopUserConsumeDTO[0].consumePrice = Math.round($scope.shopUserConsumeDTO[0].consumeNum*$scope.shopUserConsumeDTO[0].consumeOncePrice);
    }

    //手动改动购买数量
    $scope.changeConsumeNum = function () {
        if($scope.shopUserConsumeDTO[0].consumeNum > $scope.responseData.sysShopProjectSurplusTimes){
            alert("对不起，划卡数量不能大于剩余卡数^_^");
            return;
        }
        $scope.shopUserConsumeDTO[0].consumePrice = Math.round($scope.shopUserConsumeDTO[0].consumeNum*$scope.shopUserConsumeDTO[0].consumeOncePrice);
    }

    $scope.goConfirmations = function() {
        if($scope.shopUserConsumeDTO[0].consumeNum > $scope.responseData.sysShopProjectSurplusTimes){
            alert("对不起，划卡数量不能大于剩余卡数^_^");
            return;
        }

        ImageBase64UploadToOSS.save({
            imageStr: $("#signConfirmRight").jSignature("getData"),
        }, function(data) {
            $scope.shopUserConsumeDTO[0].imageUrl = data.responseData
            if($scope.staffListIds == undefined){
                $scope.shopUserConsumeDTO[0].sysClerkId = "";
                $scope.shopUserConsumeDTO[0].sysClerkName = ""
            }else {
                $scope.shopUserConsumeDTO[0].sysClerkId = $scope.staffListIds.join(";");
                $scope.shopUserConsumeDTO[0].sysClerkName = $scope.staffListNames.join(";");
            }
            $scope.shopUserConsumeDTO.imageUrl = $("#signConfirmRight").jSignature("getData")

            //疗程卡划卡
            if($scope.params.type== 1){
                ConsumeCourseCard.save({
                    shopUserConsumeDTO: $scope.shopUserConsumeDTO
                }, function(data) {
                    if('0x00001' == data.result){
                        $rootScope.staffListNames=[]//保存清除关联员工
                        $rootScope.staffListIds=[]
                        alert("保存成功")
                        window.history.go(-1)
                    }else{
                        alert(data.errorInfo);
                    }
                })
            }

            //套卡划卡
            if($scope.params.type == 2){
                ConsumesDaughterCard.save({
                    shopUserConsumeDTO: $scope.shopUserConsumeDTO
                }, function(data) {
                    if('0x00001' == data.result){
                        $rootScope.staffListNames=[]//保存清除关联员工
                        $rootScope.staffListIds=[]
                        alert("保存成功")
                        window.history.go(-1)
                    }else{
                        alert(data.errorInfo);
                    }
                })
            }
        })


    }

    var $signature = $("#signConfirmRight").jSignature({
        'height': 450,
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

    // $("#signConfirmRight").jSignature("getData")//传给后台的值
});