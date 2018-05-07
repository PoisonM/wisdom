/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('beautySalonCtrl',
    ['$scope','$rootScope','$stateParams','$state','ShopDayAppointmentInfoByDate',
        function ($scope,$rootScope,$stateParams,$state,ShopDayAppointmentInfoByDate) {
/*$stateParams.sysShopId*/
            console.log($stateParams);
            $rootScope.title = "唯美度养生会所";
            ShopDayAppointmentInfoByDate.get({
                startDate:"2018-00-00%2000:00:00",
                endDate:'endDate=2019-00-00%2000:00:00',
                sysShopId:'3'
            },function(data){
                $scope.beautySalon = data.responseData;
                delete $scope.beautySalon.startTime;
                delete $scope.beautySalon.endTime;

            })
            $scope.canceledGo = function(){
                $state.go("canceled",{sysShopId:$stateParams.sysShopId})
            }

        }])