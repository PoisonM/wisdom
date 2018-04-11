PADWeb.controller("dayAppointmentCtrl", function($scope, $state, $stateParams,$filter,ngDialog,ShopDayAppointmentInfoByDate,$http) {
    var h = window.innerHeight/64-0.6875-0.765-1.128+'rem';
    $scope.h = "height:"+h;
    $scope.date = $filter("date")(Date.parse(new Date()),"yyyy-MM-dd");
    $scope.param = {
        week:[],
        btnActive:['btnActive','common'],
        day : [["09:00",'18'],["09:30","19"],["10:00","20"],["10:30","21"],["11:00","22"],["11:30","23"],["12:00","24"],["12:30","25"],["13:00","26"],["13:30","27"],["14:00","28"],["14:30","29"],["15:00","30"],["15:30","31"],["16:00","32"],["16:30","33"],["17:00","34"],["17:30","35"],["18:00","36"],["18:30","37"],["19:00","38"],["19:30","39"],["20:00","40"],["20:30","41"],["21:00","42"],["21:30","43"],["22:00","44"],["22:30","45"],["23:00","46"],["23:30","47"]],
        serachContent:'',/*搜索内容*/
        givingIndex:0,/*赠送Index*/
        AppointmentType:"散客",
        dayFlag:true,
        weekFlag:false,
        newProductObject:{
            index:0,
            titleFlag:false,
        },
        timeLength:false,/*修改预约-选择时长*/
        timeLengthObject:{
            time:''
        },
        /*修改预约-时长对象*/
        selectBeautician:false,/*修改预约-选择美容师*/
        detailsReservationText:"去消费",/*预约按钮详情的按钮展示*/
       /* scratchCardText:"去划卡",/!*预约按钮详情的按钮展示*!/
        detailsReservationBtn:false,/!*预约按钮详情的按钮展示*!/
        scratchCardBtn:false,/!*预约按钮详情的按钮展示*!/*/
        num:1,
        data:[{
            title:'合作',
            content:['全部','整形系列']
        },{
            title:'面部',
            content:['全部','保湿','美白','补水','植萃','抗皱']
        },
            {
                title:'眼部',
                content:['全部','保湿']
            },{
                title:'SPA',
                content:['全部','保湿']
            }],
    };
    $scope.time = function (time){
        console.log(1)
        $scope.param.week = [];
        var time = time.replace("年","-").replace("月","-").replace("日","");
        var now = new Date(time);
        var nowTime = now.getTime() ;
        var day = now.getDay();
        var oneDayTime = 24*60*60*1000 ;
        var MondayTime = nowTime - (day-1)*oneDayTime ;
        var SundayTime =  nowTime + (7-day)*oneDayTime ;
        $scope.Monday = new Date(MondayTime).setDate(new Date(MondayTime).getDay());
        var today,ms,thatDay, y, m, d,endDate;
        today = new Date(MondayTime).getTime();
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
            $scope.param.btnActive[1]='btnActive';
            $scope.param.weekFlag = true;
            $scope.param.dayFlag = false;
        }else{
            $scope.arrTime = $scope.param.day;
            $scope.param.btnActive[0]='btnActive';
            $scope.param.btnActive[1]='common';
            $scope.param.weekFlag = false;
            $scope.param.dayFlag = true;
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

    //银行卡
    $scope.bank = function(){
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'bank',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'payType ngdialog-theme-custom',
            disableAnimation:true,

        });
    }
    detailsReservation && detailsReservation($scope,ngDialog);
    individualTravelerAppointment && individualTravelerAppointment($scope,ngDialog);
    weeklyReservation && weeklyReservation($scope,ngDialog);


/*长按新建*/
    $scope.onHold = function (num) {
        console.log(num);
    };

    $scope.detailsWrap = function(index1,index2,type) {

        if(type!="散客"){
            $scope.ngDialog = ngDialog;
            ngDialog.open({
                template: 'detailsWrap',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    if(type == '消费'){
                        $scope.param. detailsReservationText = "去消费";
                    }else{ $scope.param. detailsReservationText = "去划卡";}
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',

            });
        }else{
            $scope.ngDialog = ngDialog;
            ngDialog.open({
                template: 'individual',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'individual ngdialog-theme-custom'
            });
        }

    };
    /*var url = "http://localhost:9051/appointmentInfo/shopDayAppointmentInfoByDate";
    $http.get(url,{params:{ 'sysShopId':'3',
        'startDate':'2018-00-00 00:00:00',
        'endDate':'2019-00-00 00:00:00'}}
    ).success(function(data){

    });*/
    ShopDayAppointmentInfoByDate.get({
        'sysShopId':'3',
        'startDate':'2018-00-00 00:00:00',
        'endDate':'2019-00-00 00:00:00'
    },function(data){
       // ManagementUtil.checkResponseData(data,"");
        /*if(data.result == Global.SUCCESS){

        }*/
    })
});



