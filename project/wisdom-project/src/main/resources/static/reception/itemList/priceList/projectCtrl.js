/**
 * Created by Administrator on 2018/3/29.
 */
PADWeb.controller("projectCtrl", function($scope, $state, $stateParams) {
    $scope.param = {
        selection:"0"
    }
    $scope.selection  = function (index) {
        $scope.param.selection = index
    }
})