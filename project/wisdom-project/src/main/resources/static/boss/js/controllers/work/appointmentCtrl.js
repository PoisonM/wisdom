/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('appointmentCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopAppointmentNumberInfo',
        function ($scope,$rootScope,$stateParams,$state,GetShopAppointmentNumberInfo) {

            $rootScope.title = "预约";
            GetShopAppointmentNumberInfo.get({
                searchDate:"2018-04-27"
            },function(data){
                $scope.appointment = data.responseData;
               /* if(data.result == Global.SUCCESS){

                 }*/
            })

            $scope.healthClubGo = function(sysShopId){
                $state.go("beautySalon",{sysShopId:sysShopId})
            }



        }]);