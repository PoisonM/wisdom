angular.module('controllers',[]).controller('addFamilyCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetClerkInfoList','Global','GetBossShopList','GetClerkBySearchFile',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetClerkInfoList,Global,GetBossShopList,GetClerkBySearchFile) {
            $rootScope.title = "添加家人";
            $scope.param={
                index:0,
                flag:false,
                sysShopId:'',
                searchFile:''

            };
            $scope.getInfo=function(){
                GetClerkInfoList.query({
                    sysBossId:"",
                    sysShopId:$scope.param.sysShopId,
                    pageSize:"1000"
                },function(data){
                    $scope.addFamily = data

                })
            }
            $scope.getInfo()


            $scope.addEmployeesGo = function(){
                $state.go('addEmployees')
            }
            $scope.selShop = function () {
                $scope.param.flag = true;
                GetBossShopList.get({},function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null) {
                        $scope.shopList = data.responseData;
                    }
                })
            }
            $scope.selShopTrue = function(sysShopId){
                $scope.param.flag = false;
                $scope.param.sysShopId=sysShopId;
                $scope.getInfo()
            }
            $scope.all = function(){
                $scope.param.flag = false;
            }
            $scope.clearSearch = function () {
                $scope.param.searchFile = ''
            }
            $scope.search = function () {
                GetClerkBySearchFile.get({
                    searchFile:$scope.param.searchFile
                },function(data){
                    $scope.addFamily = data.responseData
                })
            }


        }])
