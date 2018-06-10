/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('beautyBranchCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopCustomerArriveList','$filter',
        function ($scope,$rootScope,$stateParams,$state,GetShopCustomerArriveList,$filter) {

            $rootScope.title = "美容院分店";
            $scope.param={
                current: "1",
                date:$stateParams.date
            };
            $scope.setCurrent=function (param) {
                $scope.param.current=param;
                $scope.getInfo()

            };
            $scope.getInfo= function () {
                GetShopCustomerArriveList.get({
                    condition:$scope.param.current,
                    startTime:$scope.param.date+' 00:00:00',
                    endTime:$scope.param.date+' 23:59:59',
                    sysShopId:$stateParams.sysShopId
                },function(data){
                     $scope.beautyBranch = data.responseData
                })
            }
            $scope.getInfo();

            $scope.HPic =function(){
                if($scope.param.current !=2)return
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

            /*日期插件*/
            $scope.param.date=$scope.param.date.replace(/00/g,'');
            $scope.param.date=$scope.param.date.replace(/:/g,'');

            var disabledDates = [
                new Date(1437719836326),
                new Date(),
                new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
                new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
                new Date("08-14-2015"), //Short format
                new Date(1439676000000) //UNIX format
            ];
            //方便的年月日设置方式，正和我意，可以随便改了。
            var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
            var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];

            // 日期选择后的回调函数
            var datePickerCallback = function (val) {
                if (typeof (val) === 'undefined') {
                } else {
                    var dateValue = $filter('date')(val, 'yyyy-MM-dd') + " 00:00:00";
                    $scope.param.date = $filter('date')(val, 'yyyy-MM-dd')
                    $scope.getInfo();
                }
            };

            //主体对象
            $scope.datepickerObjectEnd = {
                titleLabel: '选择日期',  //可选
                todayLabel: '今天',  //可选
                closeLabel: '关闭',  //可选
                setLabel: '确定',  //可选
                setButtonType: 'button-assertive',  //可选
                todayButtonType: 'button-assertive',  //可选
                closeButtonType: 'button-assertive',  //可选
                inputDate: new Date(),  //可选，输入值
                mondayFirst: true,  //可选,星期一开头
                disabledDates: disabledDates, //可选
                weekDaysList: weekDaysList, //可选
                monthList: monthList, //可选
                templateType: 'modal', //可选i.e.的模式 modal or popup(兼容模式？)
                showTodayButton: 'true', //可选
                modalHeaderColor: 'bar-positive', //可选
                modalFooterColor: 'bar-positive', //可选
                from: new Date(2008, 8, 2), //可选
                to: new Date(2030, 8, 25),  //可选
                callback: function (val) {  //Mandatory
                    datePickerCallback(val);
                },
                dateFormat: 'yyyy-MM-dd', //可选
                closeOnSelect: true, //可选,设置选择日期后是否要关掉界面。呵呵，原本是false。
            };

        }]);