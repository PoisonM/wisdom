PADWeb.controller("productDetailsCtrl", function($scope, $state, $stateParams,$rootScope,ProductInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.headerPrice.title = "产品详情";
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
    console.log("productDetailsCtrl")
    $rootScope.title="产品详情";
    /*开关设置如果没有侧边栏*/
    $scope.$parent.mainSwitch.footerBoxFlag=false;
    /*轮播图*/
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
    ProductInfo.get({productId:$stateParams.id},function (data) {
        if(data.result == "0x00001"){
            $scope.productInformation = data.responseData
        }
    })
    $scope.$parent.priceListBlackFn = function () {
        $state.go("pad-web.left_nav.product")
    }

})