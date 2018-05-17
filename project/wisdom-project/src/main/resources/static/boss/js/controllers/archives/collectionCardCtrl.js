angular.module('controllers',[]).controller('collectionCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserProjectGroupList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserProjectGroupList) {
            $rootScope.title = "套卡";
            GetUserProjectGroupList.get({sysUserId:$stateParams.sysUserId},function (data) {
                console.log(data);
                $scope.collectionCar=data.responseData;
            })
        }]);