angular.module('controllers',[]).controller('trendChartCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {
            function fun_date(aa){
                var date1 = new Date(),
                    time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
                console.log(time1)
                var date2 = new Date(date1);
                date2.setDate(date1.getDate()+aa);
                var time2 = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
                console.log(time2)
            }

            fun_date(-7);//7天前的日期
        }])