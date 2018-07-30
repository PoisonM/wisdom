angular.module('controllers',[]).controller('orderListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','GetBorderSpecialProductOrderList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,GetBorderSpecialProductOrderList) {
            console.log("orderList")
            // $scope.option = {
            //     inportContent:"goodsList",
            //     method:oneLevelProject,
            //     dataList:""
            // }
            GetBorderSpecialProductOrderList.get({status:"all"},function (data) {
                if(data.result == Global.SUCCESS){
                    $scope.orderList = data.responseData;
                }else{
                    alert("获取信息失败")
                }
                console.log(data)
            })
        }]);