PADWeb.controller('guideCtrl', function($scope,$state, $stateParams,SuggestionDetail) {
    $scope.$parent.param.headerCash.backContent=""
    $scope.$parent.param.headerCash.title = "使用帮助"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false
})
