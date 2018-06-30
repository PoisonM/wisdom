/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('expenseCardRecordCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetProjectConsumes','Global',
        function ($scope,$rootScope,$stateParams,$state,GetProjectConsumes,Global) {

            $scope.param = {
                pageSize:5000,
                projectConsumes : [],
                expenseCardDetailData :'true'
            }

            GetProjectConsumes.save({consumeType:'1',goodsType:'6',
                pageSize:$scope.param.pageSize,sysUserId:$rootScope.shopAppointInfo.shopUserInfo.id},function (data) {
                if(data.result==Global.SUCCESS)
                {
                    if(data.responseData!=null)
                    {
                        $scope.param.projectConsumes = data.responseData;
                    }
                    else {
                        $scope.param.expenseCardDetailData = "false";
                    }
                }
            })

}])