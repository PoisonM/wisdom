angular.module('controllers',[]).controller('selBrandCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','SearchShopProductList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,SearchShopProductList,Global) {
            $rootScope.title = "选择品牌";
            SearchShopProductList.get({
                filterStr:""
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                     $scope.selBrand = data.responseData.detailLevel
                }
            })
            $scope.selBand = function(name,oneId){
                if($stateParams.type== 'add'){
                    $state.go('addProduct',{id:$stateParams.id, band:name,oneId:oneId})
                }else{
                    $state.go('modifyProduct',{id:$stateParams.id, band:name,oneId:oneId})
                }

            }

        }])