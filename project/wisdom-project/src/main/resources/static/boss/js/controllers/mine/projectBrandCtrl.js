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
            $scope.addBrandOneGo = function(type,id,projectTypeName,status){
                $state.go("projectSetting",{type:type,id:id,projectTypeName:projectTypeName,status:status});
            };
            $scope.checkSeries=function (id) {
                $state.go("projectSeries",{id:id})
            };
            SearchShopProjectList.get({filterStr:'',useStyle:"2"},function (data) {
                console.log(data.responseData.detailLevel);
                $scope.projectBrand = data.responseData.detailLevel
            })
        }]);