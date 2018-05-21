/**
 * Created by Administrator on 2018/5/21.
 */
/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('projectSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveShopProjectType','UpdateOneLevelProjectType',
        function ($scope,$rootScope,$stateParams,$state,SaveShopProjectType,UpdateOneLevelProjectType) {

            $rootScope.title = "产品品牌";
            $scope.param={
                editType : $stateParams.type,
                projectTypeName:"",
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
                    SaveShopProjectType.get({projectTypeName:$scope.param.projectTypeName,status:$scope.param.status},function (data) {
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
                    UpdateOneLevelProjectType.get({id:$scope.param.id,projectTypeName:$scope.param.projectTypeName,status:$scope.param.status},function (data) {
                        if(data.result=="0x00001"){
                            $state.go("productBrand")
                        }
                    })
                }
            };
        }]);