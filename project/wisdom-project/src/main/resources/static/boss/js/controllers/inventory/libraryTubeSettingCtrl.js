angular.module('controllers',[]).controller('libraryTubeSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','FindStoreList','GetStoreManager','SetStorekeeper',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,FindStoreList,GetStoreManager,SetStorekeeper) {
            $rootScope.title = "库管设置";
            $scope.show = true;
            $scope.storeManagerName="";
            $scope.warehousePeopleGo = function () {
                $state.go('warehousePeople')
            };
            $scope.param = {
               id:$stateParams.shopStoreId
            }
            console.log( $scope.param.id)

            FindStoreList.get({},function(data){
                if(data.result =="0x00001"){
                    $scope.libraryTubeSetting =data.responseData
                }

            })

            if($stateParams.ids!=""){
                 $scope.show = false;
                 $scope.storeManagerName = $stateParams.names.split(',');
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
            $scope.kuTube();
            $scope.save = function () {

                var setStorekeeperRequestDTO = {
                    shopStoreId:$scope.param.id,
                    storeManagerIds:$stateParams.ids.split(','),
                    storeManagerNames:$stateParams.names.split(',')
                }
                SetStorekeeper.save(setStorekeeperRequestDTO,function (data) {
                    alert(data.result);
                    if(data.result=="0x00001"){
                        alert("更新成功");
                        $state.reload('app.toMenu');
                    }
                })
            }

        }])
