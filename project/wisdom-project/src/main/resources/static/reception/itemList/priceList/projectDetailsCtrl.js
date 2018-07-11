PADWeb.controller("projectDetailsCtrl", function($scope, $state, $stateParams,$rootScope,ProjectInfo) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.headerPrice.title = "项目详情";
    $scope.$parent.param.headerPrice.saveContent = "";
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
    $scope.$parent.mainSwitch.footerBoxFlag=false;
    /*轮播图*/
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
    $rootScope.loadingFlag = true;
    ProjectInfo.get({id:$stateParams.id},function (data) {
        if(data.result == '0x00001'){
            $rootScope.loadingFlag = false;
            $scope.projectInformation = data.responseData;
            console.log($scope.projectInformation);
        }
    })

    $scope.$parent.priceListBlackFn = function () {
        $state.go("pad-web.left_nav.project");
    }

});