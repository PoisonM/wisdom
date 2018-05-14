angular.module('controllers',[]).controller('partialFilesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','FindArchives','GetBossShopList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,FindArchives,GetBossShopList) {
            $rootScope.title = "全院档案";
            $scope.param={
                sysShopId:"11",
                pageSize:"1",
                pageNo:"1",
                queryField:"",
                blackBox:false,
                fileBOx:false
            };
            $scope.searchCont = "";
            FindArchives.get({sysShopId:$scope.param.sysShopId,pageSize:$scope.param.pageSize,pageNo:$scope.param.pageNo,queryField:$scope.param.queryField},function (data) {
                if(data.result == "0x00001"){
                    $scope.fileList = [];
                    $scope.info = data.responseData.info;
                }
            });
            /*点击跳转到预警档案*/
            $scope.switching=function () {
                $state.go("warningFile")
            };
           $scope.goActives=function (id) {
               $state.go("archives",{id:id})
           };
           /*点击切换档案*/
           $scope.tabSwitching=function () {
               $scope.param.blackBox=true;
               $scope.param.fileBOx=true;
               GetBossShopList.get(function (data) {
                   if(data.result == "0x00001"){
                       $scope.switchingList = [];
                       $scope.switchingList = data.responseData;
                       console.log(data)
                   }
               })
           };
          $scope.checkBgBox=function () {
              $scope.param.blackBox=false;
              $scope.param.fileBOx=false;
          };
          $scope.checkFileBox=function (id) {
              FindArchives.get({sysShopId:$scope.param.sysShopId,pageSize:$scope.param.pageSize,pageNo:$scope.param.pageNo,queryField:$scope.param.queryField},function (data) {
                  if(data.result == "0x00001"){
                      $scope.fileList = [];
                      $scope.info = data.responseData.info;
                  }
              });
              $scope.param.blackBox=false;
              $scope.param.fileBOx=false;
          }

        }]);