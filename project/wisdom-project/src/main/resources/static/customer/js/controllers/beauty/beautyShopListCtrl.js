/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyShopListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserClientInfo','Global','ChangeUserShop',
        function ($scope,$rootScope,$stateParams,$state,GetUserClientInfo,Global,ChangeUserShop) {

            $scope.param = {
                currentShopInfo : {},
                otherShopInfo : []
            }

            GetUserClientInfo.get(function (data) {
                if(data.result==Global.SUCCESS)
                {
                    $scope.param.currentShopInfo = data.responseData.currentShop;
                    $scope.param.otherShopInfo = data.responseData.otherShop;
                }
            })
            
            $scope.chooseShop = function (shopId) {
                ChangeUserShop.get({sysShopId:shopId},function (data) {
                    if(data.result=Global.SUCCESS)
                    {
                        $state.go("beautyUserCenter");
                    }
                })
            }

}])