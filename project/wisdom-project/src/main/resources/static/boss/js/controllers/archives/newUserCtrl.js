angular.module('controllers',[]).controller('newUserCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Detail','Global','UpdateArchiveInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Detail,Global,UpdateArchiveInfo) {
            $rootScope.title = "";
            $scope.param={
                userInformation :{
                    sex:"",
                    birthday:"",
                    constellation:"",
                    detail:""
                }
            };
            Detail.get({
                id:$stateParams.id
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.newUser = data.responseData
                }
            });
            /*更新保存*/
            $scope.preservation=function () {
                UpdateArchiveInfo.save($scope.param.userInformation,function (data) {
                    if(Global.SUCCESS=data.result){
                      $state.go("archives")
                    }
                })
            }


        }]);