/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('appointmentCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "预约";

        }]);