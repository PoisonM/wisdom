/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('beautySettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossShopInfo','Global','UpdateShopInfo',
        function ($scope,$rootScope,$stateParams,$state,GetBossShopInfo,Global,UpdateShopInfo) {

            $rootScope.title = "美容院设置";
            $scope.param = {
                flag:false
            }
            $scope.showPic = function () {
                $scope.param.flag =!$scope.param.flag
            }
            GetBossShopInfo.get({},function (data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                     $scope.beautySetting = data.responseData
                }
            })
            $scope.save = function () {
                UpdateShopInfo.save($scope.beautySetting,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $state.go("basicSetting")
                    }
                })
            }


        }]);