angular.module('controllers',[]).controller('kuTubeCtrl',
    ['$scope','$rootScope','$stateParams','$state',"BossUtil",'Global','$filter','GetClerkInfoList','GetBossShopList','GetClerkBySearchFile',
        function ($scope,$rootScope,$stateParams,$state,BossUtil,Global,$filter,GetClerkInfoList,GetBossShopList,GetClerkBySearchFile) {
            $rootScope.title = "添加家人";
            $scope.param={
                index:0,
                flag:false,
                sysShopId:'',
                ids:[],
                names:[],
                searchFile:''
            };
            if($stateParams.storeManagerId!=''){
                $scope.param.ids = $stateParams.storeManagerId.split(",")
            }
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


            $scope.selFilamy = function (domIndex,name) {


                if ($scope.param.ids.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach( $scope.param.ids, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.ids.splice(key, 1);
                            $scope.param.names.splice(name,1);
                        }
                        key++;
                    })
                } else {
                    if($scope.param.ids.length>=3)return
                    $scope.param.ids.push(domIndex);
                    $scope.param.names.push(name);
                }
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
            $scope.save = function () {
                  $state.go('libraryTubeSetting',{ids:$scope.param.ids.join(','),names:$scope.param.names.join(','),id:$stateParams.id})
            }

        }])