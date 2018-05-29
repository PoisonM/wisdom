/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('cardListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopProjectGroups','Global',
        function ($scope,$rootScope,$stateParams,$state,GetShopProjectGroups,Global) {

            $rootScope.title = "套卡列表";
            GetShopProjectGroups.get({
                pageSize:1000,
                projectGroupName:""
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                     $scope.cardList = data.responseData
                }
            })

        }]);