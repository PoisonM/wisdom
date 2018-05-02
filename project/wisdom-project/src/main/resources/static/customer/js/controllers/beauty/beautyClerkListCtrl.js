/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyClerkListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopClerkList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetShopClerkList,Global) {

            $scope.param = {
                pageNo : 0,
                pageSize:10,
                clerkId:$rootScope.shopAppointInfo.clerkId,
                clerkList : []
            }

            $scope.confirmClerk = function() {
                $state.go("beautyAppoint");
            }

            $scope.chooseClerk = function(clerkId)
            {
                $scope.param.clerkId = angular.copy(clerkId);
                $rootScope.shopAppointInfo.clerkId = $scope.param.clerkId;
            }

            $scope.doRefresh = function()
            {
                GetShopClerkList.get({pageNo:$scope.param.pageNo,pageSize:$scope.param.pageSize},function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        $scope.param.clerkList = data.responseData;
                        angular.forEach($scope.param.clerkList,function (value,index,array) {
                            var clerkProgressStyle = {
                                width: value.score +  "%",
                                background : "red",
                                height:"10px"
                            }
                            value.clerkProgressStyle = angular.copy(clerkProgressStyle);
                        })
                    }
                    $scope.$broadcast('scroll.refreshComplete');
                })
                $scope.pageNo++;
            }

            $scope.doRefresh();

}])