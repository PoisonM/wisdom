/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('cancelDetailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetAppointmentInfoById',
        function ($scope,$rootScope,$stateParams,$state,GetAppointmentInfoById) {

            $rootScope.title = "取消预约的详情";
            GetAppointmentInfoById.get({
                shopAppointServiceId:"id_7" /*$stateParams.shopAppointServiceId*/
            },function(data){
                $scope.cancelDetails = data.responseData;
            })
            $scope.pho=function(pho){
                window.location.href = "tel:" + pho;
            }

        }]);