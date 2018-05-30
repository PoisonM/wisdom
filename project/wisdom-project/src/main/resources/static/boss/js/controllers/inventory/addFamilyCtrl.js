angular.module('controllers',[]).controller('addFamilyCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetClerkInfoList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetClerkInfoList,Global) {
            $rootScope.title = "添加家人";
            $scope.param={
                index:0,
                flag:false
            };

            GetClerkInfoList.query({
                sysBossId:"11",
                sysShopId:"11",
                pageSize:"100"
            },function(data){
                $scope.addFamily = data
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.addFamily = data.responseData;
                }
            })
            $scope.selEmployees = function(index){
                $scope.param.index = index;
            }

            $scope.addEmployeesGo = function(){
                $state.go('addEmployees')
            }
            $scope.selShop = function () {
                $scope.param.flag = true;
            }
            $scope.selShopTrue = function(){
                $scope.param.flag = false;
            }
            $scope.all = function(){
                $scope.param.flag = false;
            }


        }])
