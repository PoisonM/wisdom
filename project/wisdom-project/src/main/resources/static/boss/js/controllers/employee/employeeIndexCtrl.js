/**
 * Created by Administrator on 2018/5/31.
 */
angular.module('controllers',[]).controller('employeeIndexCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetClerkWorkDetail','Global','BossUtil','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetClerkWorkDetail,Global,BossUtil,$ionicLoading) {

            $rootScope.title = "";

            BossUtil.setUserType(Global.userType.BEAUTY_CLERK);


            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetClerkWorkDetail.get({startTime:BossUtil.getNowFormatDate(),endTime:BossUtil.getAddDate(BossUtil.getNowFormatDate(),1)},function(data){
                    BossUtil.checkResponseData(data,"employeeIndex");
                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $ionicLoading.hide();
                        $scope.workHome = data.responseData;
                    }
                });
            });
           $scope.employeeComprehensive = function () {
               $state.go("employeeComprehensive")
           }

}]);