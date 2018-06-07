angular.module('controllers',[]).controller('treatmentCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','GetUserCourseProjectList',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,GetUserCourseProjectList) {
            $rootScope.title = "疗程卡";
            
            GetUserCourseProjectList.get({cardStyle:"1",sysUserId:$stateParams.sysUserId},function (data) {
                console.log(data);
                $scope.treatmentCard=data.responseData;

            });

            $scope.goTreatmentCard=function (sysUserId) {
                console.log(1);
                $state.go("treatmentCardDtails",{sysUserId:sysUserId})
            };
        }]);