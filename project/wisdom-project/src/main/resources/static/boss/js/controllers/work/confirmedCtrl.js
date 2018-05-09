/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('confirmedCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetAppointmentInfoById','UpdateAppointmentInfoById','Global',
        function ($scope,$rootScope,$stateParams,$state,GetAppointmentInfoById,UpdateAppointmentInfoById,Global) {
          /*  $rootScope.title = "已确认预约";*/
            $scope.date=$stateParams.date;
            /*日期插件*/

            $scope.cancel = function(){
                UpdateAppointmentInfoById.get({
                    shopAppointServiceId:$stateParams.shopAppointServiceId, /*$stateParams.shopAppointServiceId*/
                    status:"4"
                },function(data){
                    $state.go('canceled',{date:$stateParams.date,sysClerkId:$stateParams.sysClerkId,sysShopId:$stateParams.sysShopId})
                })
               
            };
            GetAppointmentInfoById.get({
                shopAppointServiceId:$stateParams.shopAppointServiceId
            },function(data){
                $scope.confirmed = data.responseData;
                $rootScope.title = $scope.confirmed.sysClerkName

            })
            $scope.pho=function(num){
                window.location.href = "tel:" + num;
            }


        }]);