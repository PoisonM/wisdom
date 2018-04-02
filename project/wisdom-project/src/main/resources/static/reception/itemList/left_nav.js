PADWeb.controller("left_navCtrl", function($scope, $state, $stateParams) {
    console.log("left_navCtrl")
    $scope.param = {
        selectSty:"1"
    }


    /*-------------------------------方法-------------------------------------------*/
    $scope.selectSty = function (index) {
        $scope.param.selectSty = index
    }
})