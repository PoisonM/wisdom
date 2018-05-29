/**
 * Created by Administrator on 2018/5/6.
 */
angular.module('controllers',[]).controller('rechargeListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRechargeCardList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetRechargeCardList,Global) {

            $rootScope.title = "充值卡列表";
            GetRechargeCardList.get({
                pageSize:1000
            },function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.rechargeList = data.responseData

                }

            })
            $scope.editedRechargeGo =function (id) {
                $state.go("editedRecharge",{id:id})
            }

        }]);