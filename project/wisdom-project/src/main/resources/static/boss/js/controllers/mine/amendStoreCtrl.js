/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('amendStoreCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopInfo','Global','UpdateShopInfo',
        function ($scope,$rootScope,$stateParams,$state,GetShopInfo,Global,UpdateShopInfo) {

            $rootScope.title = "修改门店";
            GetShopInfo.get({
                sysShopId:$stateParams.sysShopId
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.amendStore = data.responseData
                }

            })
           /* $scope.delPic = function(index){
                $rootScope.settingAddsome.editedRecharge.imageUrls.splice(index,1)
            }*/
            $scope.save = function () {
                UpdateShopInfo.save($scope.amendStore,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $state.go("basicSetting")
                    }
                })
            }

        }]);