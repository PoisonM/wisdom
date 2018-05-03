/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("rechargeableCardCtrl", function($scope, $state, $stateParams,$rootScope,GetShopProjectGroups) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.$parent.param.headerPrice.blackTitle = "套卡"
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
    console.log("rechargeableCardCtrl");
    $rootScope.title="充值卡";
    $scope.goRechargeableDetails=function (id) {
        $state.go("pad-web.cardDetails",{id:id})
    }
    $scope.param={
        projectGroupName:"",
        pageSize:"15",
        rechargeableCardList:{}
    }
    GetShopProjectGroups.get({projectGroupName: $scope.param.projectGroupName,pageSize:$scope.param.pageSize},function (data) {
       $scope.rechargeableCardList=data.responseData;
      console.log(data)
    })
})
