/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('productSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveProductTypeInfo','UpdateOneLevelTypeInfo',
        function ($scope,$rootScope,$stateParams,$state,SaveProductTypeInfo,UpdateOneLevelTypeInfo) {

            $rootScope.title = "产品品牌";
            $scope.param={
                editType : $stateParams.type,
                productTypeName:"",
                status:"1",/*不启动*/
                id:""
            };

               /*保存商品*/
             $scope.preservation=function () {
                 if($scope.param.editType=="add"){
                     if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                         $scope.param.status="1"
                     }else {
                         $scope.param.status="0"
                     }
                     SaveProductTypeInfo.get({productTypeName:$scope.param.productTypeName,status:$scope.param.status},function (data) {
                         if(data.result=="0x00002"){
                             $state.go("productBrand")
                         }
                     });
                 }else if($scope.param.editType=="edit"){
                     if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                         $scope.param.status="1"
                     }else {
                         $scope.param.status="0"
                     }
                     UpdateOneLevelTypeInfo.get({id:$scope.param.id,productTypeName:$scope.param.productTypeName,status:$scope.param.status},function (data) {
                         if(data.result=="0x00001"){
                             $state.go("productBrand")
                         }
                      })
                 }
             };
        }]);