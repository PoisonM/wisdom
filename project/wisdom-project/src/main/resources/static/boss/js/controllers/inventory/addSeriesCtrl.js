angular.module('controllers',[]).controller('addSeriesCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','TwoLevelProduct','Global','UpdateTwoLevelTypeInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,TwoLevelProduct,Global,UpdateTwoLevelTypeInfo) {
            $rootScope.title = "添加系列";
            $scope.param = {
                selTrue:[]
            };

            $scope.productBrandGo = function () {
                var requestList = {
                    requestList:$scope.requestList
                };
                UpdateTwoLevelTypeInfo.save(requestList,function(data){
                    if(data.result == '0x00002'){
                         $state.go("productBrand")
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
                TwoLevelProduct.get({
                   id:$stateParams.id
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.requestList = data.responseData;
                        $ionicLoading.hide();
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
                    productTypeName:"",
                    parentId:$stateParams.id

                };
                $scope.requestList.push(obj)
            }



        }])