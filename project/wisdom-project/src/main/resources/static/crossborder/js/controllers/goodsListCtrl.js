angular.module('controllers',[]).controller('goodsListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','oneLevelProject',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,oneLevelProject) {
            console.log("goodsList")

            $scope.option = {
                inportContent:"goodsList",
                method:oneLevelProject,
                dataList:""
            }
            //分页
            $scope.paginationConf = {
                currentPage: 1,//当前页
                totalItems:0,//allItem数
                itemsPerPage: 10,//一页多少个
                pagesLength: 10,//页码长度
                perPageOptions: [10, 20, 30, 40, 50],
                onChange: function () {
                    // $scope.getOrderList();
                }
            };

        }]);