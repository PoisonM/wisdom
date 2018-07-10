/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyTrainingVideoCtrl',
    ['$scope','$rootScope','$stateParams','$state','BeautyUtil','GetClerkScheduleInfo',
        'GetBeautyShopInfo','Global','SaveUserAppointInfo','GetCurrentLoginUserInfo','$filter','$ionicLoading','$sce',
        function ($scope,$rootScope,$stateParams,$state,BeautyUtil,GetClerkScheduleInfo,
                  GetBeautyShopInfo,Global,SaveUserAppointInfo,GetCurrentLoginUserInfo,$filter,$ionicLoading,$sce) {

        $scope.param.videos = [
            {
                name:'店员手机操作演示',
                url:'https://mxavi.oss-cn-beijing.aliyuncs.com/beautydemo/%E5%BA%97%E5%91%98%E6%BC%94%E7%A4%BA.mp4'
            },
            {
                name:'店主手机操作演示',
                url:'https://mxavi.oss-cn-beijing.aliyuncs.com/beautydemo/%E8%80%81%E6%9D%BF%E6%BC%94%E7%A4%BA.MP4'
            },
            {
                name:'店员PAD端操作演示',
                url:'https://mxavi.oss-cn-beijing.aliyuncs.com/beautydemo/pad%E5%89%8D%E5%8F%B0%E6%BC%94%E7%A4%BA.mp4'
            },
            {
                name:'消费者操作演示',
                url:'https://mxavi.oss-cn-beijing.aliyuncs.com/beautydemo/%E6%B6%88%E8%B4%B9%E8%80%85%E6%BC%94%E7%A4%BA.mp4'
            }
        ]

        angular.forEach($scope.param.videos,function (val,index) {
            val.url = angular.copy($sce.trustAsResourceUrl(val.url));
        })
}]);
