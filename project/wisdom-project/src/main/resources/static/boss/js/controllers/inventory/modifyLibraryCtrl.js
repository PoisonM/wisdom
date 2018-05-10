angular.module('controllers',[]).controller('modifyLibraryCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','UpdateStockNumber','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,UpdateStockNumber,Global) {
            $rootScope.title = "修改";
            $scope.shopStockNumberDTO = {
                id:"11",
                actualStockPrice:"1",
                actualStockNumber:"2"
            }
            $scope.entryDetailsGo = function(){

                UpdateStockNumber.get($scope.shopStockNumberDTO,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $state.go("entryDetails")

                    }
                })


            }

        }])
