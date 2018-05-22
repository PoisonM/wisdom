/**
 * Created by Administrator on 2018/5/21.
 */
angular.module('controllers',[]).controller('projectBrandCtrl',
    ['$scope','$rootScope','$stateParams','$state','SearchShopProjectList',
        function ($scope,$rootScope,$stateParams,$state,SearchShopProjectList) {

            $rootScope.title = "项目类别";
            $scope.addSeriesGo = function(){
                $state.go("projectSeries")
            };
            $scope.addBrandOneGo = function(type,id){
                $state.go("projectSetting",{type:type,id:id});
            };
            $scope.checkSeries=function (projectTypeOneId) {
                $state.go("projectSeries",{projectTypeOneId:projectTypeOneId})
            };
            SearchShopProjectList.get({filterStr:'',useStyle:"2"},function (data) {
                console.log(data.responseData.detailLevel);
                $scope.projectBrand = data.responseData.detailLevel
            })
        }]);