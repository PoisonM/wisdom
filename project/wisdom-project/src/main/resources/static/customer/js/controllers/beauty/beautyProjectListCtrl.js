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
        if($rootScope.shopAppointInfo.shopProjectIds.indexOf(projectId)!=-1)
        {
            var key = 0;
            angular.forEach($rootScope.shopAppointInfo.shopProjectIds,function (val,index) {
                if(val==projectId)
                {
                    $rootScope.shopAppointInfo.shopProjectIds.splice(key,1);
                }
                key++;
            })
        }
        else
        {
            $rootScope.shopAppointInfo.shopProjectIds.push(projectId);
        }
        console.log($rootScope.shopAppointInfo.shopProjectIds);
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