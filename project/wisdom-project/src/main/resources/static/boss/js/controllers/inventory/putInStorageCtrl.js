angular.module('controllers', []).controller('putInStorageCtrl',
    ['$scope', '$rootScope', '$stateParams', '$state', '$ionicLoading','GetShopProductLevelInfo',
        function ($scope, $rootScope, $stateParams, $state, $ionicLoading,GetShopProductLevelInfo) {
            $rootScope.title = "入库";
            $scope.sum = 0;
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
                multiSelectFlag:true,
                selectProductTypeOneId:'',
                selectProductList:'',
            };
            $rootScope.shopInfo.entryShopProductList = [];

            GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){

                $scope.param.detailProductList = data.responseData.detailProductList;
                $scope.param.oneLevelList = data.responseData.oneLevelList;
                $scope.param.selectProductTypeOneId = $scope.param.oneLevelList[0].productTypeOneId;
                $scope.param.twoLevelList = data.responseData.twoLevelList;
            })

            // 客装产品  院装产品 易耗品tab按钮
            $scope.changeBtn = function (type) {
                if($scope.param.type != type){
                    $scope.param.type = type;
                    if(!$scope.param.multiSelectFlag){
                       GetShopProductLevelInfo.get({productType:type},function(data){
                           $scope.param.oneLevelList = data.responseData.oneLevelList;
                           $scope.param.selectProductTypeOneId = $scope.param.oneLevelList[0].productTypeOneId;
                           $scope.param.twoLevelList = data.responseData.twoLevelList;
                       })
                    }else{
                        $scope.param.multiSelectFlag = !$scope.param.multiSelectFlag;

                        GetShopProductLevelInfo.get({productType:type},function(data){
                           $scope.param.oneLevelList = data.responseData.oneLevelList;
                           $scope.param.selectProductTypeOneId = $scope.param.oneLevelList[0].productTypeOneId;
                           $scope.param.twoLevelList = data.responseData.twoLevelList;
                        })
                    }

                }else{
                  if(!$scope.param.multiSelectFlag){
                      $scope.param.multiSelectFlag = !$scope.param.multiSelectFlag;
                   }else{
                       $scope.param.multiSelectFlag = !$scope.param.multiSelectFlag;
                       GetShopProductLevelInfo.get({productType:type},function(data){
                           $scope.param.oneLevelList = data.responseData.oneLevelList;
                           $scope.param.selectProductTypeOneId = $scope.param.oneLevelList[0].productTypeOneId;
                           $scope.param.twoLevelList = data.responseData.twoLevelList;
                       })
                   }
                }
            };
            
            $scope.chooseTwoLevelList = function (productTypeOneId) {
                $scope.param.selectProductTypeOneId = productTypeOneId;
                GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
                   $scope.param.twoLevelList = data.responseData.twoLevelList;
               })

            }
            
            $scope.selNext = function () {
                $scope.param.flag = true;
            };
            $scope.all = function () {
                $scope.param.multiSelectFlag = false;
            };

            $scope.threeMess = function () {
                $scope.param.flag = false;
            };
            $scope.selType = function (type) {
                $scope.param.selType = type;

                //扫码入库

            }

            $scope.selProduct = function (domIndex) {
                if($scope.sum<30){
                    $rootScope.shopInfo.entryShopProductList = [];
                    if($scope.param.type=='0')
                    {
                        $scope.param.selectProductList = '客装产品';
                    }
                    else if($scope.param.type=='1')
                    {
                        $scope.param.selectProductList = '院装产品';
                    }
                    else if($scope.param.type=='2')
                    {
                        $scope.param.selectProductList = '易耗品';
                    }
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
                    var key1 = 0;
                    angular.forEach($scope.param.ids,function (val,index) {
                        angular.forEach($scope.param.detailProductList,function (val1,index1) {
                            if(val==index1){
                                $scope.param.selectProductList = $scope.param.selectProductList+','+val1.productTypeTwoName;
                                $rootScope.shopInfo.entryShopProductList.push(val1);
                                key1++;
                            }

                        })
                    })
                    $scope.sum = key1;
                }else{
                    alert("一次性只能选择30种产品");
                }
            };

            $scope.inventoryRecordsPicsGo = function(){
                $state.go("inventoryRecordsPics",{shopStoreId:$rootScope.shopInfo.shopStoreId,name:$stateParams.name})
            };

            $scope.newLibraryGo = function(){
                console.log($rootScope.shopInfo.entryShopProductList);
                if($rootScope.shopInfo.entryShopProductList.length<=0){
                    alert("请先选择产品");
                    return;
                }
                $state.go("newLibrary",{stockStyle:$scope.param.selType,shopStoreId:$rootScope.shopInfo.shopStoreId,sum:$scope.sum,name:$stateParams.name})
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
            
            $scope.chooseProductList = function (productTypeTwoId) {
                GetShopProductLevelInfo.get({levelOneId:$scope.param.selectProductTypeOneId,
                    levelTwoId:productTypeTwoId,productType:$scope.param.type},function(data){
                    $scope.param.detailProductList = data.responseData.detailProductList;
                    $scope.param.oneLevelList = data.responseData.oneLevelList;
                    $scope.param.twoLevelList = data.responseData.twoLevelList;
                    $scope.param.multiSelectFlag=true;
                })
            }
}])