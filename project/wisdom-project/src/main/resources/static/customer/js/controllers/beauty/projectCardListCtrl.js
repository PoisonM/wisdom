/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('projectCardListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserRechargeCardList','GetUserCourseProjectList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetUserRechargeCardList,GetUserCourseProjectList,Global) {

            $scope.param = {
                projectCardList : []
            }

            GetUserCourseProjectList.get({sysUserId:$rootScope.shopAppointInfo.shopUserInfo.id,cardStyle:'1'},function (data) {
                if(data.result==Global.SUCCESS)
                {
                    $scope.param.projectCardList = data.responseData;
                    console.log($scope.param.projectCardList);
                }
            })

            $scope.chooseProjectCard = function(projectId){
                $state.go("projectCardDetail",{projectId:projectId})
            }

}])