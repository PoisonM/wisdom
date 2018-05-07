/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyUserCenterCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserClientInfo','Global','GetCurrentLoginUserInfo',
        function ($scope,$rootScope,$stateParams,$state,GetUserClientInfo,Global,GetCurrentLoginUserInfo) {

    $scope.param = {
        currentShopInfo : {}
    }

    $scope.chooseProject = function() {
        $state.go("beautyAppoint");
    }

    GetUserClientInfo.get(function (data) {
        if(data.result==Global.SUCCESS) {
            $scope.param.currentShopInfo = data.responseData.currentShop;
        }
    })

    GetCurrentLoginUserInfo.get(function (data) {
        if(data.result==Global.SUCCESS){
            $rootScope.shopAppointInfo.shopUserInfo = data.responseData;
        }
    })

}])