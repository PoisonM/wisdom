angular.module('controllers',[]).controller('projectSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','TwoLevelProject','Global','UpdateTwoLevelProjectType','$ionicPopup','$timeout',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,TwoLevelProject,Global,UpdateTwoLevelProjectType,$ionicPopup,$timeout) {
            $rootScope.title = "添加系列";
            $scope.param = {
                selTrue:[]
            };
            $scope.projectBrandGo = function () {
                for(var i=0;i<$scope.requestList.length;i++){
                    if($scope.requestList[i].projectTypeName==''&&$scope.requestList[i].status=='0'){
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">系列名不能为空</span>',
                            /*okText:'确定'*/
                        });
                        $timeout(function () {
                            alertPopup.close()
                        },1000)
                        return
                    }
                }
                var requestList = {
                    requestList:$scope.requestList
                };
                UpdateTwoLevelProjectType.save(requestList,function(data){
                    if(data.result == '0x00001'){
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;margin-left: 0.2rem">保存成功</span>',
                            /*okText:'确定'*/
                        });
                        $timeout(function () {
                            alertPopup.close()
                        },500);
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