PADWeb.controller("dayAppointmentCtrl", function($scope, $state, $stateParams,$filter,ngDialog,$http,$timeout,ShopDayAppointmentInfoByDate,GetUserCardProjectList,GetAppointmentInfoById,GetUserProjectGroupList,GetUserProductList,GetUserCourseProjectList,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject,GetUserShopProjectList,ConsumeCourseCard,GetShopClerkList,UpdateAppointmentInfoById,FindArchives,GetShopProjectList,ShopWeekAppointmentInfoByDate,GetShopClerkScheduleList)
{
    $scope.date = $filter("date")(Date.parse(new Date()), "yyyy-MM-dd");
    $scope.param = {
        week: [],
        btnActive: ['btnActive', 'common'],
        code: [{"0": "00:00"}, {"1": "00:30"}, {"2": "01:00"}, {"3": "01:30"}, {"4": "02:00"}, {"5": "02:30"}, {"6": "03:00"}, {"7": "03:30"}, {"8": "04:00"}, {"9": "04:30"}, {"10": "05:00"}, {"11": "05:30"}, {"12": "06:00"}, {"13": "06:30"}, {"14": "07:00"}, {"15": "07:30"}, {"16": "08:00"}, {"17": "08:30"}, {"18": "09:00"}, {"19": "09:30"}, {"20": "10:00"}, {"21": "10:30"}, {"22": "11:00"}, {"23": "11:30"}, {"24": "12:00"}, {"25": "12:30"}, {"26": "13:00"}, {"27": "13:30"}, {"28": "14:00"}, {"29": "14:30"}, {"30": "15:00"}, {"31": "15:30"}, {"32": "16:00"}, {"33": "16:30"}, {"34": "17:00"}, {"35": "17:30"}, {"36": "18:00"}, {"37": "18:30"}, {"38": "19:00"}, {"39": "19:30"}, {"40": "20:00"}, {"41": "20:30"}, {"42": "21:00"}, {"43": "21:30"}, {"44": "22:00"}, {"45": "22:30"}, {"46": "23:00"}, {"47": "23:30"}],
        bgBlack:false,
        serachContent: '', /*搜索内容*/
        givingIndex: 0, /*赠送Index*/
        dayFlag: true,
        weekFlag: false,
        ModifyAppointment:true,/*修预约改*/
        ModifyAppointmentObject:{/*修预约改对象*/
            hoursTime:[],
            hoursType:[],
            hoursTimeShow:"",

        },
        cancellationFlag:true,/*取消预约按钮的显示隐藏*/
        newProductObject: {/*新建预约 - 选择项目*/
            index: 0,
            titleFlag: false,
            content:true,
            filterStr:"",/*搜索文本*/
            newProjectData:"",
            selfProductData:"",
            shopProjectId:"",
            shopProjectName:"",
        },
        scratchCardObj:{
            scratchCardData:{},
            note:""
        },
        ModifyAppointmentObject:{/*新建预约*/
            appointPeriod:'1',/*时长*/
            beauticianName:"",
            type:"",
            newProjectDataFlag:[],/*疗程卡的选中显示*/
            selfProductDataFlag:[],/*本店项目的选中显示*/
            productData:[],
            productNum:'0',/*项目个数*/
            customerIndex:"-1",
            appointStartTime:"",/*预约开始时间*/
            appointEndTime:"",/*预约结束时间*/
            detail:"",/*备注*/
            status:"0",
            ModifyAppointmentData:""
        },
        selectCustomersObject:{/*选择顾客*/
            data:"",
            sysUserId:"",/*客户id*/
            sysUserName:"",/*客户名*/
            sysUserPhone:"",/*客户手机*/
            queryField:""/*查询条件，可为空*/
        },
        appointmentNew:"",
        appointmentObject: {/*日预约对象*/
            appointmentDate: '',
            beautician: [],/*美容师*/
            appointmentInfo: [],/*美容师下的预约列表*/
            point: [],/*点*/
            list:[],/*数据处理后 -- */
        },
        addCustomersObject:{/*添加顾客对象*/
            sex:"女",
            picSrc:"",
            userName:"",
            userPhone:""
        },
        week:{
            weekData:''/*周预约数据*/
        },
        index:{
            index1:"",
            index2:""
        },
        details:"",/**/
        consumptionObj:{
            sysUserId:"",
            sysShopId:"",
            singleByshopId:{
                detailProject:"",/*三级*/

            },/*某个店的单次 疗程卡 产品  数据*/
          /*  singleMessByshopId:{},/!*某个店的单次 疗程卡 产品  的三级 数据*!/*/
            collectionCardByShowId:"",/*某个店的套卡数据*/
            singleByUserId:"",/*某个用户的单次数据*/
            singleFilterStr:"",/*项目名称模糊过滤*/
            treatmentCardByUserId:"",/*某个用户的疗程数据*/
            productByUserId:"",/*某个用户的产品数据*/
            collectionCardByUserId:"",/*某个用户的套卡数据*/
            singleByUserIdFlag:true,
            treatmentCardByUserIdFlag:true,
            productByUserIdFlag:true,
            collectionCardByUserIdFlag:true,
            consumptionType:"",
            balancePrepaidCtrlData:""/*充值卡数据*/

        },
        individualTravelerAppointmentObj:{/*日预约详情 （包含预约卡项）*/
            individualTravelerAppointment:"",
            scratchCardFlag:true,/*划卡按钮*/
            consumptionFlag:true/*消费按钮*/
        },
        selectBeautician: false, /*修改预约-选择美容师*/
        relatedAtaffData:"",/*关联员工数据*/
        num: 1,
        day:[],/*用于寻找预约颜色的其中一个数据*/
        day:[],/*侧边时间循环*/
    };
    $scope.scheduling = true;
    $scope.time = function (time) {
        $scope.param.week = [];
        var time = time.replace("年", "-").replace("月", "-").replace("日", "");
        var now = new Date(time);
        var nowTime = now.getTime();
        var day = now.getDay();
        var oneDayTime = 24 * 60 * 60 * 1000;
        var MondayTime = nowTime - (day - 1) * oneDayTime;
        var SundayTime = nowTime + (7 - day) * oneDayTime;
        $scope.Monday = new Date(MondayTime).setDate(new Date(MondayTime).getDay());
        var today, ms, thatDay, y, m, d, endDate;
        today = new Date(MondayTime).getTime();
        for (var i = 0; i < 7; i++) {
            ms = today + i * 24 * 60 * 60 * 1000;
            thatDay = new Date(ms);
            y = thatDay.getFullYear();
            m = thatDay.getMonth() + 1;
            d = thatDay.getDate();
            endDate = m + "月" + d + '日';
            $scope.param.week.push(endDate);
        }
    };
    $scope.date = laydate.now(0, 'YYYY年MM月DD日');
    $scope.time($scope.date);
    $scope.dataS = function (id) {
        !function (id) {
            laydate({
                elem: id,
                skin: 'danlan',
                format: "YYYY年MM月DD日", // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                istime: false,
                choose: function (datas) { //选择日期完毕的回调
                    laydate(start);
                    laydate(end);
                    $scope.time(datas);
                    var a = datas.replace(/年/g, "-");
                    var b = a.replace(/月/g, "-");
                    var c = b.replace(/日/g, "");
                    console.log(c);
                    dayAll()
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
   var  dayAll = function(){
        ShopDayAppointmentInfoByDate.get({
            sysShopId:'3',
            startDate:"2018-00-00 00:00:00",
            endDate:"2019-00-00 00:00:00"
        },function(data){
            var memeda = data.responseData;
            /*得到循环时间*/
            var hourTime = [];
            for(var i=0;i<$scope.param.code.length;i++){
                for(key in $scope.param.code[i] ){
                    hourTime.push($scope.param.code[i][key])
                    if($scope.param.code[i][key] == memeda.startTime ){
                        var a= i;
                    }
                    if($scope.param.code[i][key] == memeda.endTime ){
                        var b= i;
                    }
                }
            }
            $scope.param.days=hourTime.slice(a,b+1);
            $scope.param.day=$scope.param.code.slice(a,b+1);
            for (key  in memeda) {
                if (key == 'endTime' || key == "startTime") {
                } else {
                    $scope.param.appointmentObject.beautician.push(key);
                    $scope.param.appointmentObject.appointmentInfo.push(memeda[key].appointmentInfo);
                    $scope.param.appointmentObject.point.push(memeda[key].point);

                }
            }




            /*处理数据*/
            /*list:[
             {status:[],
             sysUserName:[],
             shopProjectName:[]}
             \]
             想要的数据格式
             根据时间编码找到对应的索引，通过索引拿到数据
             * */

            for(var i=0;i<$scope.param.appointmentObject.appointmentInfo.length;i++){
                $scope.param.appointmentObject.list[i] = new Object;
                $scope.param.appointmentObject.list[i].status = new Array;
                $scope.param.appointmentObject.list[i].sysUserName = new Array;
                $scope.param.appointmentObject.list[i].shopProjectName = new Array;
                $scope.param.appointmentObject.list[i].time = new Array;
                $scope.param.appointmentObject.list[i].sysUserId = new Array;
                $scope.param.appointmentObject.list[i].sysShopId = new Array;
                $scope.param.appointmentObject.list[i].textShowOrHide = new Array;
                for (var e = 0; e < $scope.param.day.length; e++) {
                    $scope.param.appointmentObject.list[i].status[e]=6;
                    $scope.param.appointmentObject.list[i].sysUserName[e]=null;
                    $scope.param.appointmentObject.list[i].shopProjectName[e]=null;
                    $scope.param.appointmentObject.list[i].time[e]=null;
                    $scope.param.appointmentObject.list[i].sysUserId[e]=null;
                    $scope.param.appointmentObject.list[i].sysShopId[e]=null;
                    $scope.param.appointmentObject.list[i].textShowOrHide[e]=0;
                    for(var j=0;j<$scope.param.appointmentObject.appointmentInfo[i].length;j++){
                        for (var k = 0;k < $scope.param.appointmentObject.appointmentInfo[i][j].scheduling.split(",").length; k++) {
                            if ($scope.param.appointmentObject.appointmentInfo[i][j].scheduling.split(",")[k] == objTemp($scope.param.day[e])) {
                                $scope.param.appointmentObject.list[i].sysUserName[e] = $scope.param.appointmentObject.appointmentInfo[i][j].sysUserName;
                                $scope.param.appointmentObject.list[i].shopProjectName[e] = $scope.param.appointmentObject.appointmentInfo[i][j].shopProjectName;
                                $scope.param.appointmentObject.list[i].sysUserId[e] = $scope.param.appointmentObject.appointmentInfo[i][j].sysUserId;
                                $scope.param.appointmentObject.list[i].sysShopId[e] = $scope.param.appointmentObject.appointmentInfo[i][j].sysShopId;
                                $scope.param.appointmentObject.list[i].time[e] = $scope.param.days[e];

                                if ($scope.param.appointmentObject.appointmentInfo[i][j].scheduling.split(",")[0] == objTemp($scope.param.day[e])) {
                                    $scope.param.appointmentObject.list[i].textShowOrHide[e]=1;

                                }



                                if($scope.param.appointmentObject.appointmentInfo[i][j].status == 1){
                                    $scope.param.appointmentObject.list[i].status[e] = 1
                                } else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 2){
                                    $scope.param.appointmentObject.list[i].status[e] = 2
                                }else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 3){
                                    $scope.param.appointmentObject.list[i].status[e] = 3
                                }else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 4){
                                    $scope.param.appointmentObject.list[i].status[e] = 4
                                } else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 0){
                                    $scope.param.appointmentObject.list[i].status[e] = 0
                                }
                            }
                        }
                    }
                }
            }
        })


        function objTemp(obj) {
            for (key  in obj) {
                return key
            }
        }
    }
    $scope.appointmentChange = function (type) {
        if (type == "week") {
            $scope.weekAll()
            $scope.arrTime = $scope.param.week;
            $scope.param.btnActive[0] = 'common';
            $scope.param.btnActive[1] = 'btnActive';
            $scope.param.weekFlag = true;
            $scope.param.dayFlag = false;
            var mySwiper3 = new Swiper('.swiper-container3',{
                direction: 'vertical',
                scrollbar: '.swiper-scrollbar',
                slidesPerView: 'auto',
                mousewheelControl: true,
                freeMode: true,
                onTouchStart: function(swiper){		//手动滑动中触发


                },
                onTouchMove: function(swiper,event){		//手动滑动中触发
                    mySwiper.update(); // 重新计算高度;
                    console.log(mySwiper.update())
                    var _viewHeight = document.getElementsByClassName('swiper-wrapper')[0].offsetHeight;
                    var _contentHeight = document.getElementsByClassName('swiper-slide')[0].offsetHeight;
                    if(mySwiper.translate < 50 && mySwiper.translate > 0) {
                        $(".init-loading").html('下拉刷新...').show();
                    }else if(mySwiper.translate > 50 ){
                        $(".init-loading").html('释放刷新...').show();
                    }
                },
                onTouchEnd: function(swiper) {
                    var _viewHeight = document.getElementsByClassName('swiper-wrapper')[0].offsetHeight;
                    var _contentHeight = document.getElementsByClassName('swiper-slide')[0].offsetHeight;
                    // 上拉加载
                    if(mySwiper.translate <= _viewHeight - _contentHeight - 50 && mySwiper.translate < 0) {
                        // console.log("已经到达底部！");

                        if(loadFlag){
                            $(".loadtip").html('正在加载...');
                        }else{
                            $(".loadtip").html('没有更多啦！');
                        }

                        setTimeout(function() {
                            $(".loadtip").html('上拉加载更多...');
                            mySwiper.update(); // 重新计算高度;
                        }, 800);
                    }

                    // 下拉刷新
                    if(mySwiper.translate >= 50) {
                        $(".init-loading").html('正在刷新...').show();
                        $(".loadtip").html('上拉加载更多');
                        loadFlag = true;
                        setTimeout(function() {
                            $(".refreshtip").show(0);
                            $(".init-loading").html('刷新成功！');
                            setTimeout(function(){
                                $(".init-loading").html('').hide();
                            },800);
                            $(".loadtip").show(0);

                            //刷新操作
                            mySwiper.update(); // 重新计算高度;
                        }, 1000);
                    }else if(mySwiper.translate >= 0 && mySwiper.translate < 50){
                        $(".init-loading").html('').hide();
                    }
                    return false;
                }
            });
            setTimeout(function(){
                var mySwiper4 = new Swiper('.swiper-container4',{
                    freeMode: true,
                    onTouchStart: function(swiper){		//手动滑动中触发
                        $scope.flag.upDownScrollFlag = false;
                        console.log($scope.flag)

                    },
                    onReachEnd: function(){
                        alert('到了最后一个slide');
                    },

                });
            },2000);

            $('.tab a').click(function(){

                $(this).addClass('active').siblings('a').removeClass('active');
                mySwiper2.slideTo($(this).index(), 500, false)

                $('.w').css('transform', 'translate3d(0px, 0px, 0px)')
                $('.swiper-container4 .swiper-slide-active3').css('height','auto').siblings('.swiper-slide').css('height','0px');
                mySwiper.update();
            });
        } else {
            $scope.param.appointmentObject.appointmentDate = [];
            $scope.param.appointmentObject.beautician = [];
            $scope.param.appointmentObject.point = [];
            $scope.param.appointmentObject.list = [];
            $scope.param.days =[];
            dayAll()
            $scope.arrTime = $scope.param.day;
            $scope.param.btnActive[0] = 'btnActive';
            $scope.param.btnActive[1] = 'common';
            $scope.param.weekFlag = false;
            $scope.param.dayFlag = true;
        }
    };


    dayAll();
    //银行卡
    $scope.bank = function () {
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'bank',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function ($scope, $interval) {
                $scope.close = function () {
                    $scope.closeThisDialog();
                };
            }],
            className: 'payType ngdialog-theme-custom',
            disableAnimation: true,

        });
    };


    /*长按新建*/
    $scope.onHold = function (index1,index2,type) {
        if(type == 6){
            $scope.param.index.index1 = index1;
            $scope.param.index.index2 = index2;
            $scope.param.ModifyAppointmentObject.status = "0";
            $scope.param.appointmentObject.list[index1].status[index2] = 5;
            $scope.param.appointmentObject.list[index1].sysUserName[index2] = "新建预约";
            $scope.param.ModifyAppointmentObject.ModifyAppointmentData = "";
            $scope.param.ModifyAppointmentObject.appointStartTime =""
            $scope.param.ModifyAppointmentObject.appointEndTime=""
            $scope.param.ModifyAppointmentObject.appointPeriod=""
            $scope.param.ModifyAppointmentObject.detail=""
            $scope.param.newProductObject.shopProjectId=""
            $scope.param.selectCustomersObject.sysUserId=""
            $scope.param.selectCustomersObject.sysUserName=""
            $scope.param.selectCustomersObject.sysUserPhone=""
            $scope.param.ModifyAppointmentObject.status="";
            $scope.param.ModifyAppointmentObject.productNum = "0";
            ngDialog.open({
                template: 'appointmentType',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    //$scope.param.bgBlack = true;
                    $scope.close = function () {
                        $scope.closeThisDialog();
                        $scope.param.appointmentObject.list[index1].status[index2] = 6;
                        $scope.param.appointmentObject.list[index1].sysUserName[index2] = null;
                    };
                }],

                className: ' ngdialog-theme-default ngdialog-theme-custom',

            })
    }};
