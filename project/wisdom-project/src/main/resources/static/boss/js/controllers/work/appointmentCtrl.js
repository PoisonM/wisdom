/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('appointmentCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopAppointmentNumberInfo',
        function ($scope,$rootScope,$stateParams,$state,GetShopAppointmentNumberInfo) {

            $rootScope.title = "预约";

/*日期插件*/
            $scope.date=laydate.now();
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
            }

            GetShopAppointmentNumberInfo.get({
                searchDate:"2018-04-27"/*$scope.date*/
            },function(data){
                $scope.appointment = data.responseData;
               /* if(data.result == Global.SUCCESS){

                 }*/
            })

            $scope.healthClubGo = function(sysShopId){
                $scope.none()
                $state.go("beautySalon",{sysShopId:sysShopId,date:$scope.date})
            }



        }]);