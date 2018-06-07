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
                searchContent :"",
                oneLevelList:[],
                twoLevelList:[],
                multiSelectFlag:false,
                selectProductTypeOneId:'',
                selectProductList:'',
            };
            $rootScope.shopInfo.entryShopProductList = [];

            GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
                $scope.param.detailProductList = data.responseData.detailProductList;
                $scope.param.oneLevelList = data.responseData.oneLevelList;
                $scope.param.twoLevelList = data.responseData.twoLevelList;
            })

            $scope.changeBtn = function (type) {
                if($scope.param.type != type)
                {
                    $scope.param.selectProductTypeOneId = "";
                    $scope.param.multiSelectFlag=false;
                    $scope.param.type = type;
                    $scope.param.selectProductList = '';
                    console.log(1)
                }
                else
                {
                    $scope.param.multiSelectFlag = !$scope.param.multiSelectFlag;
                    console.log(2);
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
            }

            $scope.selProduct = function (domIndex) {
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
                angular.forEach($scope.param.ids,function (val,index) {
                    angular.forEach($scope.param.detailProductList,function (val1,index1) {
                        if(val==index1)
                        {
                            $scope.param.selectProductList = $scope.param.selectProductList+','+val1.productTypeTwoName;
                            $rootScope.shopInfo.entryShopProductList.push(val1);
                        }
                    })
                })
            };

            $scope.inventoryRecordsPicsGo = function(){
                $state.go("inventoryRecordsPics")
            };

            $scope.newLibraryGo = function(){
                $state.go("newLibrary",{stockStyle:$scope.param.selType})
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
                    $scope.param.multiSelectFlag=false;
                })
            }
}])