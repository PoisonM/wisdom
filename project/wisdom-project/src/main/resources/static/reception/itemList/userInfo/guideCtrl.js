PADWeb.controller('guideCtrl', function($scope,$state, $stateParams,SuggestionDetail) {
    $scope.$parent.param.headerCash.backContent="今日收银"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false
})