/*加载预约详情项目 根据预约主键查询预约项目*/
    $scope.detailsWrap = function (index1, index2,type,sysUserId,sysShopId){
        if(type==6)return;
        if(type !=5){
            $scope.param.consumptionObj.sysUserId = sysUserId;
            $scope.param.consumptionObj.sysShopId = sysShopId;
            $scope.ngDialog = ngDialog;
            ngDialog.open({
                template: 'individual',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    GetUserCardProjectList.get({
                        appointmentId:"1"
                    },function(data){
                        $scope.param.individualTravelerAppointmentObj.individualTravelerAppointment = data.responseData;
                        if(data.responseData.consume.length>=0){
                            $scope.param.individualTravelerAppointmentObj.consumptionFlag=true
                        }else{
                            $scope.param.individualTravelerAppointmentObj.consumptionFlag=false;
                        }
                        if(data.responseData.punchCard.length>=0){
                            $scope.param.individualTravelerAppointmentObj.scratchCardFlag=true
                        }else{
                            $scope.param.individualTravelerAppointmentObj.scratchCardFlag=false;
                        }
                    });
                    /*预约详情*/
                    GetAppointmentInfoById.get({
                        shopAppointServiceId:"id_7"
                    },function(data){
                        $scope.param.week.details = data.responseData;
                    });
                    $scope.close = function () {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'ngdialog-theme-default',

            });
        }else if(type==5){
            ngDialog.open({
                template: 'appointmentType',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.close = function () {
                        $scope.closeThisDialog();
                        $scope.param.appointmentObject.list[index1].status[index2] =6;
                        $scope.param.appointmentObject.list[index1].sysUserName[index2] = null;
                    };
                }],

                className: 'ngdialog-theme-default',

                className: 'ngdialog-theme-default ',

            })
        }
        detailsReservation && detailsReservation($scope, ngDialog,GetUserProjectGroupList,GetUserProductList,GetUserCourseProjectList,             SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject,GetUserShopProjectList,GetUserShopProjectList,FindArchives,GetShopProjectList,GetShopProjectList,ShopWeekAppointmentInfoByDate);

    };

    console.log($scope.param.appointmentObject.appointmentInfo);
    console.log($scope.param.appointmentObject.list);

    /*周*/
  $scope.weekAll = function(){
      ShopWeekAppointmentInfoByDate.get({
          sysShopId:"11",
          startDate:"2018-04-01 00:00:00",
          endDate:"	2018-04-10 00:00:00"
      },function(data){
          $scope.param.week.weekData = data.responseData;
          $scope.navLeftWeekTime = "";
          var arrWeek = [];
          for(var key in $scope.param.week.weekData){
              arrWeek.push($scope.param.week.weekData[key]);
          }
          $scope.navLeftWeekTime = arrWeek[0];
      })
  };


  /*关联员工*/
    $scope.getShopClerkList = function(obj,attribute){
        $scope.relatedAtaffFlag = [];
        GetShopClerkList.get({
            pageNo:1,
            pageSize:100
        },function(data){
            $scope.param.relatedAtaffData = data.responseData;
            $scope.obj = obj;
            $scope.attribute = attribute;
            for(var i=0;i<$scope.param.relatedAtaffData.length;i++){
                $scope.relatedAtaffFlag[i]= false;
                for(j=0;j<obj.length;j++){
                    if(obj[j][attribute]==$scope.param.relatedAtaffData.name){
                        $scope.relatedAtaffFlag[i]= true;
                    }
                }

            }

            $scope.showOrhideRelateStaff = function(index){
                for(var i=0;i<$scope.param.relatedAtaffData.length;i++){
                    $scope.relatedAtaffFlag[i]= false;
                }
                $scope.relatedAtaffFlag[index]= !$scope.relatedAtaffFlag[index];

            }
            $scope.finsh = function(obj,attribute){
                for(var i=0;i<$scope.param.relatedAtaffData.length;i++){
                    if($scope.relatedAtaffFlag[i]==true){
                        console.log( obj[$scope.relatedAtaffIndex]);
                        obj[$scope.relatedAtaffIndex][attribute] = $scope.param.relatedAtaffData[i].name
                    }
                }
                console.log(obj[$scope.relatedAtaffIndex][attribute])
            }

        })
    };

    var mybody = document.getElementsByTagName('body')[0];
    //滑动处理

   /* var startX, startY, moveEndX, moveEndY, X, Y;
    mybody.addEventListener('touchstart', function(e) {
        startX = e.touches[0].pageX;
        startY = e.touches[0].pageY;
    });
    mybody.addEventListener('touchmove', function(e) {
        moveEndX = e.changedTouches[0].pageX;
        moveEndY = e.changedTouches[0].pageY;
        X = moveEndX - startX;
        Y = moveEndY - startY;
        console.log(12)
        if ( X > 0 ) {alert("向右");}
        else if ( X < 0 ) {alert("向左");}
        else if ( Y > 0) {alert("向下");}
        else if ( Y < 0 ) { alert("向上");}
        else{alert("没滑动"); }

    });*/


    var loadFlag = true;
    var oi = 0;
    $scope.flag={
        upDownScrollFlag : true
    }
    var mySwiper = new Swiper('.swiper-container',{
        direction: 'vertical',
        scrollbar: '.swiper-scrollbar',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true,
        onTouchStart: function(swiper){		//手动滑动中触发


        },
        onTouchMove: function(swiper,event){		//手动滑动中触发
            mySwiper.update(); // 重新计算高度;
            var _viewHeight = document.getElementsByClassName('swiper-wrapper')[0].offsetHeight;
            var _contentHeight = document.getElementsByClassName('swiper-slide')[0].offsetHeight;
            if(mySwiper.translate < 50 && mySwiper.translate > 0) {
                $(".init-loading").html('下拉刷新...').show();
            }else if(mySwiper.translate > 50 ){
                $(".init-loading").html('释放刷新...').show();
            }
        },
        onTouchEnd: function(swiper) {
            var _viewHeight = document.getElementsByClassName('swiper-wrapper')[0].offsetHeight;
            var _contentHeight = document.getElementsByClassName('swiper-slide')[0].offsetHeight;
            // 上拉加载
            if(mySwiper.translate <= _viewHeight - _contentHeight - 50 && mySwiper.translate < 0) {
                // console.log("已经到达底部！");

                if(loadFlag){
                    $(".loadtip").html('正在加载...');
                }else{
                    $(".loadtip").html('没有更多啦！');
                }

                setTimeout(function() {
                    $(".loadtip").html('上拉加载更多...');
                    mySwiper.update(); // 重新计算高度;
                }, 800);
            }

            // 下拉刷新
            if(mySwiper.translate >= 50) {
                $(".init-loading").html('正在刷新...').show();
                $(".loadtip").html('上拉加载更多');
                loadFlag = true;
                setTimeout(function() {
                    $(".refreshtip").show(0);
                    $(".init-loading").html('刷新成功！');
                    setTimeout(function(){
                        $(".init-loading").html('').hide();
                    },800);
                    $(".loadtip").show(0);

                    //刷新操作
                    mySwiper.update(); // 重新计算高度;
                }, 1000);
            }else if(mySwiper.translate >= 0 && mySwiper.translate < 50){
                $(".init-loading").html('').hide();
            }
            return false;
        }
    });
    setTimeout(function(){
        var mySwiper2 = new Swiper('.swiper-container2',{
            freeMode: true,
            onTouchStart: function(swiper){		//手动滑动中触发
                $scope.flag.upDownScrollFlag = false;
            },
            onReachEnd: function(){
                alert('到了最后一个slide');
            },

        });
    },2000)

    $('.tab a').click(function(){

        $(this).addClass('active').siblings('a').removeClass('active');
        mySwiper2.slideTo($(this).index(), 500, false)

        $('.w').css('transform', 'translate3d(0px, 0px, 0px)')
        $('.swiper-container2 .swiper-slide-active').css('height','auto').siblings('.swiper-slide').css('height','0px');
        mySwiper.update();
    });




   /* var mySwiper2 = new Swiper('.swiper-container2',{
        autoplay: 5000,

        onReachEnd: function(){
            alert('到了最后一个slide');
        },
        setTransition: function(){
            alert('wer');
        }
    })*/



    $('.tab a').click(function(){

        $(this).addClass('active').siblings('a').removeClass('active');
        mySwiper2.slideTo($(this).index(), 500, false)

        $('.w').css('transform', 'translate3d(0px, 0px, 0px)')
        $('.swiper-container2 .swiper-slide-active').css('height','auto').siblings('.swiper-slide').css('height','0px');
        mySwiper.update();
    });
    /*修改预约*/
    $scope.shopAppointServiceDTO = {
        appointStartTime:$scope.param.ModifyAppointmentObject.appointStartTime,
        appointEndTime:$scope.param.ModifyAppointmentObject.appointEndTime,
        appointPeriod:$scope.param.ModifyAppointmentObject.appointPeriod,
        detail:$scope.param.ModifyAppointmentObject.detail,
        shopProjectId:$scope.param.newProductObject.shopProjectId,
        shopProjectName:$scope.param.newProductObject.shopProjectName,
        sysUserId:$scope.param.selectCustomersObject.sysUserId,
        sysUserName:$scope.param.selectCustomersObject.sysUserName,
        sysUserPhone:$scope.param.selectCustomersObject.sysUserPhone,
        status:$scope.param.ModifyAppointmentObject.status
    }
    $scope.modifyingAppointment = function(){
        /*$scope.param.AppointmentType="长客";
         $scope.param.appointmentNew = "no";*/
        $scope.param.cancellationFlag = true;
        ngDialog.open({
            template: 'modifyingAppointment',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.cancellationFlag = true;
                GetAppointmentInfoById.get({
                    shopAppointServiceId:"id_7"
                },function(data){
                    $scope.param.ModifyAppointmentObject.ModifyAppointmentData = data.responseData;
                    $scope.param.ModifyAppointmentObject.appointStartTime =data.responseData.appointStartTime
                    $scope.param.ModifyAppointmentObject.appointEndTime=data.responseData.appointEndTime
                    $scope.param.ModifyAppointmentObject.appointPeriod=data.responseData.appointPeriod
                    $scope.param.ModifyAppointmentObject.detail=data.responseData.detail.detail
                    $scope.param.newProductObject.shopProjectId=data.responseData.shopProjectId
                    $scope.param.selectCustomersObject.sysUserId=data.responseData.sysUserId
                    $scope.param.selectCustomersObject.sysUserName=data.responseData.sysUserName
                    $scope.param.selectCustomersObject.sysUserPhone=data.responseData.sysUserPhone
                    $scope.param.ModifyAppointmentObject.status=data.responseData.status;

                    var date={
                        "startTime": "07:00",
                        "endTime": "21:00",
                        "scheduling":"20,21,22"
                    };
                    for(var i=0;i<$scope.param.code.length;i++){
                        $scope.param.ModifyAppointmentObject.hoursType[i]="0";
                        for(key in $scope.param.code[i] ){
                            for (var k = 0;k <date.scheduling.split(",").length; k++) {
                                if(date.scheduling.split(",")[k]==key){
                                    $scope.param.ModifyAppointmentObject.hoursType[i]="1";
                                }
                            }
                            $scope.param.ModifyAppointmentObject.hoursTime[i] = $scope.param.code[i][key];
                            if($scope.param.code[i][key] == date.startTime ){
                                var a= i;
                            }
                            if($scope.param.code[i][key] == date.endTime ){
                                var b= i;
                            }
                        }
                    }
                    $scope.param.ModifyAppointmentObject.hoursTimeShow=$scope.param.ModifyAppointmentObject.hoursTime.slice(a,b+1);
                    $scope.param.selectedTime = $scope.param.ModifyAppointmentObject.hoursType.slice(a,b+1);
                    console.log(1)



                });

                GetUserCardProjectList.get({
                    appointmentId:"1"
                },function(data){
                    $scope.param.individualTravelerAppointmentObj.individualTravelerAppointment = data.responseData;
                   $scope.param.ModifyAppointmentObject.productNum = data.responseData.consume.length+data.responseData.punchCard.length


                });
                $scope.close = function(status) {

                    $scope.closeThisDialog();
                };
            }],
            className: 'ngdialog-theme-default ngdialog-theme-custom'
        });

    }

    /*选择顾客*/
    $scope.selectCustomersFun = function(){
        FindArchives.get({
            queryField:"",
            pageNo:1,
            pageSize:100
        },function(data){
            $scope.param.selectCustomersObject.data=data.responseData.info;
        })
        /* {
         queryField:$scope.param.selectCustomersObject.queryField,/!*顾客查询条件，可为空*!/
         pageNo:1,
         pageSize:100
         }*//*选择顾客参数  get*/
    }
    $scope.selectCustomersCtrl = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.selectCustomersFun();
                $scope.close = function() {
                    if(status == 0){
                        /*$scope.param.selectCustomersObject.sysUserName = "";
                         $scope.param.selectCustomersObject.sysUserId = "";
                         $scope.param.selectCustomersObject.sysUserPhone = "";*/
                    }
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });
    };
    $scope.searchCustomer = function(){
        console.log($scope.param.selectCustomersObject.queryField)
        $scope.selectCustomersFun()

    }
    $scope.selectTheCustomer = function(index,sysUserName,sysUserId,sysUserPhone){
        $scope.param.ModifyAppointmentObject.customerIndex = index;
        $scope.param.selectCustomersObject.sysUserName = sysUserName;
        $scope.param.selectCustomersObject.sysUserId = sysUserId;
        $scope.param.selectCustomersObject.sysUserPhone = sysUserPhone;
        if($scope.param.ModifyAppointmentObject.productNum == "0"){
            setTimeout(function(){
                $scope.selectNewProduct();
                ngDialog.close("selectCustomersWrap")
            },800)
        }
    }



   /*预约 选择项目*/
    /*疗程卡*/

    $scope.newProjectFun = function(){
        /*{useStyle:"0",
         filterStr:$scope.param.newProductObject.filterStr  //参数
         }*/
        /*请求数据的位置*/
        GetUserProjectGroupList.get({
            cardStyle:"0",
            sysUserId:"1"
        },function(data){
            $scope.param.newProductObject.newProjectData=data.responseData;
        })


    };
    $scope.newProjectFun()
    var selfData={
        "result":"0x00001",
        "errorInfo":null,
        "responseData":[
            {
                "面部":[
                    {
                        "id":"3",
                        "sysShopId":"11",
                        "sysBossId":null,
                        "projectName":"足疗",
                        "projectTypeOneName":"足疗",
                        "projectTypeTwoName":"足疗",
                        "projectTypeOneId":"1",
                        "projectTypeTwoId":"4",
                        "productType":"1",
                        "useStyle":"1",
                        "cardType":"1",
                        "projectUrl":null,
                        "projectDuration":12,
                        "marketPrice":null,
                        "discountPrice":null,
                        "maxContainTimes":"12",
                        "visitDateTime":null,
                        "oncePrice":null,
                        "functionIntr":null,
                        "isDisplay":null,
                        "status":"0",
                        "shopProjectId":"123",
                        "createBy":null,
                        "createDate":null,
                        "updateUser":null,
                        "updateDate":null
                    },
                    {
                        "id":"4",
                        "sysShopId":"11",
                        "sysBossId":null,
                        "projectName":"面部补水",
                        "projectTypeOneName":"面部",
                        "projectTypeTwoName":"面部",
                        "projectTypeOneId":"1",
                        "projectTypeTwoId":"2",
                        "productType":"2",
                        "useStyle":"1",
                        "cardType":"1",
                        "projectUrl":null,
                        "projectDuration":"12",
                        "marketPrice":null,
                        "discountPrice":null,
                        "maxContainTimes":12,
                        "visitDateTime":null,
                        "oncePrice":null,
                        "functionIntr":null,
                        "isDisplay":null,
                        "status":"0",
                        "shopProjectId":"123",
                        "createBy":null,
                        "createDate":null,
                        "updateUser":null,
                        "updateDate":null
                    }
                ]
            }
        ]
    }
    /*本店项目*/
    $scope.selfProduct=function(){
        /*{pageNo:1,
         pageSize:100,
         filterStr:$scope.param.newProductObject.filterStr}
         */ //参数
        GetShopProjectList.get({
            filterStr:"",
            pageNo:"",
            pageSize:""
        },function(data){
            $scope.param.newProductObject.selfProductData = data.responseData;
        });
    }
    $scope.newProductSearch = function(){
        console.log($scope.param.newProductObject.filterStr)
    }
    /*选择项目*/
    $scope.selectNewProduct = function(){
        if($scope.param.selectCustomersObject.sysUserName == ""){
            $scope.selectCustomersCtrl()
        }else{
            ngDialog.open({
                template: 'newProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    /*$scope.newProjectFun();*/
                    $scope.close = function(status) {
                        if(status == 1){
                            $scope.param.newProductObject.shopProjectIdArr = [];/*Id数组*/
                            $scope.param.newProductObject.shopProjectNameArr = [];/*项目名数组*/
                            var timeLength = 0;/*项目时长*/
                            /* $scope.param.ModifyAppointmentObject.productNum = 0;/!*项目个数*!/*/
                            for(var i=0;i<$scope.param.ModifyAppointmentObject.selfProductDataFlag.length;i++){
                                if($scope.param.ModifyAppointmentObject.selfProductDataFlag[i]==true){
                                    $scope.param.newProductObject.shopProjectIdArr.push($(".selfProductDataIndex").eq(i).attr('shopProjectId'))
                                    $scope.param.newProductObject.shopProjectNameArr.push($(".selfProductDataIndex").eq(i).attr('projectName'));
                                    timeLength += $(".selfProductDataIndex").eq(i).attr('appointPeriod')/1;
                                    $scope.param.ModifyAppointmentObject.productNum ++;
                                }
                            }
                            for(var i=0;i<$scope.param.newProductObject.newProjectData.length;i++){
                                if($scope.param.ModifyAppointmentObject.newProjectDataFlag[i]==true){
                                    $scope.param.newProductObject.shopProjectIdArr.push($(".newProjectIndex").eq(i).attr('shopProjectId'))
                                    $scope.param.newProductObject.shopProjectNameArr.push($(".newProjectIndex").eq(i).attr('projectName'));
                                    timeLength += $(".newProjectIndex").eq(i).attr('appointPeriod')/1;
                                    $scope.param.ModifyAppointmentObject.productNum ++;

                                }
                            }
                            /*项目ID*/         $scope.param.newProductObject.shopProjectId=$scope.param.newProductObject.shopProjectIdArr.join(",");
                            /*项目名称*/         $scope.param.newProductObject.shopProjectName=$scope.param.newProductObject.shopProjectNameArr.join(",");
                            /*项目时长*/         $scope.param.ModifyAppointmentObject.appointPeriod = timeLength;
                            console.log($scope.param.ModifyAppointmentObject.appointPeriod)
                            ngDialog.close("selectCustomersWrap")
                        }else{
                            $scope.param.ModifyAppointmentObject.productNum = "0";
                            $scope.falseAll()
                            $scope.param.newProductObject.shopProjectId='';
                            $scope.param.newProductObject.shopProjectName=""
                        }
                        $scope.closeThisDialog();

                    };
                }],
                className: 'newProject ngdialog-theme-custom'
            });
        }


    }
    $scope.param.newProductObject.content = true;
    $scope.newProductBtn = function(index){
        $scope.param.newProductObject.index =index;
        if(index == 1){
            $scope.param.newProductObject.titleFlag = true;
            $scope.param.newProductObject.content = false;
            $scope.selfProduct();
        }else{
            $scope.param.newProductObject.titleFlag = false;
            $scope.param.newProductObject.content = true;
            $scope.newProjectFun();
        }
    };
    $scope.falseAll = function(){
        for(var i=0;i<$scope.param.newProductObject.newProjectData.length;i++){
            $scope.param.ModifyAppointmentObject.newProjectDataFlag[i] = false;
        }
        for(var i=0;i<$scope.param.newProductObject.selfProductData.length;i++){
            for(var key in $scope.param.newProductObject.selfProductData[i]){
                $scope.param.ModifyAppointmentObject.selfProductDataFlag.push(false);
            }
        }
    }
    var a = -1;
    $scope.selectTheProduct = function(index,type){

        if(type == "疗程"){
            $scope.param.ModifyAppointmentObject.newProjectDataFlag[index] =!$scope.param.ModifyAppointmentObject.newProjectDataFlag[index];

        }else{

            $scope.param.ModifyAppointmentObject.selfProductDataFlag[index] = !$scope.param.ModifyAppointmentObject.selfProductDataFlag[index];



        }


    }





    /*选择美容师*/
    $scope.selectBeautician = function(){
        if($scope.param.selectCustomersObject.sysUserName == ""){
            /*$scope.selectCustomersCtrl()*/

        }else {
            ngDialog.open({
                template: 'selectBeautician',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.param.timeLengthIndex = -1;
                    GetShopClerkList.get({
                        pageNo:1,
                        pageSize:100
                    },function(data){
                        $scope.param.timeLength = data.responseData;
                    })
                    $scope.close = function (status) {
                        if(status == 0){
                            /* $scope.param.ModifyAppointmentObject.beauticianName =""*/
                        }
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectBeautician ngdialog-theme-custom'
            });
        }
    }

/*确认预约*/
    $scope.startAppointmentIndivdual = function(){
        $scope.param.ModifyAppointment = false;
        UpdateAppointmentInfoById.get({
            shopAppointServiceId:'1',
            status:"0"
        },function(data){

        })
    };
  /*开始服务*/
    $scope.startSevier = function(){
        $scope.seriverColor=false;
        UpdateAppointmentInfoById.get({
            shopAppointServiceId:"1",
            status:"1"
        },function(data){

        })
    };

    /*排班*/
    GetShopClerkScheduleList.get({
        searchDate:"2018-04-28"
    },function(data){

    })




    detailsReservation && detailsReservation($scope, ngDialog
        ,GetUserProjectGroupList,GetUserProductList,GetUserCourseProjectList
        ,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups
        ,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject
        ,GetUserShopProjectList,GetUserShopProjectList,ConsumeCourseCard
        ,GetShopClerkList,FindArchives,GetShopProjectList);
    individualTravelerAppointment && individualTravelerAppointment($scope, ngDialog,UpdateAppointmentInfoById,FindArchives,GetShopProjectList);
    weeklyReservation && weeklyReservation($scope, ngDialog,FindArchives);
    appointmentTypeCtrl && appointmentTypeCtrl($scope, ngDialog,UpdateAppointmentInfoById,FindArchives);/*新建预约*/
    relatedStaffCtrl && relatedStaffCtrl($scope,ngDialog,GetShopClerkList,FindArchives)
});




