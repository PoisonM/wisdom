angular.module('controllers',[]).controller('AddOutboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','AddStock',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,AddStock) {

            $rootScope.title = "新增出库";

            $scope.param = {
                shopStock : [],
                outOperationName : ''
            }

            $rootScope.shopInfo.outShopStockType = '';

            angular.forEach($rootScope.shopInfo.entryShopProductList,function (val,index) {
                var value = {
                    detail:"",
                    productDate:val.effectDate,
                    stockPrice:val.marketPrice,/*进货单价*/
                    shopProcId:val.id,/*产品id*/
                    shopStoreId:$rootScope.shopInfo.shopStoreId,/*仓库id*/
                    stockNumber: "",
                    productUrl : val.productUrl,
                    productName: val.productName,
                    productUnit: val.productUnit,
                    productSpec: val.productSpec,
                    stockStyle:$stateParams.stockStyle, /*0、手动入库 1、扫码入库 2、手动出库 3、扫码出库	*/
                    stockType:$rootScope.shopInfo.outShopStockType,

                }
                $scope.param.shopStock.push(value);
            })

            $scope.selectTheOutboundTypeGo = function(){
                $state.go("selectTheOutboundType")
            }

            $scope.selFamilyGo = function(){
                $state.go("addFamily");
            };

            /*添加更多*/
            $scope.productInventoryGo = function(stockStyle){
                $state.go('productInventory',{stockStyle:stockStyle})
            }

            /*确认出库*/
            $scope.successfulInventoryGo = function(){
                AddStock.save($scope.param.shopStock,function(data){
                    if(data.result==Global.SUCCESS){
                        $state.go("successfulInventory",{entryId:data.responseData})
                    }
                })
            }
        }])
