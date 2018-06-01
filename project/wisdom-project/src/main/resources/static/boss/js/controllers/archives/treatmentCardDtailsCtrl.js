angular.module('controllers',[]).controller('treatmentCardDtailsCtrl',
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
                    $scope.getInfo()
                }else{
                    $scope.param.flag = false;
                    $scope.getInfo()
                }
            };
            var userConsumeRequest={
                consumeType:"1",
                goodType:"5",
                pageSize:"10",
                sysUserId	: $scope.param.sysUserId
            };
            $scope.getInfo = function () {
                Consumes.save(userConsumeRequest,function (data) {
                    $scope.treatmentCardDtails=data.responseData;
                    console.log( $scope.treatmentCardDtails);

                });
            }
            $scope.getInfo()
            /*点击顾客签字*/
            $scope.drawCardRecordsDetailGO=function () {
             $state.go("drawCardRecordsDetail")
            }
        }]);