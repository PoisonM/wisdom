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
    }
})