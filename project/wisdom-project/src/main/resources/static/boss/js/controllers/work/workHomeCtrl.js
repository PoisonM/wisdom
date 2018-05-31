/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',[]).controller('workHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossAchievement','Global','BossUtil',
        function ($scope,$rootScope,$stateParams,$state,GetBossAchievement,Global,BossUtil) {

            $rootScope.title = "今日工作";

            BossUtil.setUserType(Global.userType.BEAUTY_BOSS);

            GetBossAchievement.get({},function(data){
                console.log(data);
                BossUtil.checkResponseData(data,'workHome');
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.workHome = data.responseData;
                }
            })

}])