angular.module('controllers',[]).controller('libraryTubeSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','FindStoreList','GetStoreManager','SetStorekeeper',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,FindStoreList,GetStoreManager,SetStorekeeper) {
            $rootScope.title = "库管设置";
            $scope.warehousePeopleGo = function () {
                $state.go('warehousePeople')
            };
            $scope.param = {
               id:$stateParams.id
            }
            console.log( $scope.param.id)
            FindStoreList.get({},function(data){
                if(data.result =="0x00001")
                $scope.libraryTubeSetting =data.responseData
            })
            if($stateParams.ids!=""){
                $scope.peopleKuTube = $stateParams.names
            }
            $scope.kuTube = function (id) {
                if($scope.param.id !=id){
                    $scope.param.id = id;
                }else{
                    $scope.param.id = ''
                    return
                }

                GetStoreManager.get({id:id
                },function(data){
                    if(data.result =="0x00001")
                        $scope.peopleKuTube = data.responseData
                })
            }
            
            $scope.save = function () {
                var setStorekeeperRequestDTO = {
                    shopStoreId:$scope.param.id,
                    storeManagerIds:$stateParams.ids.split(','),
                    storeManagerNames:$scope.peopleKuTube.split(',')
                }
                SetStorekeeper.save(setStorekeeperRequestDTO,function (data) {
                    
                })
            }

        }])
