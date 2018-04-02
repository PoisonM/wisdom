PADWeb.controller("dayAppointmentCtrl", function($scope, $state, $stateParams) {
    console.log("dayAppointment")
    $scope.week = [];
    $scope.param = {
        week:[],
        btnActive:['btnActive','common'],
        day : ["09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:30","21:00","21:30","22:00","22:30","23:00","23:30","1"]

    };


    $scope.time = function (time){
        var time = time.replace("年","-").replace("月","-").replace("日","")
        var now = new Date(time);
        var nowTime = now.getTime() ;
        var day = now.getDay();
        var oneDayTime = 24*60*60*1000 ;
        var MondayTime = nowTime - (day-1)*oneDayTime ;
        var SundayTime =  nowTime + (7-day)*oneDayTime ;
        $scope.Monday = new Date(MondayTime).setDate(new Date(MondayTime).getDay());
        var today,ms,thatDay, y, m, d,endDate;
        today = new Date(SundayTime).getTime();
        for(var i=0;i<7;i++){
            ms = today + i*24*60*60*1000;
            thatDay = new Date(ms);
            y = thatDay.getFullYear();
            m = thatDay.getMonth()+1;
            d = thatDay.getDate();
            endDate = m+"月"+d+'日';
            $scope.param.week.push(endDate);
        }
    };
    $scope.date = laydate.now(0,'YYYY年MM月DD日');
    $scope.time($scope.date);
    $scope.dataS = function (id) {
        !function (id) {
            laydate({
                elem: id,
                skin:'danlan',
                format: "YYYY年MM月DD日", // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                istime: false,
                choose: function(datas){ //选择日期完毕的回调
                    laydate(start);
                    laydate(end);
                    $scope.time(datas);




                }
            });
            laydate.skin('danlan');

        }();

        //日期范围限制
        var start = {
            elem: '#start',
            format: 'YYYY年MM月DD日',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-06-16', //最大日期
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };

        var end = {
            elem: '#end',
            format: 'YYYY年MM月DD日',
            min: laydate.now(),
            max: '2099-06-16',
            istime: true,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，充值开始日的最大日期
            }
        };

    };
    $scope.arrTime = $scope.param.day;
    $scope.appointmentChange = function (type) {
        if(type =="week"){
            $scope.arrTime=$scope.param.week;
            $scope.param.btnActive[0]='common';
            $scope.param.btnActive[1]='btnActive'
        }else{
            $scope.arrTime = $scope.param.day;
            $scope.param.btnActive[0]='btnActive';
            $scope.param.btnActive[1]='common';
        }
    };





    $scope.mess = {
        name:["李",'王','k'],
        time:[[{
            start:"10:30",
            end:"12:30",
            mess:{

            }
        },{
            start:"13:30",
            end:"14:30",
        }],[{
            start:"09:30",
            end:"12:30",
        }]]
    };
    var indexArr=[];
    var startArr=[];
    $scope.tdLengthArr =new Array();
    for(var i=0;i<$scope.mess.time.length;i++){
        startArr[i] = [];
        indexArr[i] =[];
        for(var j=0;j<$scope.mess.time[i].length;j++){
            var start = $scope.arrTime.indexOf($scope.mess.time[i][j].start);
            var end = $scope.arrTime.indexOf($scope.mess.time[i][j].end);
            var indexTime = end-start;
            startArr[i].push(start);
            indexArr[i].push(indexTime)
        }
    }
    /*$timeout(function () {
     var trIndex = document.querySelectorAll(".lis tr");
     for(var i=0;i<startArr.length;i++){
     for(var j=0;j<startArr[i].length;j++){
     var tdIndex = trIndex[startArr[i][j]].querySelectorAll('td')[i];
     tdIndex.innerHTML ="<p><span>安琪拉</span><span>小气泡</span></p>"+
     "<p><span>11:00</span><span>房间-1</span></p>";
     for(var n=1;n<indexArr[i][j];n++){
     var tr = trIndex[startArr[i][j]+n];
     var td = tr.querySelectorAll('td')[i];
     tr.removeChild(td);
     console.log(tr)
     }
     tdIndex.setAttribute("rowspan",indexArr[i][j]);
     }
     }
     },100);*/
   // $scope.demo = new Demo();
    /*$scope.hasMore = false;
    //      $scope.dataNull=false;     // 无数据提示
    $scope.SName = "您当前没有待办事务";
    $scope.do_refresher = function() {
        console.log(12);
        $scope.currentPage = 1;
        $scope.bItems = [];
        ajax.post(reqUrl, {
            "rowsOfPage": rowsOfPage,
            "currentPage": $scope.currentPage
        }, function (listdata, successful) {
            if (successful) {
                $scope.bItems = listdata.datas || [];
                $scope.hasMore = ($scope.bItems.length < listdata.totalRows);
                if ($scope.bItems.length == 0) {
                    $scope.dataNull = true;
                } else {
                    $scope.dataNull = false;
                }
            } else {
                $scope.hasMore = false;
            }
            $scope.$broadcast("scroll.refreshComplete");
        });
    }*/


})


