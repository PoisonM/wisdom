angular.module('controllers',[]).controller('warningFileCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetEarlyWarningList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetEarlyWarningList,Global) {
            $rootScope.title = "预警档案";
            $scope.queryType = "one";



            $scope.chooseTab = function(type){
                $scope.queryType = type;
                $scope.getInfo()
            }
            $scope.getInfo = function(){
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetEarlyWarningList.get({
                    pageNo:1,
                    pageSize:100,
                    queryType:$scope.queryType
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $ionicLoading.hide()
                        $scope.warningFile = data.responseData
                    }
                })
            };
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                $scope.getInfo()
            })



            $scope.archivesGo = function(id){
                $state.go("archives",{id:id})
            }

        }])
