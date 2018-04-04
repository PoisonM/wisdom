PADWeb.controller("dayAppointmentCtrl", function($scope, $state, $stateParams,$filter) {
    console.log("dayAppointment");
    $scope.date = $filter("date")(Date.parse(new Date()),"yyyy-MM-dd")
    $scope.param = {
        week:[],
        btnActive:['btnActive','common'],
        day : ["09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:30","21:00","21:30","22:00","22:30","23:00","23:30"],
        detailsReservation:false,/*预约详情*/
        consumption:false,/*消费*/
        selectSingle:false,/*选择单次*/
        selectTreatmentCard:false,/*消费-选择疗程卡*/
        selectProduct:false,/*选择产品*/
        collectionCard:false,/*选择套卡*/
        consumptionNextStep:false,/*消费-消费-下一步*/
        balancePrepaid:false,/*余额充值*/
        relatedStaff:false,/*关联员工*/
        scratchCard:false,/*划卡*/
        scratchCardSelectTreatmentCard:false,/*划卡-选择疗程卡*/
        giving:false,/*赠送*/
        givingProduct:true,/*赠送-产品*/
        givingVouchers:false,/*赠送-项目*/
        scratchCard:false,/*赠送优惠券*/
        ModifyAppointment:true,/*修改预约按钮*/
        individualTravelerAppointment:false,/*散客详情*/
        modifyingAppointment:true, /*修改预约*/
        serachContent:'',/*搜索内容*/
        givingIndex:0,/*赠送Index*/
        AppointmentType:"散客",
        dayFlag:true,
        weekFlag:false,
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
                    console.log(datas)
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
        $scope.param.btnActive = ['btnActive','common'];
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
    //预约详情

    $scope.detailsReservation = function(index1,index2,e,status){
        $scope.param.ModifyAppointment = true;
           /* var top = (e.clientY +100)/128;
            var left = (e.clientX +100)/128;
            var screen = document.documentElement.clientWidth;*/
           $scope.param.consumption = false;
           $scope.param.selectSingle = false;
           $scope.param.selectTreatmentCard = false;
           $scope.param.selectProduct = false;
           $scope.param.collectionCard = false;
           $scope.param.consumptionNextStep = false;
           $scope.param.balancePrepaid = false;
           $scope.param.relatedStaff = false;
           $scope.param.scratchCard = false;
           $scope.param.giving = false;
           $scope.param.givingProduct = false;
           $scope.param.givingVouchers = false;
           $scope.param.scratchCardSelectTreatmentCard = false;
           $scope.param.individualTravelerAppointment = false;
           $scope.param.detailsReservation = false;
           $scope.param.modifyingAppointment = false;
            if(status == '消费'||status == '划卡'){
                $scope.param.detailsReservation = true;
                var detailsWrap = document.getElementsByClassName("detailsWrap")[0];
                if(status == '消费'){
                    $scope.param. detailsReservationText = "去消费";
                }else{ $scope.param. detailsReservationText = "去划卡";}
                if(e.clientY >337){
                    detailsWrap.style.top = 3.5+"rem";
                    detailsWrap.style.left = (e.clientX +100)/128+"rem";
                }else{
                    detailsWrap.style.top = (e.clientY +100)/128+"rem";
                    detailsWrap.style.left = (e.clientX +100)/128+"rem";
                }
            }else {
                $scope.param.individualTravelerAppointment = true;
                var detailsWrap = document.getElementsByClassName("individual")[0];
                if(e.clientY >100){
                    detailsWrap.style.top = 1.5+"rem";
                    detailsWrap.style.left = (e.clientX +100)/128+"rem";

                }else{
                    detailsWrap.style.top = (e.clientY +100)/128+"rem";
                    detailsWrap.style.left = (e.clientX +100)/128+"rem";
                }
            }


           $scope.detailsReservationPic = function(){
               $scope.param.detailsReservation = false;
           };
           $scope.individualTravelerAppointmentClose = function(){
               $scope.param.individualTravelerAppointment = false;
           }

    }
    //消费-消费
    $scope.candelConsumption = function(){
        $scope.param.consumption = false;
    };
    $scope.consumptionProjuct = function(status,index){
        if(index == 0){
            if(status == 'selectSingle'){
                $scope.param.selectSingle = false;
            }else if(status == 'selectTreatmentCard'){
                $scope.param.selectTreatmentCard = false;
            }else if(status == 'selectProduct'){
                $scope.param.selectProduct = false;
            }else if(status == 'collectionCard'){
                $scope.param.collectionCard = false;
            }else if(status == 'consumptionNextStep'){
                $scope.param.consumptionNextStep = false;
            }else if(status == 'balancePrepaid'){
                $scope.param.balancePrepaid = false;
                $scope.param.consumption = true;
            }else if(status == 'scratchCard'){
                $scope.param.scratchCard = false;
            }else if(status == 'giving'){
                $scope.param.giving = false;
                $scope.param.consumption = true;
            }else if(status == 'relatedStaff'){
                $scope.param.relatedStaff = false;
                $scope.param.consumption = true;
            }else if(status == 'scratchCardSelectTreatmentCard'){
                $scope.param.scratchCardSelectTreatmentCard = false;
                $scope.param.scratchCard = true;
            }else if(status == 'modifyingAppointment'){
                $scope.param.modifyingAppointment = false;
                if($scope.param.AppointmentType == "散客"){
                    $scope.param.individualTravelerAppointment = true;
                }else{
                    $scope.param.detailsReservation = true;
                }
            }

        }else{
            if(status == 'selectSingle'){
                $scope.param.selectSingle = false;
            }else if(status == 'selectTreatmentCard'){
                $scope.param.selectTreatmentCard = false;
            }else if(status == 'selectProduct'){
                $scope.param.selectProduct = false;
            }else if(status == 'collectionCard'){
                $scope.param.collectionCard = false;
            }else if(status == 'consumptionNextStep'){
                $scope.param.consumptionNextStep = false;
                $scope.param.consumption = false;
            }else if(status == 'scratchCard'){
                $scope.param.scratchCardSelectTreatmentCard = true;
                $scope.param.scratchCard = false;
            }else if(status == 'giving'){
                $scope.param.giving = false;
                $scope.param.consumption = true;
            }else if(status == 'relatedStaff'){
                $scope.param.relatedStaff = false;
                $scope.param.consumption = true;
            }else if(status == 'scratchCardSelectTreatmentCard'){
                $scope.param.scratchCardSelectTreatmentCard = false;
                $scope.param.scratchCard = true;
            }else if(status == 'modifyingAppointment'){
                if($scope.param.AppointmentType == "散客"){
                    $scope.param.individualTravelerAppointment = true;
                }else{
                    $scope.param.detailsReservation = true;
                }
                $scope.param.modifyingAppointment = false;

            }
        }

    };

    //消费-单次
    $scope.candelSelectSingle = function(){
        $scope.param.selectSingle = false;
        $scope.singleFinish = function(){
            $scope.param.selectSingle = false;
        }
    };
    //消费-疗程卡
    $scope.candelSelectTreatmentCard = function(){
        $scope.param.selectTreatmentCard = false;
        $scope.treatmentCardFinish = function(){
            $scope.param.selectTreatmentCard = false;
        }
    }
    //消费-选择产品
    $scope.candelSelectProduct = function(){
        $scope.param.selectProduct = false;
        $scope.selectProductFinish = function(){
            $scope.param.selectProduct = false;
        }
    };
    //消费-选择套卡
    $scope.candelCollectionCard = function(){
        $scope.param.collectionCard = false;
    };
    $scope.collectionCardFinish = function(){
        $scope.param.collectionCard = false;
    };
    detailsReservation && detailsReservation($scope);
    individualTravelerAppointment && individualTravelerAppointment($scope);
    console.log($scope.param);
    weeklyReservation && weeklyReservation($scope)
    /*$scope.nextStepBtnConsumption = function(){
        $scope.nextStepBtnConsumption
    }*/


})


