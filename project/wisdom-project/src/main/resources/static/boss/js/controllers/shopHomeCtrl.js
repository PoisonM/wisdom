angular.module('controllers',[]).controller('shopHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "美享99触屏版";
            $scope.param = {
                bannerList:{},
                productList:{},//特殊商品
                product2List:[[]],//普通商品
                promoteProduct:true,
                promoteProductId:"88888888888",
                rookieProduct:true,
                rookieProductId:"201712101718100007",
                redPackerFlagOne:false,
                redPackerFlagTwo:false,
                bonusValue:"",
                timeContent:""
            }
            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });

            });
}])
