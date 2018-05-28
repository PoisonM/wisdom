angular.module('controllers',[]).controller('inventoryDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetShopProductLevelInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetShopProductLevelInfo) {
            $rootScope.title = "仓库产品";

            $scope.param = {
                flag: false,
                type: 0, /*客装产品  易耗品*/
                selType: 0, /*扫码入库  手动入库*/
                ids: [],/*入库产品*/
                detailProductList:[],
                searchProductList:[],
                searchContent :"",
                oneLevelList:[],
                twoLevelList:[],
                multiSelectFlag:false,
                selectProductTypeOneId:'',
                selectProductList:'',
            };

            GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
                $scope.param.detailProductList = data.responseData.detailProductList;
                $scope.param.oneLevelList = data.responseData.oneLevelList;
                $scope.param.twoLevelList = data.responseData.twoLevelList;

                console.log($scope.param.detailProductList);
            })

            $scope.changeBtn = function (type) {
                if($scope.param.type != type)
                {
                    $scope.param.selectProductTypeOneId = "";
                    $scope.param.multiSelectFlag=false;
                    $scope.param.type = type;
                    $scope.param.selectProductList = '';
                }
                else
                {
                    $scope.param.multiSelectFlag = !$scope.param.multiSelectFlag;
                }
                GetShopProductLevelInfo.get({productType:type},function(data){
                    $scope.param.detailProductList = data.responseData.detailProductList;
                    $scope.param.oneLevelList = data.responseData.oneLevelList;
                    $scope.param.twoLevelList = data.responseData.twoLevelList;
                })
            };

            $scope.chooseTwoLevelList = function (productTypeOneId) {
                $scope.param.selectProductTypeOneId = productTypeOneId;
            }

            $scope.search = function(){
                $scope.param.searchProductList = [];
                GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
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
                GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
                    $scope.param.detailProductList = data.responseData.detailProductList;
                    $scope.param.oneLevelList = data.responseData.oneLevelList;
                    $scope.param.twoLevelList = data.responseData.twoLevelList;
                })
            }

            $scope.productInventoryDetailsGo = function(){
                $state.go('productInventoryDetails')
            }

        }])
