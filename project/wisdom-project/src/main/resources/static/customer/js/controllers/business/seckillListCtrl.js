angular.module('controllers',[]).controller('seckillListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SeckillList',
        function ($scope,$rootScope,$stateParams,$state,SeckillList) {
            $rootScope.title = "秒杀专区";
            $scope.$on('$ionicView.enter', function(){
                $scope.PageParamVoDTO ={
                    pageNo:$scope.pageNo,
                    pageSize:$scope.pageSize,
                };
                SeckillList.save($scope.PageParamVoDTO,function (data) {
                    $scope.seckillList = data.responseData.responseData;
                    console.log(  $scope.seckillList )
                })

                $scope.seckillInfo = function (id) {
                    $state.go("seckillInfo",{id:id});
                }
            });
        }]);