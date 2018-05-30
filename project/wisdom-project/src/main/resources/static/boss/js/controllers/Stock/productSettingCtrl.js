/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('productSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveProductTypeInfo','UpdateOneLevelTypeInfo',
        function ($scope,$rootScope,$stateParams,$state,SaveProductTypeInfo,UpdateOneLevelTypeInfo) {

            $rootScope.title = "产品品牌";
            $scope.param={
                productTypeName:$stateParams.productTypeName,
                editType : $stateParams.type,
                id:$stateParams.id,
                status:''/*不启动*/
            };

            if($stateParams.status=="1"){/**/
                $scope.param.status=false
            }else {
                $scope.param.status=true
            }
               /*保存商品*/
             $scope.preservation=function () {
                 if($scope.param.status==true){/*如果为true启动，反之不启动*/
                     $stateParams.status="0"
                 }else {
                     $stateParams.status="1"
                 }
                 if($scope.param.editType=="add"){

                     SaveProductTypeInfo.get({productTypeName:$scope.param.productTypeName,status:$stateParams.status},function (data) {
                         console.log(data);
                         if(data.result=="0x00002"){
                             $state.go("productBrand")
                         }
                     });
                 }else if($scope.param.editType=="edit"){

                     UpdateOneLevelTypeInfo.save({id:$scope.param.id,productTypeName:$scope.param.productTypeName,status:$stateParams.status},function (data) {
                         if(data.result=="0x00002"){
                             $state.go("productBrand")
                         }
                      })
                 }
             };
        }]);