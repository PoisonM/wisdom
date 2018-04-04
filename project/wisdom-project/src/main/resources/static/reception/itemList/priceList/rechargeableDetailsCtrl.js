/**
 * Created by Administrator on 2018/4/3.
 */
PADWeb.controller("rechargeableDetailsCtrl", function($scope, $state, $stateParams,$rootScope) {
    console.log("rechargeableDetailsCtrl");
    $rootScope.title="充值卡详情";
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
})