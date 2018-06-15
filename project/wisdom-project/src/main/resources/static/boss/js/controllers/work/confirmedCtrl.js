/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('confirmedCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetAppointmentInfoById','UpdateAppointmentInfoById','Global','$ionicPopup','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,GetAppointmentInfoById,UpdateAppointmentInfoById,Global,$ionicPopup,$ionicLoading) {
          /*  $rootScope.title = "已确认预约";*/
            $scope.date=$stateParams.date;
            $scope.param={
                flag:false
            }
            GetAppointmentInfoById.get({
                shopAppointServiceId:$stateParams.shopAppointServiceId
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.confirmed = data.responseData;
                    $rootScope.title = $scope.confirmed.sysClerkName;
                    $scope.param.flag = false;
                }else{
                    $scope.param.flag = true;
                }
            })
            /*日期插件*/

            $scope.cancel = function(){
                $ionicPopup.confirm({
                    template:"<div style='text-align:center;font-family: 微软雅黑;'>确认取消预约吗？</div>",
                    buttons: [
                        { text: "<div class='myPopup'>取消</div>",
                            onTap:function(e){
                            }
                        },
                        {text: '<div class="myPopup">确定</div>',
                            onTap:function(e){
                                UpdateAppointmentInfoById.get({
                                    shopAppointServiceId:$stateParams.shopAppointServiceId, /*$stateParams.shopAppointServiceId*/
                                    status:"4"
                                },function(data){
                                    if(data.result==Global.SUCCESS){
                                        $state.go('canceled',{date:$stateParams.date,sysClerkId:$stateParams.sysClerkId,sysShopId:$stateParams.sysShopId})
                                    }

                                })
                            }
                        }]
                });
            };


            $scope.pho=function(num){
                window.location.href = "tel:" + num;
            }


        }]);