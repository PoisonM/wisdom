PADWeb.controller('consumeCardDetailCtrl', function($scope, $stateParams, $state, ngDialog
    ,GetCureByflowId,GetCompleteByflowId,GetConsumeDetail) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.title = "消费记录";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.$parent.param.selectSty = $stateParams.userId
    $scope.$parent.$parent.backHeaderCashFn = function () {
        window.history.go(-1)
    }

    if($stateParams.type == "lck"){
        GetCureByflowId.get({
            flowId:$stateParams.id
        },function (data) {
            if(data.result == "0x00001"){
                $scope.dataInfo = data.responseData
            }
        })
    }else if($stateParams.type == "tk"){
        GetCompleteByflowId.get({
            id:$stateParams.id
        },function (data) {
            if(data.result == "0x00001"){
                $scope.dataInfo = data.responseData
            }
        })
    }else if($stateParams.type == "cp"){
        GetConsumeDetail.save({
            consumeType:"0",
            flowId:$stateParams.id,
            goodsType:"4"//产品详情传4 充值卡订单详情传2
        },function (data) {
            if(data.result == "0x00001"){
                $scope.dataInfo = data.responseData
            }
        })
    }

});