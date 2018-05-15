/**
 * Created by Administrator on 2018/5/2.
 */
angular.module('controllers',["ionic-datepicker"]).controller('sevenDayChartsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetExpenditureAndIncome','BossUtil','Global','$filter',
        function ($scope,$rootScope,$stateParams,$state,GetExpenditureAndIncome,BossUtil,Global,$filter) {

            $rootScope.title = "7日收益趋势图";

            $scope.test = '1234';

            $scope.param = {
                income : 0,
                expenditure:0,
                startDate : BossUtil.getNowFormatDate(),
                dateX : ['2018-04-16','2018-04-16','2018-04-16','2018-04-16','前天','昨日','今日']
            }


            $scope.getInfo=function(){
                if($stateParams.id!='a'){
                    GetExpenditureAndIncome.get({sysShopId:$stateParams.id},function (data) {
                        if(data.result==Global.SUCCESS&&data.responseData!=null){
                            $scope.sevenDayCharts = data.responseData
                            $scope.HPic()
                        }


                    })
                }else{
                    GetExpenditureAndIncome.get({},function (data) {
                        if(data.result==Global.SUCCESS&&data.responseData!=null){
                            $scope.sevenDayCharts = data.responseData
                            $scope.HPic()
                        }


                    })
                }

            }

            $scope.HPic =function(){
                $scope.cardSeries = [
                    {
                        name: '耗卡',
                        data: [20,30.40,22,33,42,25]
                    },
                    {
                        name: '业绩',
                        data: [1,2,3,4],
                        color:'orange'
                    }
                ];

                $scope.cardOptions = {
                    title:'',
                    chart: {
                        type: 'line'
                    },
                    xAxis: {
                        categories: [],
                        crosshair: true
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: ''
                        }
                    },

                    legend: {
                        layout: '',
                        align: 'right',
                        verticalAlign: 'top',
                        borderWidth: 0
                    },
                    credits: {		            //去除右下角highcharts标志
                        enabled: false
                    },
                };

                for(var i=0;i< $scope.sevenDayCharts.length;i++){
                    $scope.cardSeries[0].data[i] =  $scope.sevenDayCharts[i].expenditure;
                    $scope.cardSeries[1].data[i] =  $scope.sevenDayCharts[i].income;
                    $scope.cardOptions.xAxis.categories[i] =  $scope.sevenDayCharts[i].formateDate
                }
            }
            $scope.getInfo()

}]);