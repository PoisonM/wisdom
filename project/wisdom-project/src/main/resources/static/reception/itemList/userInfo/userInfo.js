PADWeb.controller('userInfoCtrl', function($scope, $stateParams, ngDialog) {

    $scope.flagFn = function (bool) {
        $scope.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.mainSwitch.headerLoginFlag = !bool
    }
    $scope.flagFn(true)
});