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


            $scope.series = [{
                name: '业绩',
                data: [4,7,5,6,9,2,10]
            }];

            $scope.cardSeries = [ {
                name: '消卡',
                data: [20,30.40,22,33,42,25]
            }];

            $scope.options = {
                title:'',
                chart: {
                    type: 'column'
                },
                xAxis: {
                    categories: $scope.param.dateX,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                // series: $scope.sportSeries,
                legend: {
                    enabled: false			//隐藏data中的name显示
                },
                credits: {		            //去除右下角highcharts标志
                    enabled: false
                }
            };

            $scope.cardOptions = {
                title:'',
                chart: {
                    type: 'column'
                },
                xAxis: {
                    categories: $scope.param.dateX,
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                // series: $scope.sportSeries,
                legend: {
                    enabled: false			//隐藏data中的name显示
                },
                credits: {		            //去除右下角highcharts标志
                    enabled: false
                }
            };
}]);