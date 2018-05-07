/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('canceledCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopAppointmentInfoByStatus',
        function ($scope,$rootScope,$stateParams,$state,GetShopAppointmentInfoByStatus) {

            $rootScope.title = "";

           /* GetShopAppointmentInfoByStatus.get({
                searchDate:"2018-04-27",
                sysShopId:'11',
                status:"4"
            },function(data){
                $scope.canceled = data.responseData

            })*/

            $scope.confirmedGo = function(){
                $state.go("confirmed")
            }

            $scope.cancelReservationGo = function(){
                $state.go("cancelReservation")
            }

        }]);