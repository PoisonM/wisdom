angular.module('controllers',[]).controller('addEmployeesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetBossShopList','Global','SaveClerkInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetBossShopList,Global,SaveClerkInfo) {
            $rootScope.title = "添加家人";
            $scope.param={
                flag:false,
                beauticianStatus:true,
                managerStatus:false,
                fontDeskStatus:false,
                shopName:""
            };
            $scope.sysClerkDTO = {
                sysShopId:"11",
                mobile:"",
                name:"",
                role:""
            }
            $scope.shop = function(){
                GetBossShopList.get({},function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.fenShop = data.responseData
                        $scope.param.flag = true;

                    }

                })

            }

            $scope.selShop = function(id,sysShopName){
                $scope.param.flag = false;
                $scope.sysClerkDTO.sysShopId = id;
                $scope.param.shopName = sysShopName

            }
            $scope.all = function(){
                $scope.param.flag = false;
            };

            $scope.save = function () {
                if($scope.param.beauticianStatus == true){
                    $scope.sysClerkDTO.role += "美容师 "

                }
                if($scope.param.managerStatus == true){
                    $scope.sysClerkDTO.role += "店长 "

                }
                if($scope.param.fontDeskStatus == true){
                    $scope.sysClerkDTO.role += "前台 "

                }
                $scope.sysClerkDTO.role = $scope.sysClerkDTO.role.slice(0, $scope.sysClerkDTO.role.length-1);

                console.log( $scope.sysClerkDTO)
                SaveClerkInfo.save($scope.sysClerkDTO,function (data) {
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $state.go("addFamily")
                    }
                })
            }
            $scope.importAddressBookGo=function () {
                $state.go("importAddressBook")
            }

        }])
