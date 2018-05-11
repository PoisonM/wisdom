angular.module('controllers',[]).controller('AddOutboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "新增出库";
            $scope.shopStock =[{
                detail:"啊啊",/*备注*/
                flowNo:"785489636598741258",/*订单号*/
                productDate:"",/*产品生产日期	*/
                setStockPrice:"12",/*进货单价*/
                shopProcId:"7878",/*产品id*/
                shopStoreId:"651742081",/*仓库id*/
                stockNumber:"12",/*库存数量	*/
                stockStyle:$stateParams.stockStyle,/*0、手动入库 1、扫码入库 2、手动出库 3、扫码出库	*/
                stockNumber:"1"/*0、采购入库 1、内部员工出库 2、顾客出库 3、赠送 4、报废 5、院用 6、退回供货商*/

            }];
            $scope.selectTheOutboundTypeGo = function(){
                $state.go("selectTheOutboundType")
            }
            $scope.selFamilyGo = function(){
                $state.go("addFamily")
            };
            /*添加更多*/
            $scope.productInventoryGo = function(stockStyle){
                $state.go('productInventory',{stockStyle:stockStyle})
            }
            /*确认出库*/
            $scope.successfulInventoryGo = function(){



                $state.go('successfulInventory')
            }

        }])
