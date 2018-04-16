/**
 * Created by Administrator on 2018/4/16.
 */
PADWeb.controller("setCardCtrl", function($scope, $state, $stateParams,$rootScope) {
    console.log("setCardCtrl");
    $rootScope.title="充值卡";
    $scope.goCardDetails=function () {
        $state.go("pad-web.cardDetails")
    }
})