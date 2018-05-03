/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyUserAppointDetailCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetAppointmentInfoById',
        function ($scope,$rootScope,$stateParams,$state,GetAppointmentInfoById) {

        $scope.param = {
            appointInfo : {}
        }

        GetAppointmentInfoById.get({shopAppointServiceId:$stateParams.appointId},function (data) {
            $scope.param.appointInfo = data.responseData;
            console.log($scope.param.appointInfo);
        })
}])