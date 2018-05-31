/**
 * Created by Administrator on 2018/5/22.
 */
angular.module('controllers',[]).controller('addProjectSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','TwoLevelProject',
        function ($scope,$rootScope,$stateParams,$state,TwoLevelProject) {

            $rootScope.title = "";

            TwoLevelProject.get({id:$scope.settingAddsome.project.projectTypeOneId},function (data) {
                $scope.seriesList=data.responseData;
            });
            $scope.selectSeries=function (seriesId,seriesName) {
                $rootScope.settingAddsome.project.projectTypeTwoId=seriesId
                $rootScope.settingAddsome.project.projectTypeTwoName=seriesName
                $state.go($stateParams.url,{projectId:$stateParams.projectId})

            }
            
        }]);