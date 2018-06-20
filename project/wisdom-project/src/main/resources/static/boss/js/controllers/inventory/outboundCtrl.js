angular.module('controllers',[]).controller('outboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetShopProductLevelInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetShopProductLevelInfo) {
            $rootScope.title = "出库";
            $scope.sum = 0;
            $scope.param = {
                flag: false,
                type: 0, /*客装产品  易耗品*/
                selType: 2, /*扫码出库  手动出库*/
                indexs: [],/*出库产品*/
                ids:[],
                detailProductList:[],
                searchProductList:[],
                searchContent :"",
                oneLevelList:[],
                twoLevelList:[],
                multiSelectFlag:false,
                selectProductTypeOneId:'',
                selectProductList:'',
            };
            $rootScope.shopInfo.outShopProductList = [];

            GetShopProductLevelInfo.get({productType:$scope.param.type},function(data){
                $scope.param.detailProductList = data.responseData.detailProductList;
                $scope.param.oneLevelList = data.responseData.oneLevelList;
                $scope.param.selectProductTypeOneId = $scope.param.oneLevelList[0].productTypeOneId;
                $scope.param.twoLevelList = data.responseData.twoLevelList;
            })

            $scope.changeBtn = function (type) {
                 if($scope.param.type != type){
                    $scope.param.type = type;
                    if($scope.param.multiSelectFlag){
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
                  if($scope.param.multiSelectFlag){
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

           /* $scope.selNext = function () {
                $scope.param.flag = true;
            };*/
            $scope.all = function () {
                $scope.param.multiSelectFlag = false;
            };

         /*   $scope.threeMess = function () {
                $scope.param.flag = false;
            }
            $scope.selType = function (type) {
                $scope.param.selType = type;
            }*/

            $scope.selProduct = function (domIndex,id) {
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
                    if ($scope.param.indexs.indexOf(domIndex) != -1) {
                        var key = 0;
                        angular.forEach($scope.param.indexs, function (val, index) {
                            if (val == domIndex) {
                                $scope.param.indexs.splice(key, 1);
                                $scope.param.ids.splice(key, 1);
                            }
                            key++;
                        })
                    } else {
                        $scope.param.indexs.push(domIndex);
                        $scope.param.ids.push(id);
                    }
                    var key1 = 0;
                    angular.forEach($scope.param.indexs,function (val,index) {
                        angular.forEach($scope.param.detailProductList,function (val1,index1) {
                            if(val==index1)
                            {
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

           /*出库记录*/
            $scope.outboundRecordsGo = function(){
                $state.go("outboundRecords",{shopStoreId:$rootScope.shopInfo.shopStoreId,name:$stateParams.name})
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

            /*下一步*/
            $scope.AddOutboundGo = function(){
                if($rootScope.shopInfo.entryShopProductList.length<=0){
                    alert("请选择产品");
                    return
                }
                $state.go("AddOutbound",{shopStoreId:$rootScope.shopInfo.shopStoreId,stockStyle:$scope.param.selType,name:$stateParams.name,sum:$scope.sum})
            }

}])