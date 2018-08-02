/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('cardListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopProjectGroups','Global','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetShopProjectGroups,Global,$ionicLoading) {

            $rootScope.title = "套卡列表";
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetShopProjectGroups.get({
                    pageSize:1000,
                    projectGroupName:""
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $ionicLoading.hide();
                        $scope.cardList = data.responseData
                    }else{
                        $scope.cardList=[]
                    }
                })
            })


        }]);