angular.module('controllers',[]).controller('archivesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Detail',"Global",
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Detail,Global) {
            $rootScope.title = "档案";

            Detail.get({
               id:$stateParams.id
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.archives = data.responseData

                }
            })

        }])
