/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('projectListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SearchShopProjectList',
        function ($scope,$rootScope,$stateParams,$state,SearchShopProjectList) {

            $rootScope.title = "项目列表";

            $scope.modifyProjectGo=function (projectId) {
                $state.go("modifyProject",{projectId:projectId})
            };

            SearchShopProjectList.get({filterStr:"",useStyle:"2"},function (data) {
                $scope.projectList=data.responseData.detailProject;
                /*循环遍历项目里面所以商品*/
                for(var  i=0;i< $scope.projectList.length;i++){
                    if($scope.projectList[i].cardType=="1"){
                        $scope.projectList[i].cardType='月卡';
                    }else if($scope.projectList[i].cardType=="2"){
                        $scope.projectList[i].cardType='季卡';
                    }else if($scope.projectList[i].cardType=="3"){
                        $scope.projectList[i].cardType='半年卡';
                    }else if($scope.projectList[i].cardType=="4"){
                        $scope.projectList[i].cardType='年卡';
                    }
                }
            });
        }]);