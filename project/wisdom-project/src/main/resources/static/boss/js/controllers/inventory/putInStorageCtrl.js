angular.module('controllers', []).controller('putInStorageCtrl',
    ['$scope', '$rootScope', '$stateParams', '$state', '$ionicLoading','GetShopProductLevelInfo',
        function ($scope, $rootScope, $stateParams, $state, $ionicLoading,GetShopProductLevelInfo) {
            $rootScope.title = "入库";
            $scope.param = {
                flag: false,
                type: 0, /*客装产品  易耗品*/
                selType: 0, /*扫码入库  手动入库*/
                ids: [],/*入库产品*/
                detailProductList:[],
                searchProductList:[],
                searchContent :""
            };

            GetShopProductLevelInfo.get({},function(data){
                $scope.param.detailProductList = data.responseData.detailProductList;
            })

            $scope.changeBtn = function (type) {
                $scope.param.type = type;
            };
            $scope.selNext = function () {
                $scope.param.flag = true;
            };
            $scope.all = function () {
                $scope.param.flag = false;
            };
            $scope.selThree = function () {

            };
            $scope.threeMess = function () {
                $scope.param.flag = false;
            };
            $scope.selType = function (type) {
                $scope.param.selType = type;
            }

            $scope.selProduct = function (domIndex) {
                if ($scope.param.ids.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach($scope.param.ids, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.ids.splice(key, 1);
                        }
                        key++;
                    })
                } else {
                    $scope.param.ids.push(domIndex);
                }
            };

            $scope.inventoryRecordsPicsGo = function(){
                $state.go("inventoryRecordsPics")
            };

            $scope.newLibraryGo = function(){
                $state.go("newLibrary",{stockStyle:$scope.param.selType})
            }

            $scope.search = function(){
                $scope.param.searchProductList = [];
                GetShopProductLevelInfo.get({},function(data){
                    $scope.param.detailProductList = data.responseData.detailProductList;
                    angular.forEach($scope.param.detailProductList,function (value,key) {
                        if(value.productTypeOneName.indexOf($scope.param.searchContent)!=-1||
                            value.productName.indexOf($scope.param.searchContent)!=-1||
                            (value.productCode+"").indexOf($scope.param.searchContent)!=-1)
                        {
                            $scope.param.searchProductList.push(value);
                        }
                    })
                    $scope.param.detailProductList = angular.copy($scope.param.searchProductList);
                })
            }

            $scope.clearSearch = function()
            {
                $scope.param.searchContent = "";
                GetShopProductLevelInfo.get({},function(data){
                    $scope.param.detailProductList = data.responseData.detailProductList;
                })
            }
}])