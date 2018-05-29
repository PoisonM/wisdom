angular.module('controllers',[]).controller('treatmentCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserCourseProjectList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserCourseProjectList) {
            $rootScope.title = "疗程卡";
            $scope.param={
                flag:false,
                goodType:"6"
            };
            /*点击筛选*/
            $scope.sel = function(){
                $scope.param.flag = true
            };
            /*点击关闭*/
            $scope.disNone=function () {
                $scope.param.flag = false;
            };
            /*点击选择类型*/
            $scope.selType = function(type){
                $scope.param.goodType = type
            };
            /*点击重置*/
            $scope.reset = function() {
                $scope.param.goodType = '6'
            };
            console.log($stateParams.sysUserId);
            GetUserCourseProjectList.get({cardStyle:"1",sysUserId:$stateParams.sysUserId},function (data) {
                console.log(data);
                $scope.treatmentCard=data.responseData;

            });
            $scope.goTreatmentCard=function (sysUserId) {
                console.log(1);
                $state.go("treatmentCardDtails",{sysUserId:sysUserId})
            };
        }]);