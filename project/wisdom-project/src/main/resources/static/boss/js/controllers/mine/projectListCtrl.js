/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('projectListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SearchShopProjectList','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,SearchShopProjectList,$ionicLoading) {

            $rootScope.title = "项目列表";

            $scope.modifyProjectGo=function (projectId) {
                $state.go("modifyProject",{projectId:projectId})
            };
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                SearchShopProjectList.get({filterStr:"",useStyle:"2"},function (data) {
                    $ionicLoading.hide();
                    $scope.projectList=data.responseData.detailProject;
                    /*循环遍历项目里面所以商品*/
                    for(var  i=0;i< $scope.projectList.length;i++){
                        if($scope.projectList[i].cardType=="0"){
                            $scope.projectList[i].cardType='时效卡';
                        }else if($scope.projectList[i].cardType=="1"){
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
            })


        }]);