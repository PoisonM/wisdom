/**
 * Created by Administrator on 2018/3/5.
 */
angular.module('controllers',[]).controller('myTeamCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserNextLevelStruct','Global','GetUserInfo',
        function ($scope,$rootScope,$stateParams,$state,GetUserNextLevelStruct,Global,GetUserInfo) {

            $rootScope.title = "我的团队";

            $scope.param = {
                userName : "",
                userNextLevelStruct :[]
            }

            GetUserInfo.get(function(data){
                if(data.responseData.userType.indexOf("B-1")>-1)
                {
                    $scope.param.userName = "9小主";
                }
                else if(data.responseData.userType.indexOf("A-1")>-1)
                {
                    $scope.param.userName = "大当家";
                }
                else
                {
                    $scope.param.userName = "普通会员";
                }
            })

            GetUserNextLevelStruct.get(function(data){

                if(data.result==Global.SUCCESS){
                    console.log(data.responseData);
                    $scope.param.userNextLevelStruct = data.responseData;
                }

            })

        }])