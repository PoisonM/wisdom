/**
 * Created by Administrator on 2018/3/30.
 */
PADWeb.controller("projectDetailsCtrl", function($scope, $state, $stateParams,ProjectInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.headerPrice.title = "项目详情";
    $scope.flagFn = function (bool) {
        //头
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.mainSwitch.headerCashAllFlag = !bool;
        $scope.$parent.mainSwitch.headerPriceListAllFlag = bool;
        $scope.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.mainSwitch.headerPriceListBlackFlag = !bool

    };
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);
    console.log("projectDetailsCtrl");
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
    ProjectInfo.get({id:1},function (data) {
        console.log($stateParams.projectTypeOneId)
        console.log(data)
    })
});