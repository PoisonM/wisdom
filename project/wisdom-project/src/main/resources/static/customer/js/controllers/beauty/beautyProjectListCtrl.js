/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyProjectListCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

        $scope.chooseProject = function() {
            $state.go("beautyAppoint");
        }

}])