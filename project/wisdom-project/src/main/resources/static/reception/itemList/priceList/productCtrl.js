/**
 * Created by Administrator on 2018/4/2.
 */
PADWeb.controller("productCtrl", function($scope, $state, $stateParams) {
    $scope.param = {
        selection:"0"
    }
    $scope.selection  = function (index) {
        $scope.param.selection = index
    }
})