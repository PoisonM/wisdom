/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('cancelReservationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopAppointmentInfoByStatus',
        function ($scope,$rootScope,$stateParams,$state,GetShopAppointmentInfoByStatus) {

            $rootScope.title = "取消预约";

            $scope.cancelDetailsGo=function(){
                $state.go("cancelDetails")
            }
            GetShopAppointmentInfoByStatus.get({
                searchDate:"2018-04-27",
                sysClerkId:'cc03a01d060e4bb09e051788e8d9801b',
                sysShopId:"11",
                status:"4"
            },function(data){

            })


        }]);