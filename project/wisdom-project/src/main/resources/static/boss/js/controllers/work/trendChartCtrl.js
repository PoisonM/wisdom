angular.module('controllers',[]).controller('trendChartCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetCashEarningsTendency','Global',
        function ($scope,$rootScope,$stateParams,$state,GetCashEarningsTendency,Global) {
            if($stateParams.id !='a'){
                GetCashEarningsTendency.get({
                    sysShopId:$stateParams.id
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.trendChart = data.responseData;
                        $scope.HPic()

                    }

                })

            }else{
                GetCashEarningsTendency.get({
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.trendChart = data.responseData;
                        $scope.HPic()

                    }

                })
            }




            $scope.HPic =function(){
                $scope.series = [
                    {
                        name: '现金',
                        data: [20,30.40,22,33,42,25]
                    },

                ];

                $scope.options = {
                    title:'',
                    chart: {
                        type: 'column'
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
                        enabled: false
                    },
                    credits: {		            //去除右下角highcharts标志
                        enabled: false
                    },
                };

                for(var i=0;i< $scope.trendChart.length;i++){
                    $scope.series[0].data[i] =  $scope.trendChart[i].cashEarnings;
                    $scope.options.xAxis.categories[i] =  $scope.trendChart[i].formateDate
                }
            }

        }])