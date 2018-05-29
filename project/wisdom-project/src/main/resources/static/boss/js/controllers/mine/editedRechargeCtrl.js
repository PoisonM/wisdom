/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('editedRechargeCtrl',
    ['$scope','$rootScope','$stateParams','$state','RechargeCardDetail','Global','$http','GetGoodsUseScope','UpdateRechargeCardInfo',
        function ($scope,$rootScope,$stateParams,$state,RechargeCardDetail,Global,$http,GetGoodsUseScope,UpdateRechargeCardInfo) {

            $rootScope.title = "编辑充值卡";
            $scope.param={
                appearArr:[false,false,false],
                status:true,
                id:$stateParams.id
            };
            RechargeCardDetail.get({
                id:$stateParams.id
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $rootScope.settingAddsome.editedRecharge = data.responseData
                    if($rootScope.settingAddsome.editedRecharge.status == '0'){
                        $scope.param.status = true
                    }else{
                        $scope.param.status = false
                    }
                    GetGoodsUseScope.get({
                        shopRechargeCardId:$stateParams.id
                    },function (data) {
                        $rootScope.settingAddsome.editedRecharge.timesList =data.responseData.timesList
                        $rootScope.settingAddsome.editedRecharge.periodList =data.responseData.periodList
                        $rootScope.settingAddsome.editedRecharge.productList =data.responseData.productList
                        if($rootScope.settingAddsome.editedRecharge.productList.length>0){
                            $scope.param.appearArr[2]=true
                        }
                        if($rootScope.settingAddsome.editedRecharge.periodList.length>0){
                            $scope.param.appearArr[1]=true
                        }
                        if($rootScope.settingAddsome.editedRecharge.timesList.length>0){
                            $scope.param.appearArr[0]=true
                        }

                    })

                }
            })
            $scope.delPic = function(index){
                $scope.editedRecharge.imageUrls.splice(index,1)
            }
            $scope. appear=function (index) {
                $scope.param.appearArr[index ] =!$scope.param.appearArr[index ]
            }

            $scope.discount = function(style){
                $rootScope.settingAddsome.editedRecharge[style] = $rootScope.settingAddsome.editedRecharge[style].replace(/[^\d]/g,'')

            }
            $scope.discounts = function (style) {
                if($rootScope.settingAddsome.editedRecharge[style]>10||$rootScope.settingAddsome.editedRecharge[style]<0.1){
                    $rootScope.settingAddsome.editedRecharge[style] =0.1
                }
            }
            $scope.save = function () {
                UpdateRechargeCardInfo.save($rootScope.settingAddsome.editedRecharge,function (data) {
                    if(data.result==Global.SUCCESS){
                        $state.go("basicSetting")
                    }
                    
                })
            }




        }])