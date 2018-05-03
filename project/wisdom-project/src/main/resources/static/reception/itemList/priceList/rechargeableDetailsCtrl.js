/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("rechargeableDetailsCtrl", function($scope, $state, $stateParams,$rootScope,GetCardInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.headerPrice.title = "充值卡详情"
    $scope.flagFn = function (bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = !bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.mainSwitch.headerPriceListBlackFlag = !bool

    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)
    console.log("rechargeableDetailsCtrl");
    $scope.$parent.mainSwitch.footerBoxFlag=false;
    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 3,
        loop: true,
        autoplayDisableOnInteraction : false,
        autoplay:1500,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
    });

    $scope.param={
        rechargeList:{},
        urlArray:{}
    }
    GetCardInfo.get({id:"2"},function (data) {
        $scope.rechargeList=data.responseData;
        //将轮播图数据截取
        /*var url= $scope.rechargeList.imageUrl;
        $scope.param.urlArray = url.split(",");*/
        console.log(data)
    })
})