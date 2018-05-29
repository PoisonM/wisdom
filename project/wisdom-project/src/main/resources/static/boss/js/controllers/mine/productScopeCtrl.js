
angular.module('controllers',[]).controller('productScopeCtrl',
    ['$scope','$rootScope','$stateParams','$state','Global','SearchShopProductList',
        function ($scope,$rootScope,$stateParams,$state,Global,SearchShopProductList) {
            $scope.param = {
                productList:[]
            }
            SearchShopProductList.get({
                filterStr:""
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.productScope =data.responseData
                }

            });
            for(var  i=0;i<$rootScope.settingAddsome.editedRecharge.productList.length;i++){
                $scope.param.productList[i]=$rootScope.settingAddsome.editedRecharge.productList[i].shopGoodsTypeId
            }


            $scope.selProductScope = function (domIndex) {
                console.log(domIndex)
                if ($scope.param.productList.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach($scope.param.productList, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.productList.splice(key, 1);
                        }
                        key++;
                    })
                } else {
                    $scope.param.productList.push(domIndex);
                }
                console.log($scope.param.productList)
            }
            $scope.save = function () {
                for(var  i=0;i<$scope.param.productList.length;i++){
                    $rootScope.settingAddsome.editedRecharge.productList[i]={shopGoodsTypeId:$scope.param.productList[i],goodsType:"4"}
                }
                $state.go($stateParams.url,{id:$stateParams.id})
            }

        }])
