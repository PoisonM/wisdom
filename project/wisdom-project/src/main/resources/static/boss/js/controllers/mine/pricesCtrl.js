/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('pricesCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $rootScope.title = "价目表";
            $scope.chooseTab = function(type){
                $scope.queryType = type;
            }
        }]);