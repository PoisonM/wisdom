/**
 * Created by Administrator on 2018/5/3.
 */
angular.module('controllers',[]).controller('confirmedCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetAppointmentInfoById','UpdateAppointmentInfoById',
        function ($scope,$rootScope,$stateParams,$state,GetAppointmentInfoById,UpdateAppointmentInfoById) {
            $rootScope.title = "已确认预约";
            $scope.date=$stateParams.date;
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
            $scope.cancel = function(){
                UpdateAppointmentInfoById.get({
                    shopAppointServiceId:"id_7", /*$stateParams.shopAppointServiceId*/
                    status:"4"
                },function(data){
                    $state.go('appointment')
                })
               
            }
            GetAppointmentInfoById.get({
                shopAppointServiceId:"id_7" /*$stateParams.shopAppointServiceId*/
            },function(data){
                $scope.confirmed = data.responseData

            })
            $scope.pho=function(num){
                window.location.href = "tel:" + num;
            }


        }]);