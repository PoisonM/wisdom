var seckill = angular.module('controllers',[]).controller('seckillListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SeckillList',
        function ($scope,$rootScope,$stateParams,$state,SeckillList) {
            $rootScope.title = "秒杀专区";
            $scope.$on('$ionicView.enter', function(){
                $scope.PageParamVoDTO ={
                    pageNo:$scope.pageNo,
                    pageSize:$scope.pageSize,
                };
                SeckillList.save($scope.PageParamVoDTO,function (data) {
                    $scope.seckillList = data.responseData.responseData;
                    console.log(  $scope.seckillList )
                })

                $scope.seckillInfo = function (id) {
                    $state.go("seckillInfo",{id:id});
                }
            });
        }]);

seckill.directive('timerBtn', function() { // 倒计时按钮
    return {
        restrict: 'A',
        replace: true,
        scope: {
            startTime: '=startTime',
            getData: '&getData'
        },
        template: '<span class="btn btn-danger" ng-disabled="startTime> 0" ng-bind="startTime > 0 ? \'活动倒计时:\' +showTime : \'\'" ng-click="getData()"></span>',
        controller: function($scope, $interval) {
            var formatTime = function(sys_second) {
                if (sys_second > 0) {
                    sys_second -= 1;
                    var day = Math.floor((sys_second / 3600) / 24);
                    if (day < 0) {
                        day = 0;
                    }
                    var hour = Math.floor((sys_second / 3600) % 24);
                    if (hour < 0) {
                        hour = 0;
                    }
                    var minute = Math.floor((sys_second / 60) % 60);
                    if (minute < 0) {
                        minute = 0;
                    }
                    var second = Math.floor(sys_second % 60);
                    if (second < 0) {
                        second = 0;
                    }
                    return  (hour < 10 ? "0" + hour : hour) + "小时 " + (minute < 10 ? "0" + minute : minute) + "分钟" + (second < 10 ? "0" + second : second)+"秒";
                }
            }

            var timer = $interval(function() {
                $scope.startTime -= 1;
                $scope.showTime = formatTime($scope.startTime);
                if($scope.startTime < 1) {
                    $interval.cancel(timer);
                };
            }, 1000);

        }
    };
});