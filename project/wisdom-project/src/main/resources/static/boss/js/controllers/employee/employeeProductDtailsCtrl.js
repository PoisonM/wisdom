/**
 * Created by Administrator on 2018/6/1.
 */
angular.module('controllers',[]).controller('employeeProductDtailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Consumes',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Consumes) {
            $rootScope.title = "产品详情";
            $scope.param={
                flag:true
            };
            $scope.chooseTab = function (type) {
                if(type =="0"){
                    $scope.param.flag = true
                }else{
                    $scope.param.flag = false;
                }
            };
            /*领取记录*/
            var userConsumeRequest={
                consumeType:"0",
                goodType:"4",
                pageSize:"10",
                shopUserId:"11"
            };
            Consumes.save(userConsumeRequest,function (data) {
                $scope.productDtails=data.responseData;
                console.log(data)
            })

        }]);
