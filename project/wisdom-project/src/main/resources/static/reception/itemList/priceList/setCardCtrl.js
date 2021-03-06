/**
 * Created by Administrator on 2018/4/16.
 */
PADWeb.controller("setCardCtrl", function($scope, $state, $stateParams,$rootScope,GetRechargeCardList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.$parent.param.headerPrice.blackTitle = "充值卡"
    $scope.$parent.param.priceType = "czk"
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = !bool
        $scope.$parent.mainLeftSwitch.priceListFlag = bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerPriceListBlackFlag = bool

    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)
    console.log("setCardCtrl");
    $rootScope.title="充值卡";
    $scope.goCardDetails=function (shopRechargeCardId) {
        $state.go("pad-web.rechargeableDetails",{shopRechargeCardId:shopRechargeCardId})
    }
    $scope.param={
        name:"",
        pageSize:"100",
        cardList:{}
    }
    $scope.status = '0';
    $rootScope.loadingFlag = true;
    GetRechargeCardList.get({
        name:$scope.param.name,
        pageSize:$scope.param.pageSize,
        status:$scope.status
    },function (data) {
        if(data.result == "0x00001"){
            $rootScope.loadingFlag = false;
            $scope.cardList=data.responseData;
        }
    })
})