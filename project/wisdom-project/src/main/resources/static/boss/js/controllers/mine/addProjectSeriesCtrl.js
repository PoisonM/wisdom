/**
 * Created by Administrator on 2018/5/22.
 */
angular.module('controllers',[]).controller('addProjectSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','TwoLevelProject',
        function ($scope,$rootScope,$stateParams,$state,TwoLevelProject) {

            $rootScope.title = "";
            console.log($stateParams.typeId);
            $scope.param={
                id:$stateParams.typeId/*一级项目id*/
            };
            TwoLevelProject.get({id:$scope.param.id},function (data) {
                $scope.seriesList=data.responseData;
               console.log( $scope.seriesList)
            });
            $scope.selectSeries=function (seriesId,seriesName) {
                $state.go($stateParams.add||$stateParams.mod,{seriesId:seriesId,seriesName:seriesName,projectId:$stateParams.projectId})
            }
            
        }]);