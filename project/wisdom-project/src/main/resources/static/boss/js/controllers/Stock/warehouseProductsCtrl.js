/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('warehouseProductsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopProductLevelInfo','Global','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetShopProductLevelInfo,Global,$ionicLoading) {

            $rootScope.title = "仓库产品";
            $scope.param = {
                flag: false,
                productType:"",
                levelOneId:"",
                levelTwoId:"",
                twoLevelList:[]
            };

            $scope.modifyProductGo = function(id){
                $state.go("modifyProduct",{id:id})
            }

            $scope.changeBtn = function (type) {/*点击文字*/
                $scope.param.levelOneId = "";
                $scope.param.levelTwoId = '';
                $scope.param.productType = type;
                $scope.getInfo()

            };
            $scope.selNext = function (type) {/*点击小三角*/
                $scope.param.levelOneId = "";
                $scope.param.levelTwoId='';
                $scope.param.productType = type;
                $scope.param.flag = true;
                $scope.getInfo()
                $scope.param.twoLevelList = $scope.warehouseProducts.twoLevelList
            };
            $scope.selTwo = function (productTypeOneId) {/*根据一级查询二级*/
                $scope.param.levelOneId = productTypeOneId;
                $scope.param.twoLevelList = []
                for(var i=0;i<$scope.warehouseProducts.twoLevelList.length;i++){
                    if( $scope.warehouseProducts.twoLevelList[i].productTypeOneId == productTypeOneId){
                        $scope.param.twoLevelList.push($scope.warehouseProducts.twoLevelList[i])
                    }

                }
                console.log( $scope.warehouseProducts.twoLevelList)
            }
            $scope.checkThree = function(productTypeTwoId){
                $scope.param.levelTwoId =productTypeTwoId;
                $scope.param.flag = false;
                $scope.getInfo()
            }

            $scope.all = function () {
                $scope.param.flag = false;
            };

            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetShopProductLevelInfo.get({
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $ionicLoading.hide();
                        $scope.warehouseProducts = data.responseData;
                    }

                })
            })

            $scope.getInfo = function(){
                GetShopProductLevelInfo.get({
                    productType:$scope.param.productType,
                    levelOneId:$scope.param.levelOneId,
                    levelTwoId:$scope.param.levelTwoId,
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.warehouseProducts = data.responseData
                        if($scope.param.levelOneId == ''){
                        }

                    }

                })

            }

        }]);
