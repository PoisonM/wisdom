/**
 * Created by Administrator on 2018/3/30.
 */
PADWeb.controller("detailsCtrl", function($scope, $state, $stateParams) {
    console.log("detailsCtrl")
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