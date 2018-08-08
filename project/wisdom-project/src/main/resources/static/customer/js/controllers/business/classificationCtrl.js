/**
 * Created by Administrator on 2018/7/27.
 */
angular.module('controllers',[]).controller('classificationCtrl',
    ['$scope','$rootScope','$stateParams','$state',"$ionicLoading","GetOneProductClassList","GetTwoProductClassList",'Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetOneProductClassList,GetTwoProductClassList,Global) {
            $scope.getInfo=function () {
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                });
                GetOneProductClassList.get(function (data) {
                    $ionicLoading.hide();
                    if(data.result==Global.SUCCESS&&data.responseData!=null)
                    {
                        $scope.param.firstList=data.responseData;
                    }
                    $scope.checkBox(0,$scope.param.firstList[0].productClassId)
                });
                $scope.checkBox=function(index,productClassId) {
                    $scope.param.selection=index;
                    GetTwoProductClassList.get({productClassId:productClassId},function (data) {
                        if(data.result==Global.SUCCESS&&data.responseData!=null)
                        {
                            $scope.param.twoList=data.responseData;
                        }/*else {
                            $scope.param.twoList=""
                        }*/
                    })
                };
            };
            $scope.$on('$ionicView.enter', function() {
                $scope.param={
                    firstList:{},
                    twoList:{},
                    selection:"0"
                };
                $scope.getInfo();
            });
           /*点击 搜索进入搜索页面*/
           $scope.search=function () {
               $state.go("searchPage")
           };
           /*点击二级类目商品跳转到具体页面*/
          $scope.specific=function (id) {
             $state.go("specificGoods",{id:id})
          }
        }]);