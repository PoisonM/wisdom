PADWeb.controller("attendanceCtrl", function($scope, $state, $stateParams,$filter) {
    $scope.param = {
        now: $scope.now,/*月时间*/
        monthArr:[], /*整月时间*/
        red :['red', ''],
        show:[true,false],
        timeShow:false/*日期的显示*/
    };
    var now = new Date();
    $scope.param.now = now.getTime();
    getTime($scope.param.now);
    /*获得时间*/
    function getTime(date){
        $scope.param.monthArr =[];
        var date = new Date(date);
        with(date) {
            year = date.getYear() + 1900;
            month = date.getMonth() + 1;
        }
        today = year + "-" + month + "-1";
        var new_year = year;  //取当前的年份
        var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
        if(month>12){new_month -=12;new_year++;}
        var new_date = new Date(new_year,new_month,1);
        var lastDay = (new Date(new_date.getTime()-1000*60*60*24)).getDate();
        for(var i=1;i<=lastDay;i++){
            $scope.monthAll = new Date(year +'-'+ (month-1)+'-'+i).getTime();
            $scope.param.monthArr.push($scope.monthAll)
        }
        console.log($scope.param.monthArr)
    }
    /*月份按钮*/
    $scope.MonthChange = function (type) {
        if(type=="last"){
            $scope.param.now = now.setMonth(now.getMonth() - 1);
        }else{
            $scope.param.now = now.setMonth(now.getMonth() + 1);
        }
        getTime($scope.param.now)

    };
    /*tab切换*/
    $scope.appointmentChange = function (type) {
        if (type == "record") {
            $scope.arrTime = $scope.week;
            $scope.param.red[0] = '';
            $scope.param.red[1] = 'red';
            $scope.param.show[0] = false;
            $scope.param.show[1] = true;
            $scope.param.timeShow = true;
        } else {
            $scope.arrTime = $scope.day;
            $scope.param.red[0] = 'red';
            $scope.param.red[1] = '';
            $scope.param.show[0] = true;
            $scope.param.show[1] = false;
            $scope.param.timeShow = false;

        }
    };
})
