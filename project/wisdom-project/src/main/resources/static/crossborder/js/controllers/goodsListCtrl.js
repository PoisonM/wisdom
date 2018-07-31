angular.module('controllers',[]).controller('goodsListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductList'
        ,'GetBorderSpecialProductBrandList','AddBorderSpecialProduct2ShoppingCart',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductList
            ,GetBorderSpecialProductBrandList,AddBorderSpecialProduct2ShoppingCart) {
            console.log("goodsList")
            $scope.authentication_flag = false
            //获取品牌列表
            GetBorderSpecialProductBrandList.save({},function (data) {
                if(data.result == Global.SUCCESS){
                    $scope.brandLiat = data.responseData
                }

            })
            //获取商品列表
            $scope.getGoodsList = function (productName,brandName) {
                GetBorderSpecialProductList.save({
                    pageNo:$scope.paginationConf.currentPage,//当前页
                    pageSize:$scope.paginationConf.itemsPerPage,
                    requestData:{
                        productName:productName,
                        brand:brandName,
                        type:'special'
                    }
                },function (data) {
                    if(data.result == Global.SUCCESS){
                        $scope.paginationConf.totalItems = data.responseData.totalCount//总条数
                        // $scope.paginationConf.currentPage = data.responseData.pageNo//当前

                        $scope.option.dataList = data.responseData.responseData
                    }else {
                        alert(data.responseData)
                    }
                })
            }
            $rootScope.option = {
                inportContent:"",
                method:GetBorderSpecialProductList,
                fn:$scope.getGoodsList,
                dataList:""
            }

            $scope.checkBrand = function (brandName) {
                $scope.itemFlag = brandName || ""
                $scope.getGoodsList("",brandName)
            }

            $scope.goDetail = function (productId) {
                $state.go("details",{id:productId})
            };
            $scope.goShoppingCart = function () {
                $state.go("shoppingCart")
            };
            //分页
            $scope.paginationConf = {
                currentPage: 1,//当前页
                totalItems:0,//allItem数
                itemsPerPage: 12,//一页多少个
                pagesLength: 10,//页码长度
                perPageOptions: [10, 20, 30, 40, 50],
                onChange: function () {
                    $scope.getGoodsList();
                }
            };


            //加入购物车
            $scope.addShopCart = function (productId) {
                $scope.authentication_flag = true
                AddBorderSpecialProduct2ShoppingCart.get({
                    productId:productId,
                    productNum: "1",
                    productSpec:""
                },function (data) {
                    $scope.authentication_flag = false
                    if(data.result==Global.FAILURE){
                        alert("加入购物车失败");
                    }else{
                        alert("加入购物车成功");
                    }
                })
            }

        }]);