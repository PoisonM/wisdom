PADWeb.controller('loginCtrl', function($scope, $stateParams, ngDialog) {

/*------------------------------------------头部开关--------------------------------------------------*/

    $scope.$parent.mainSwitch.headerLoginFlag = true
    $scope.$parent.mainSwitch.headerReservationAllFlag = false
    $scope.$parent.mainSwitch.headerCashAllFlag = false
});