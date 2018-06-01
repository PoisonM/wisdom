/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('selectionCategoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','OneLevelProject',
        function ($scope,$rootScope,$stateParams,$state,OneLevelProject) {
            $rootScope.title = "选择类别";
           /* $scope.param={
                projectId:$stateParams.projectId
            };*/
           console.log($stateParams.projectId)
            /*调取选择分类的接口*/
            OneLevelProject.get(function (data) {
                $scope.selectionCategory=data.responseData;
            });
            $scope.selectType=function (projectTypeOneId,name) {
                $scope.settingAddsome.project.projectTypeOneName = name
                $scope.settingAddsome.project.projectTypeOneId = projectTypeOneId
                $state.go($stateParams.url,{projectId:$stateParams.projectId})
            }

        }]);