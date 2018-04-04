/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("productDetailsCtrl", function($scope, $state, $stateParams,$rootScope) {
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
})