/**
 * Created by Administrator on 2018/5/31.
 */
angular.module('controllers',[]).controller('employeeIndexCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetClerkWorkDetail','Global','BossUtil',
        function ($scope,$rootScope,$stateParams,$state,GetClerkWorkDetail,Global,BossUtil) {

            $rootScope.title = "";

            BossUtil.setUserType(Global.userType.BEAUTY_CLERK);

            GetClerkWorkDetail.get({startTime:BossUtil.getNowFormatDate(),endTime:BossUtil.getAddDate(BossUtil.getNowFormatDate(),1)},function(data){
                BossUtil.checkResponseData(data,"employeeIndex");
                if(data.result==Global.SUCCESS&&data.responseData!=null)
                {
                    $scope.workHome = data.responseData;
                }
            });
           $scope.employeeComprehensive = function () {
               $state.go("employeeComprehensive")
           }

}]);