angular.module('controllers',[]).controller('AddOutboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','AddStock','GetStockNumber','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,AddStock,GetStockNumber,Global) {

            $rootScope.title = "新增出库";

            $scope.param = {
                shopStock : [],
                outOperationName : '',
                detail:''
            };
            angular.forEach($rootScope.shopInfo.entryShopProductList,function (val,index) {
                var value = {
                    detail:"",
                    productDate:val.effectDate,
                    stockPrice:val.marketPrice,/*进货单价*/
                    shopProcId:val.id,/*产品id*/
                    shopStoreId:$stateParams.shopStoreId,/*仓库id*/
                    stockNumber: "",
                    productUrl : val.productUrl,
                    productName: val.productName,
                    productUnit: val.productUnit,
                    productSpec: val.productSpec,
                    stockStyle:$stateParams.stockStyle, /*0、手动入库 1、扫码入库 2、手动出库 3、扫码出库	*/
                    stockType:$rootScope.shopInfo.outShopStockType,
                    receiver:'',
                    stockOutNumber:''
                }
                GetStockNumber.get({shopStoreId:$rootScope.shopInfo.shopStoreId,shopProcId:val.id},function (data) {
                    value.stockNumber = data.responseData.stockNumber;
                    $scope.param.shopStock.push(value);
                })
            })

            $scope.selectTheOutboundTypeGo = function(){
                $state.go("selectTheOutboundType",{shopStoreId:$stateParams.shopStoreId,stockStyle:$stateParams.stockStyle})
            }

            /*添加更多*/
            $scope.productInventoryGo = function(stockStyle){
                $state.go('productInventory',{stockStyle:stockStyle})
            }
            
            $scope.deleteOutboundProduct = function (productId) {
                $scope.param.shopStock = [];
                angular.forEach($rootScope.shopInfo.entryShopProductList,function (val,index) {
                    if(productId!=val.id)
                    {
                        var value = {
                            detail:"",
                            productDate:val.effectDate,
                            stockPrice:val.marketPrice,/*进货单价*/
                            shopProcId:val.id,/*产品id*/
                            shopStoreId:$stateParams.shopStoreId,/*仓库id*/
                            stockNumber: "",
                            productUrl : val.productUrl,
                            productName: val.productName,
                            productUnit: val.productUnit,
                            productSpec: val.productSpec,
                            stockStyle:$stateParams.stockStyle, /*0、手动入库 1、扫码入库 2、手动出库 3、扫码出库	*/
                            stockType:$rootScope.shopInfo.outShopStockType,
                            receiver:'',
                            stockOutNumber:''
                        }
                        $scope.param.shopStock.push(value);
                    }
                })
            }

            /*确认出库*/
            $scope.successfulInventoryGo = function(){

                angular.forEach($scope.param.shopStock,function (val,index) {
                   val.receiver = $scope.param.outOperationName;
                   val.detail = $scope.param.detail;
                });
                var list=$scope.param.shopStock;
                for(var i=0;i<list.length;i++){
                    if(list[i].stockNumber ==""||list[i].stockOutNumber ==""||list[i].stockType ==""||list[i].receiver ==""){
                        alert("请检查信息");
                        return
                    }
                }
                AddStock.save($scope.param.shopStock,function(data){
                    if(data.result==Global.SUCCESS){
                        $rootScope.shopInfo.outShopStockType = '';
                        $state.go("successfulInventory",{id:data.responseData,type:'outbound'})
                    }
                })
            }
        }])
