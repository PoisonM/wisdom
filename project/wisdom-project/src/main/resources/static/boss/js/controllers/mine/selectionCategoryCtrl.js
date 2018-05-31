/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('selectionCategoryCtrl',
    ['$scope','$rootScope','$stateParams','$state','OneLevelProject',
        function ($scope,$rootScope,$stateParams,$state,OneLevelProject) {
            console.log($rootScope.xixixix);
            $rootScope.title = "选择类别";
           /* $scope.param={
                projectId:$stateParams.projectId
            };*/
            /*调取选择分类的接口*/
            OneLevelProject.get(function (data) {
                $scope.selectionCategory=data.responseData;
            });
            $scope.selectType=function (typeId,name) {
                $state.go($stateParams.add || $stateParams.mod,{typeId:typeId,name:name,projectId:$stateParams.projectId})
            }

        }]);