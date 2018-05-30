/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('modificationInformationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetCurrentLoginUserInfo','UpdateBossInfo',
        function ($scope,$rootScope,$stateParams,$state,GetCurrentLoginUserInfo,UpdateBossInfo) {

            $rootScope.title = "修改资料";
            $scope.userInfo={
                sex:"女"
            };
            /*点击女*/
            $scope.female=function () {
                $scope.userInfo.sex = '女'
            };
            /*点击男*/
            $scope.male=function () {
                $scope.userInfo.sex = '男'
            };
            /*查询我的信息*/
            GetCurrentLoginUserInfo.get(function (data) {
                $scope.userInfo=data.responseData;
            });
            /*修改我的信息*/
            $scope.Preservation=function () {
                /*查询到的所有内容全部保存到修改的接口*/
                UpdateBossInfo.save( $scope.userInfo,function (data) {
                    /*这里面点击保存的时候用户不修改也可以保存，所以不需要判断修改的数据是否为空：（依据美业邦）*/
                    if(data.result=="0x00001"){
                       $state.go("myself")
                    }
                })
            }
        }]);