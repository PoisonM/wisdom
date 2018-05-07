/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('canceledCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopClerkAppointmentInfo',
        function ($scope,$rootScope,$stateParams,$state,GetShopClerkAppointmentInfo) {

            $rootScope.title = "";

            GetShopClerkAppointmentInfo.get({
                searchDate:"2018-04-27",
                sysClerkId:'cc03a01d060e4bb09e051788e8d9801b',
                sysShopId:"11"
            },function(data){
                $scope.canceled = data.responseData

            })

            $scope.confirmedGo = function(){
                $state.go("confirmed")
            }

            $scope.cancelReservationGo = function(){
                $state.go("cancelReservation")
            }

        }]);