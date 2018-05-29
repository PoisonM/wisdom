angular.module('controllers',[]).controller('collectionCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserProjectGroupList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserProjectGroupList) {
            $rootScope.title = "套卡";
            $scope.param={
                flag:false,
                goodType:"6"
            };
            /*点击筛选*/
            $scope.sel = function(){
                $scope.param.flag = true
            };
            /*点击关闭*/
            $scope.disNone=function () {
                $scope.param.flag = false;
            };
            /*点击选择类型*/
            $scope.selType = function(type){
                $scope.param.goodType = type
            };
            /*点击重置*/
            $scope.reset = function() {
                $scope.param.goodType = '6'
            };
            
            GetUserProjectGroupList.get({sysUserId:$stateParams.sysUserId},function (data) {
                console.log(data);
                $scope.collectionCar=data.responseData;
            })
        }]);