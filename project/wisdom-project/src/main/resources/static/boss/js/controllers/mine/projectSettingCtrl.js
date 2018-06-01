
angular.module('controllers',[]).controller('projectSettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveShopProjectType','UpdateOneLevelProjectType',
        function ($scope,$rootScope,$stateParams,$state,SaveShopProjectType,UpdateOneLevelProjectType) {

            $rootScope.title = "产品品牌";
            $scope.param={
                projectTypeName:$stateParams.projectTypeName,
                editType : $stateParams.type,
                id:$stateParams.id,
                status:""/*不启动*/
            };
            if($stateParams.status=="1"){/**/
                $scope.param.status=false
            }else {
                $scope.param.status=true
            }
            /*保存商品*/
            $scope.preservation=function () {
                if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                    var status = '0'
                }else {
                    var status = '1'
                }
                console.log($scope.param.status==true)
                console.log(status)
                if($scope.param.editType=="add"){

                    SaveShopProjectType.save({projectTypeName:$scope.param.projectTypeName,status:status},function (data) {
                        if(data.result=="0x00001"){
                            $state.go("projectBrand")
                        }
                    });
                }else if($scope.param.editType=="edit"){
                    UpdateOneLevelProjectType.save({id:$scope.param.id,projectTypeName:$scope.param.projectTypeName,status:status},function (data) {
                        if(data.result=="0x00001"){
                            $state.go("projectBrand")
                        }
                    })
                }
            };
        }]);