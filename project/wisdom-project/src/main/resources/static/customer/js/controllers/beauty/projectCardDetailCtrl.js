/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('projectCardDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetProjectCardConsume','Global',
        function ($scope,$rootScope,$stateParams,$state,GetProjectCardConsume,Global) {

    $scope.param = {
        pageSize:5000,
        projectCardConsumes : []
    }

    GetProjectCardConsume.get({consumeFlowNo:$stateParams.projectId},function (data) {
        if(data.result==Global.SUCCESS)
        {
            $scope.param.projectCardConsumes = data.responseData;
        }
        console.log(data);
    })

}])