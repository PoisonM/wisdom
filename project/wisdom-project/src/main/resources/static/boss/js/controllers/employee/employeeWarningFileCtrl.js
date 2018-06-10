/**
 * Created by Administrator on 2018/6/1.
 */
angular.module('controllers',[]).controller('employeeWarningFileCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetEarlyWarningList','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetEarlyWarningList,Global) {

            $rootScope.title = "预警档案";
            $scope.queryType = "one";
            $scope.archiveCount = 0;

            $scope.chooseTab = function(type){
                $scope.queryType = type;
                $scope.archiveCount = 0;
                $scope.getInfo()
            }

            $scope.getInfo = function(){
                GetEarlyWarningList.get({
                    pageNo:1,
                    pageSize:100,
                    queryType:$scope.queryType
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.warningFile = data.responseData.info;
                        angular.forEach($scope.warningFile,function (val,index) {
                            angular.forEach(val,function (val1,index) {
                                $scope.archiveCount++;
                            })
                        })
                        console.log($scope.warningFile);
                    }
                })
            };

            $scope.getInfo()

            $scope.archivesGo = function(id){
                $state.go("archives",{id:id})
            }

        }])
