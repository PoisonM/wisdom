angular.module('controllers',[]).controller('treatmentCtrl',
    ['$scope','$rootScope','$stateParams','$state','Global','SearchShopProjectList',
        function ($scope,$rootScope,$stateParams,$state,Global,SearchShopProjectList) {
            $scope.param = {
                periodList:[]
            }
            SearchShopProjectList.get({
                filterStr:"",
                useStyle:'1'
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.treatment =data.responseData
                }
            });
            for(var  i=0;i<$rootScope.settingAddsome.editedRecharge.periodList.length;i++){
                $scope.param.periodList[i]=$rootScope.settingAddsome.editedRecharge.periodList[i].shopGoodsTypeId
            }

            $scope.selTreatment = function (domIndex) {
                if ($scope.param.periodList.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach($scope.param.periodList, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.periodList.splice(key, 1);
                        }
                        key++;
                    })
                } else {
                    $scope.param.periodList.push(domIndex);
                }
                $scope.save = function () {
                    for(var  i=0;i<$scope.param.periodList.length;i++){
                        $rootScope.settingAddsome.editedRecharge.periodList[i]={shopGoodsTypeId:$scope.param.periodList[i],goodsType:"1"}
                    }
                    $state.go($stateParams.url,{id:$stateParams.id})
                }

                
            }
        }])