/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyProjectListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserClientShopProjectList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetUserClientShopProjectList,Global) {

    $scope.confirmProject = function() {
        $state.go("beautyAppoint");
    }

    $scope.chooseProject = function(projectId)
    {
        $scope.param.shopProjectId = angular.copy(projectId);
        $rootScope.shopAppointInfo.shopProjectId = $scope.param.shopProjectId
    }

    $scope.param = {
        pageNo : 0,
        pageSize:10,
        shopProjectList : [],
        shopProjectId : $rootScope.shopAppointInfo.shopProjectId
    }

    $scope.doRefresh = function()
    {
        GetUserClientShopProjectList.get({pageNo:$scope.param.pageNo,pageSize:$scope.param.pageSize},function(data){
            if(data.result=Global.SUCCESS)
            {
                $scope.param.shopProjectList = data.responseData;
                console.log($scope.param.shopProjectList);
            }
            $scope.$broadcast('scroll.refreshComplete');
        })
        $scope.pageNo++;
    }

    $scope.doRefresh();

}])