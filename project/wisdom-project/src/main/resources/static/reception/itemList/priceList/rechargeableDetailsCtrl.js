/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("rechargeableDetailsCtrl", function($scope, $state, $stateParams,$rootScope,GetCardInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.headerPrice.title = "充值卡详情";
    $scope.$parent.param.headerPrice.saveContent = "";
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
    $scope.$parent.mainSwitch.footerBoxFlag=false;

    $scope.timeer = setInterval(function () {
        if($(".swiper-slide").length!=0){
            clearInterval($scope.timeer)
            var mySwiper = new Swiper('.swiper-container',{
                slidesPerView : 3,
                slidesPerGroup : 1,
                spaceBetween : 20,
            })
        }
    },100)

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
        rechargeDetail:{},
        urlArray:{}
    };
    $rootScope.loadingFlag = true;
    GetCardInfo.get({
        id:$stateParams.shopRechargeCardId
    },function (data) {
        if(data.result == "0x00001"){
            $scope.param.rechargeDetail = data.responseData;
            $rootScope.loadingFlag = false;
        }
    })


    $scope.$parent.priceListBlackFn = function () {
        $state.go("pad-web.left_nav.setCard")
    }
})