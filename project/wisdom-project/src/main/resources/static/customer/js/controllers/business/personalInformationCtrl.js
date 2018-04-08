angular.module('controllers',[]).controller('personalInformationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserInfo','UserLoginOut','Global','BusinessUtil','$ionicLoading','$ionicPopup',
        function ($scope,$rootScope,$stateParams,$state,GetUserInfo,UserLoginOut,Global,BusinessUtil,$ionicLoading,$ionicPopup) {

            $scope.param={
                userInfo:{}
            }

            $scope.$on('$ionicView.enter', function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetUserInfo.get(function (data) {
                    $ionicLoading.hide();
                    BusinessUtil.checkResponseData(data,'myselfCenter');
                    $scope.param.userInfo = data.responseData;
                })
            });

            // 退出登录
            $scope.exit = function(){
                /*调取退出登录的接口，后端返回状态*/
                UserLoginOut.get(function(data){
                    if(data.result==Global.SUCCESS)
                    {
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">退出登录成功</span>',
                            okText:'确定'
                        });
                        $state.go("myselfCenter");
                    }else{
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.5rem">退出登录失败</span>',
                            okText:'确定'
                        });
                    }
                })
            }
}])