/**
 * Created by Administrator on 2018/5/31.
 */
angular.module('controllers',[]).controller('employeeWorkHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossAchievement','Global',
        function ($scope,$rootScope,$stateParams,$state,GetBossAchievement,Global) {

            $rootScope.title = "";
            GetBossAchievement.get({},function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.workHome = data.responseData;
                }
            });
           $scope.employeeComprehensive=function () {
               $state.go("employeeComprehensive")
           }
        }]);