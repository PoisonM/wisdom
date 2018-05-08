/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('beautySalonCtrl',
    ['$scope','$rootScope','$stateParams','$state','ShopDayAppointmentInfoByDate',
        function ($scope,$rootScope,$stateParams,$state,ShopDayAppointmentInfoByDate) {
            $rootScope.title = "唯美度养生会所";
            $scope.date=$stateParams.date;
            ShopDayAppointmentInfoByDate.get({
                startDate:"2018-00-00%2000:00:00",/*$scope.date*/
                endDate:'2019-00-00%2000:00:00',/*$scope.date*/
                sysShopId:'3'/*$stateParams.sysShopId*/
            },function(data){
                $scope.beautySalon = data.responseData;
                delete $scope.beautySalon.startTime;
                delete $scope.beautySalon.endTime;

            })
            /*日期插件*/
            $scope.dataS =  function(id){
                !function(id){
                    laydate.skin('dahong');
                    laydate({elem: id});
                }();

                //日期范围限制
                var start = {
                    elem: '#start',
                    format: 'YYYY-MM-DD',
                    min: laydate.now(), //设定最小日期为当前日期
                    max: '2099-06-16', //最大日期
                    istime: true,
                    istoday: false,
                    choose: function(datas){
                        end.min = datas; //开始日选好后，重置结束日的最小日期
                        end.start = datas //将结束日的初始值设定为开始日
                        $scope.date = datas
                    }
                };

                var end = {
                    elem: '#end',
                    format: 'YYYY-MM-DD',
                    min: laydate.now(),
                    max: '2099-06-16',
                    istime: true,
                    istoday: false,
                    choose: function(datas){
                        start.max = datas; //结束日选好后，充值开始日的最大日期
                    }
                };
                laydate(start);
                laydate(end);
            };
            $scope.none=function(){
                $("#laydate_box").css('display',"none")
            };
            $scope.canceledGo = function(sysClerkId){
                $scope.none()
                $state.go("canceled",{sysShopId:$stateParams.sysShopId,sysClerkId:sysClerkId,date:$scope.date})
            }

        }])