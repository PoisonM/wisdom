angular.module('controllers',[]).controller('goodsListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {
            console.log("goodsList")


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