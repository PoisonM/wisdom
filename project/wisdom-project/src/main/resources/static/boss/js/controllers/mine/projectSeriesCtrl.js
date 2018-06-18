angular.module('controllers',[]).controller('projectSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','TwoLevelProject','Global','UpdateTwoLevelProjectType',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,TwoLevelProject,Global,UpdateTwoLevelProjectType) {
            $rootScope.title = "添加系列";
            $scope.param = {
                selTrue:[]
            };
            $scope.projectBrandGo = function () {
                var requestList = {
                    requestList:$scope.requestList
                };
                UpdateTwoLevelProjectType.save(requestList,function(data){
                    if(data.result == '0x00001'){
                        $state.go("projectBrand")
                    }
                })

            };
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                TwoLevelProject.get({
                    id:$stateParams.id
                },function(data){
                    if(data.result==Global.SUCCESS){
                        $ionicLoading.hide();
                        $scope.requestList = data.responseData;
                        if(data.responseData==null){$scope.requestList=[]}
                        for(var i=0;i<$scope.requestList.length;i++){
                            $scope.requestList[i].parentId = $stateParams.id;
                            $scope.param.selTrue.push(false)
                        }
                    }
                });
            })

            $scope.selBtnShow = function(index){
                $scope.param.selTrue[index] =!$scope.param.selTrue[index]
              };
            $scope.sel = function(index){
                $scope.requestList[index].status = '1'
            };
            $scope.addSeriesLis = function(){

                var obj = {
                    status:"0",
                    projectTypeName:"",
                    parentId:$stateParams.id

                };
                $scope.requestList.push(obj)
            }



        }])