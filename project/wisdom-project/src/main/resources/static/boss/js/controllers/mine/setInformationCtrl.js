/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('setInformationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossShopList','Global','BossSwitchShops','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetBossShopList,Global,BossSwitchShops,$ionicLoading) {

            $rootScope.title = "设置";
            $scope.param={
                flag:false
            }
               /*点击基础资料设置*/
            $scope.basicSettingGo=function () {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                })
                GetBossShopList.get({},function(data){
                    $ionicLoading.hide()
                    if(data.result==Global.SUCCESS&&data.responseData==null){
                         alert('请先添加店铺')
                    }else if(data.result==Global.SUCCESS&&data.responseData!=null){
                        GetBossShopList.get({
                        },function(data){
                            if(data.result==Global.SUCCESS&&data.responseData!=null) {
                                $scope.setInformation = data.responseData
                                $scope.param.flag = true;
                            }
                        })

                    }

                })

            };
            $scope.swichShop = function (sysShopId) {
                BossSwitchShops.get({
                    sysShopId:sysShopId
                },function (data) {
                    if(data.result==Global.SUCCESS) {
                        $state.go("basicSetting")
                        $scope.param.flag = false;
                    }
                })
            }
            $scope.dis = function () {
                $scope.param.flag = false;
            }

            /*点击系统设置*/
            $scope.systemSetupGo=function () {
                $state.go("systemSetup")
            };

        }]);