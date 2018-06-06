angular.module('controllers',[]).controller('oneTimeCtrl',
    ['$scope','$rootScope','$stateParams','$state','Global','SearchShopProjectList','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,Global,SearchShopProjectList,$ionicLoading) {
            $scope.param={
                timesList:[]
            }
            $scope.$on('$ionicView.enter', function() {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                SearchShopProjectList.get({
                    filterStr:"",
                    useStyle:'0'
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $ionicLoading.hide();
                        $scope.oneTime =data.responseData
                    }
                })
            })

            for(var  i=0;i<$rootScope.settingAddsome.editedRecharge.timesList.length;i++){
                $scope.param.timesList[i]=$rootScope.settingAddsome.editedRecharge.timesList[i].shopGoodsTypeId
            }


            $scope.selOneTime = function (domIndex) {
                if ( $scope.param.timesList.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach( $scope.param.timesList, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.timesList.splice(key, 1);
                        }
                        key++;
                    })
                } else {
                    $scope.param.timesList.push(domIndex);
                }
            }
            $scope.save = function () {
                for(var  i=0;i<$scope.param.timesList.length;i++){
                    $rootScope.settingAddsome.editedRecharge.timesList[i]={shopGoodsTypeId:$scope.param.timesList[i],goodsType:"0"}
                }
                $state.go($stateParams.url,{id:$stateParams.id})
            }
        }])
