/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('beautySalonCtrl',
    ['$scope','$rootScope','$stateParams','$state','getShopAppointmentInfo',
        function ($scope,$rootScope,$stateParams,$state,getShopAppointmentInfo) {

            console.log($stateParams);
            $rootScope.title = "唯美度养生会所";
            getShopAppointmentInfo.get({
                searchDate:"2018-04-27",
                sysShopId:$stateParams.sysShopId
            },function(data){
                $scope.beautySalon = data.responseData

            })
            $scope.canceledGo = function(){
                $state.go("canceled",{sysShopId:$stateParams.sysShopId})
            }

        }])