/**
 * Created by Administrator on 2018/6/1.
 */
angular.module('controllers',[]).controller('employeeTreatmentCardDtailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Consumes',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Consumes) {
            $rootScope.title = "疗程卡详情";
            $scope.param={
                flag:true,
                sysUserId	:$stateParams.sysUserId
            };
            $scope.chooseTab = function (type) {
                if(type =="0"){
                    $scope.param.flag = true
                }else{
                    $scope.param.flag = false;
                }
            };
            var userConsumeRequest={
                consumeType:"1",
                goodType:"5",
                pageSize:"10",
                sysUserId	: $scope.param.sysUserId
            };
            Consumes.save(userConsumeRequest,function (data) {
                $scope.treatmentCardDtails=data.responseData;
                console.log( $scope.treatmentCardDtails);

            });
            /*点击顾客签字*/
            $scope.drawCardRecordsDetailGO=function () {
                $state.go("drawCardRecordsDetail")
            }
        }]);