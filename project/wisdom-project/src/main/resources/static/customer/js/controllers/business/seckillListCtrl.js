var seckill = angular.module('controllers',[]).controller('seckillListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SeckillList',
        function ($scope,$rootScope,$stateParams,$state,SeckillList) {
            document.title = '秒杀专区';
            /*$rootScope.title = "秒杀专区";*/
            $scope.$on('$ionicView.enter', function(){
                $scope.PageParamVoDTO ={
                    pageNo:$scope.pageNo,
                    pageSize:100,
                };
                SeckillList.save($scope.PageParamVoDTO,function (data) {
                    $scope.seckillList = data.responseData.responseData;
                    console.log(  $scope.seckillList )
                })

                $scope.seckillInfo = function (item) {
                    if(item.status == 0){
                        $state.go("seckillInfo",{id:item.fieldId});
                    }
                }
            });
        }]);

seckill.directive('timerBtn', function() { // 倒计时按钮
    return {
        restrict: 'A',
        replace: true,
        scope: {
            startTime: '=startTime',
            remindMe: '=remindMe',
            getData: '&getData'
        },
        template: '<span class="btn btn-danger" ng-disabled="startTime> 0" ng-bind="remindMe == 0 ? \'距离活动结束还剩:\' +showTime : \'距离活动开始还剩:\' +showTime " ng-click="getData()"></span>',
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
                    return  (day >0?((day < 10 ? "0" + day : day) + "天"):"") + (hour < 10 ? "0" + hour : hour) + "小时" + (minute < 10 ? "0" + minute : minute) + "分钟" + (second < 10 ? "0" + second : second)+"秒";
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