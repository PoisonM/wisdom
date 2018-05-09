/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('workHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossAchievement','Global',
        function ($scope,$rootScope,$stateParams,$state,GetBossAchievement,Global) {

    $rootScope.title = "今日工作";
            GetBossAchievement.get({

            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.workHome = data.responseData

                }

            })


}])