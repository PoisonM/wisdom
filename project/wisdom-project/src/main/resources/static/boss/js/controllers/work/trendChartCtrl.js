angular.module('controllers',[]).controller('trendChartCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetExpenditureAndIncome',
        function ($scope,$rootScope,$stateParams,$state,GetExpenditureAndIncome) {
            GetExpenditureAndIncome.get({},function(data){

            })

            $scope.HPic = function(){
                var tangyanchun = setInterval(function () {
                    if($("#box").length != 0){
                        clearInterval(tangyanchun)
                        var myChart = echarts.init(document.getElementById("box"));
                        option = {
                            title: {
                                text: '近七日业绩',
                                subtext: ''
                            },
                            legend: {
                                data:['业绩'],
                                right:20,
                                top:5
                            },

                            grid: {
                                left: '3%',   //图表距边框的距离
                                right: '10%',
                                bottom: '3%',
                                containLabel: true,

                            },

                        xAxis: {
                                type: 'category',
                                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                                axisLine:{
                                    lineStyle:{
                                        color:'red'
                                    }
                                },
                                //x轴文字旋转
                                axisLabel:{
                                    interval:0,//横轴信息全部显示
                                    rotate:-30,//-30度角倾斜显示
                                },
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: [
                                {
                                name:'业绩',
                                data: [820, 932, 901, 934, 1290, 1330, 1320],
                                type: 'line'
                                },
                            ]
                        };

                       /* for(var i=0;i<$scope.balanceOfPaymentsAll.length;i++){
                            option.xAxis.data[i]=$scope.balanceOfPaymentsAll[i].formateDate;
                            option.series[0].data[i] = $scope.balanceOfPaymentsAll[i].cashEarnings

                        }*/
                        myChart.setOption(option)

                    }
                },100);
            }
            $scope.HPics = function(){
                var tangyanchun = setInterval(function () {
                    if($("#boxs").length != 0){
                        clearInterval(tangyanchun)
                        var myChart = echarts.init(document.getElementById("box"));
                        option = {
                            title: {
                                text: '近七日耗卡',
                                subtext: ''
                            },
                            legend: {
                                data:['业绩','耗卡'],
                                right:20,
                                top:5
                            },

                            grid: {
                                left: '3%',   //图表距边框的距离
                                right: '10%',
                                bottom: '3%',
                                containLabel: true,

                            },

                            xAxis: {
                                type: 'category',
                                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                                axisLine:{
                                    lineStyle:{
                                        color:'red'
                                    }
                                },
                                //x轴文字旋转
                                axisLabel:{
                                    interval:0,//横轴信息全部显示
                                    rotate:-30,//-30度角倾斜显示
                                },
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: [

                                {
                                    name:'耗卡',
                                    type:'line',
//                stack: '总量',
                                    symbolSize:4,
                                    color:['orange'],
                                    smooth:false,   //关键点，为true是不支持虚线的，实线就用true
                                    itemStyle:{
                                        normal:{
                                            lineStyle:{
                                                width:2,
                                                type:'dotted'  //'dotted'虚线 'solid'实线
                                            }
                                        }
                                    },
                                    data:[500, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]
                                },

                            ]
                        };

                        /* for(var i=0;i<$scope.balanceOfPaymentsAll.length;i++){
                         option.xAxis.data[i]=$scope.balanceOfPaymentsAll[i].formateDate;
                         option.series[0].data[i] = $scope.balanceOfPaymentsAll[i].cashEarnings

                         }*/
                        myChart.setOption(option)

                    }
                },100);
            }
            $scope.HPic()
            $scope.HPics()
        }])