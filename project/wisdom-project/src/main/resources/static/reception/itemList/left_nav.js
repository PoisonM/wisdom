PADWeb.controller("left_navCtrl", function($scope, $state, $stateParams) {
    console.log("left_navCtrl")
    $scope.mainLeftSwitch = {
        peopleListFlag:false,
        priceListFlag:true
    }



    $scope.param = {
        selectSty:"1",
        priceType:"xm"
    }


    /*-------------------------------方法-------------------------------------------*/
    //
    $scope.selectSty = function (index) {
        $scope.param.selectSty = index
    }
    //价目表切换
    $scope.selectPriceType = function (type) {
        $scope.param.priceType = type
        if(type == 'xm'){
            $state.go("pad-web.left_nav.project");
        }else if(type == 'cp'){
            $state.go("pad-web.left_nav.product");
        }else if(type == 'tk'){
            $state.go("pad-web.left_nav.rechargeableCard");
        }else if(type == 'czk'){
            $state.go("pad-web.left_nav.setCard");
        }
    }
})