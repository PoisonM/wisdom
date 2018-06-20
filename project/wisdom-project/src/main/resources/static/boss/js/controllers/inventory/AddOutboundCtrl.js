angular.module('controllers',[]).controller('AddOutboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','AddStock','GetStockNumber','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,AddStock,GetStockNumber,Global) {

            $rootScope.title = "新增出库";
            $scope.sum = $stateParams.sum;
            $scope.storeManagerName ="";
            $scope.outOperationName = $stateParams.names.split(',')[0];
            $scope.outOperationVal = $stateParams.ids.split(',')[0];
            $scope.param = {
                shopStock : [],
                outOperationName : '',
                outOperationVal : '',
                detail:''
            };

            //选
            if($stateParams.ids!=""){
                $scope.param.outOperationName = $stateParams.names.split(',')[0];
                $scope.param.outOperationVal = $stateParams.ids.split(',')[0];
            }


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
                $state.go("selectTheOutboundType",{shopStoreId:$stateParams.shopStoreId,stockStyle:$stateParams.stockStyle,sum:$scope.sum,name:$stateParams.name})
            }

            /*添加更多*/
            $scope.productInventoryGo = function(stockStyle){
                $state.go('productInventory',{stockStyle:stockStyle})
            }
            
            $scope.deleteOutboundProduct = function (productId) {
                var shopStock = [];
                angular.forEach($scope.param.shopStock,function (val,index) {

                    if(productId!=val.shopProcId){
                        shopStock.push(val);
                    }
                })
                $scope.param.shopStock=shopStock;
                $scope.sum = $scope.sum-1;
            }

            $scope.selReceiver = function(){

                $state.go('receiver',{sum:$stateParams.sum,id:$stateParams.shopStoreId,stockStyle:$stateParams.stockStyle});
            }

            /*确认出库*/
            $scope.successfulInventoryGo = function(){
                if($scope.sum>0){
                    angular.forEach($scope.param.shopStock,function (val,index) {
                       val.receiver = $scope.param.outOperationVal;
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
                }else{
                    alert("请选择出库商品！");
                    $state.go('outbound',{name:$stateParams.name})
                }
            }
        }])
