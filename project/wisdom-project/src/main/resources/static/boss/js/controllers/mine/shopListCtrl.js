/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('shopListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossAllShopList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetBossAllShopList,Global) {

            $rootScope.title = "分店列表";
            GetBossAllShopList.get({},function (data) {
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.shopList = data.responseData
                }
            })

        }]);