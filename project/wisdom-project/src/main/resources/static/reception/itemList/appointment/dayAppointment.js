PADWeb.controller("dayAppointmentCtrl", function($scope, $state, $stateParams,$filter,ngDialog/*ShopDayAppointmentInfoByDate,GetAppointmentInfoById*/)
{

    $scope.date = $filter("date")(Date.parse(new Date()), "yyyy-MM-dd");
    $scope.param = {
        week: [],
        btnActive: ['btnActive', 'common'],
        code: [{"0": "00:00"}, {"1": "00:30"}, {"2": "01:00"}, {"3": "01:30"}, {"4": "02:00"}, {"5": "02:30"}, {"6": "03:00"}, {"7": "03:30"}, {"8": "04:00"}, {"9": "04:30"}, {"10": "05:00"}, {"11": "05:30"}, {"12": "06:00"}, {"13": "06:30"}, {"14": "07:00"}, {"15": "07:30"}, {"16": "08:00"}, {"17": "08:30"}, {"18": "09:00"}, {"19": "09:30"}, {"20": "10:00"}, {"21": "10:30"}, {"22": "11:00"}, {"23": "11:30"}, {"24": "12:00"}, {"25": "12:30"}, {"26": "13:00"}, {"27": "13:30"}, {"28": "14:00"}, {"29": "14:30"}, {"30": "15:00"}, {"31": "15:30"}, {"32": "16:00"}, {"33": "16:30"}, {"34": "17:00"}, {"35": "17:30"}, {"36": "18:00"}, {"37": "18:30"}, {"38": "19:00"}, {"39": "19:30"}, {"40": "20:00"}, {"41": "20:30"}, {"42": "21:00"}, {"43": "21:30"}, {"44": "22:00"}, {"45": "22:30"}, {"46": "23:00"}, {"47": "23:30"}],
        serachContent: '', /*搜索内容*/
        givingIndex: 0, /*赠送Index*/
        AppointmentType: "散客",
        dayFlag: true,
        weekFlag: false,
        newProductObject: {
            index: 0,
            titleFlag: false,
        },
        timeLengthObject: {/*修改预约-时长对象*/
            time: ''
        },
        appointmentObject: {/*日预约对象*/
            appointmentDate: '',
            beautician: [],/*美容师*/
            appointmentInfo: [],/*美容师下的预约列表*/
            point: [],/*点*/
            list:[],/*数据处理后 -- */
        },
        week:{
            weekData:''
        },
        details:"",/*预约详情*/
        selectBeautician: false, /*修改预约-选择美容师*/
        detailsReservationText: "去消费", /*详情按钮文字*/
        num: 1,
        data: [{
            title: '合作',
            content: ['全部', '整形系列']
        }, {
            title: '面部',
            content: ['全部', '保湿', '美白', '补水', '植萃', '抗皱']
        },
            {
                title: '眼部',
                content: ['全部', '保湿']
            }, {
                title: 'SPA',
                content: ['全部', '保湿']
            }],
        day:[],/*用于寻找预约颜色的其中一个数据*/
        day:[],/*侧边时间循环*/
    };
    $scope.time = function (time) {
        console.log(1)
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
    $scope.appointmentChange = function (type) {
        if (type == "week") {
            $scope.arrTime = $scope.param.week;
            $scope.param.btnActive[0] = 'common';
            $scope.param.btnActive[1] = 'btnActive';
            $scope.param.weekFlag = true;
            $scope.param.dayFlag = false;
        } else {
            $scope.arrTime = $scope.param.day;
            $scope.param.btnActive[0] = 'btnActive';
            $scope.param.btnActive[1] = 'common';
            $scope.param.weekFlag = false;
            $scope.param.dayFlag = true;
        }
    };


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
    }
    detailsReservation && detailsReservation($scope, ngDialog);
    individualTravelerAppointment && individualTravelerAppointment($scope, ngDialog);
    weeklyReservation && weeklyReservation($scope, ngDialog);


    /*长按新建*/
    $scope.onHold = function (num) {
        console.log(num);
    };

    $scope.detailsWrap = function (index1, index2, type) {
        if(type==0)return
        if (type != 3) {
            $scope.ngDialog = ngDialog;
            ngDialog.open({
                template: 'detailsWrap',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    if (type == 1) {
                        $scope.param.detailsReservationText = "去消费";
                    } else {
                        $scope.param.detailsReservationText = "去划卡";
                    }
                   /* GetAppointmentInfoById.get({
                        shopAppointServiceId: "id_7"
                    }, function (data) {
                        // ManagementUtil.checkResponseData(data,"");
                        if (data.result == Global.SUCCESS) {

                        }
                    })*/
                    $scope.close = function () {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',

            });
        } else {
            $scope.ngDialog = ngDialog;
            ngDialog.open({
                template: 'individual',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function () {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'individual ngdialog-theme-custom'
            });
        }

    };
    var data = {
        "result": "0x00001",
        "errorInfo": null,
        "responseData": {
            "安迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "王",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "1",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "六",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": "新",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "B迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "用户名称_1",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "2",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "用户名称_2",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '3',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": null,
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '1',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "C迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_3",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "用户名称_1",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "3",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "用户名称_2",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '1',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": null,
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "d迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "王",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "1",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "六",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": "新",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "f迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "王",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "1",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "六",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": "新",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "g迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "王",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "1",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "六",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": "新",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "h迪": {
                "appointmentInfo": [{
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522825200000,
                    "updateDate": 1522819014000,
                    "sysUserName": "王",
                    "updateUser": "1",
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_1",
                    "appointPeriod": 60,
                    "createBy": "1",
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "30,31",
                    "shopProjectName": "项目名称_1",
                    "id": "id_1",
                    "sysUserId": "用户表主键_1",
                    "detail": "1",
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522828800000,
                    "sysBossId": "老板表主键_1",
                    "status": "1",
                    "createDate": 1522819008000
                }, {
                    "sysClerkName": "安迪_2",
                    "appointStartTime": 1522828800000,
                    "updateDate": null,
                    "sysUserName": "六",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": "18810123938",
                    "shopProjectId": "项目表主键_2",
                    "appointPeriod": 60,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "32,33",
                    "shopProjectName": "项目名称_2",
                    "id": "id_2",
                    "sysUserId": "用户表主键_2",
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522832400000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522840937000
                }, {
                    "sysClerkName": "安迪_1",
                    "appointStartTime": 1522836000000,
                    "updateDate": null,
                    "sysUserName": "新",
                    "updateUser": null,
                    "sysClerkId": "1",
                    "sysUserPhone": null,
                    "shopProjectId": "项目表主键_3",
                    "appointPeriod": null,
                    "createBy": null,
                    "serialVersionUID": 1,
                    "sysShopId": "3",
                    "scheduling": "36,37",
                    "shopProjectName": "项目名称_3",
                    "id": "id_3",
                    "sysUserId": null,
                    "detail": null,
                    "sysShopName": "汉方美容店_1",
                    "appointEndTime": 1522839600000,
                    "sysBossId": "老板表主键_1",
                    "status": '2',
                    "createDate": 1522843224000
                }], "point": 3
            },
            "startTime": "07:00",
            "endTime": "21:00"
        }
    }

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
        for (var e = 0; e < $scope.param.day.length; e++) {
            $scope.param.appointmentObject.list[i].status[e]=0;
            $scope.param.appointmentObject.list[i].sysUserName[e]=null;
            $scope.param.appointmentObject.list[i].shopProjectName[e]=null;
            $scope.param.appointmentObject.list[i].time[e]=null;
            for(var j=0;j<$scope.param.appointmentObject.appointmentInfo[i].length;j++){
                for (var k = 0;k < $scope.param.appointmentObject.appointmentInfo[i][j].scheduling.split(",").length; k++) {
                    if ($scope.param.appointmentObject.appointmentInfo[i][j].scheduling.split(",")[k] == objTemp($scope.param.day[e])) {
                        $scope.param.appointmentObject.list[i].sysUserName[e] = $scope.param.appointmentObject.appointmentInfo[i][j].sysUserName;
                        $scope.param.appointmentObject.list[i].shopProjectName[e] = $scope.param.appointmentObject.appointmentInfo[i][j].shopProjectName;
                        $scope.param.appointmentObject.list[i].time[e] = $scope.param.days[e];

                        if($scope.param.appointmentObject.appointmentInfo[i][j].status == 1){
                            $scope.param.appointmentObject.list[i].status[e] = 1
                        } else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 2){
                            $scope.param.appointmentObject.list[i].status[e] = 2
                        }else if($scope.param.appointmentObject.appointmentInfo[i][j].status == 3){
                            $scope.param.appointmentObject.list[i].status[e] = 3
                        }
                    }
                }
            }
        }
    }

    function objTemp(obj) {
        for (key  in obj) {
            return key
        }
    }
    /*周*/
  var weekData = {
      "result": "0x00001",
      "errorInfo": null,
      "responseData": {
          "安迪": [{
              "week": "星期一",
              "Lunar": "十七",
              "day": "02",
              "info": ""
          }, {
              "week": "星期二",
              "Lunar": "十八",
              "day": "03",
              "info": ""
          }, {
              "week": "星期三",
              "Lunar": "十九",
              "day": "04",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": [{
                  "id": "id_7",
                  "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
                  "appointStartTime": 1524476446488,
                  "appointEndTime": 1522897200000,
                  "appointPeriod": 60,
                  "sysUserId": "bbc890bada834995ba814fdfc415e38d",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "efa9b254f8774016ac4f112854681848",
                  "createDate": 1522900800000,
                  "updateUser": "90329b53f9764df684ffddfb37e40667",
                  "updateDate": null
              }]
          }, {
              "week": "星期六",
              "Lunar": "廿二",
              "day": "07",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": [{
                  "id": "id_8",
                  "shopProjectId": "d01ee5d5447d42a3bc3b028ff0232f99",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "d314b70d2c6c4496ac11b594aa926765",
                  "appointStartTime": 1524476509691,
                  "appointEndTime": 1523156400000,
                  "appointPeriod": 60,
                  "sysUserId": "73fa4810bb12479ab8423630a3e0aafe",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "b49c0f3cb63e46c081ef458113a2106f",
                  "createDate": 1523160000000,
                  "updateUser": "8184691336ee4d39b0b53edb62572681",
                  "updateDate": null
              }]
          }, {
              "week": "星期二",
              "Lunar": "廿五",
              "day": "10",
              "info": ""
          }],
          "B安迪": [{
              "week": "星期一",
              "Lunar": "十七",
              "day": "02",
              "info": ""
          }, {
              "week": "星期二",
              "Lunar": "十八",
              "day": "03",
              "info": ""
          }, {
              "week": "星期三",
              "Lunar": "十九",
              "day": "04",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": [{
                  "id": "id_7",
                  "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
                  "appointStartTime": 1524476446488,
                  "appointEndTime": 1522897200000,
                  "appointPeriod": 60,
                  "sysUserId": "bbc890bada834995ba814fdfc415e38d",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "efa9b254f8774016ac4f112854681848",
                  "createDate": 1522900800000,
                  "updateUser": "90329b53f9764df684ffddfb37e40667",
                  "updateDate": null
              }]
          }, {
              "week": "星期六",
              "Lunar": "廿二",
              "day": "07",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": [{
                  "id": "id_8",
                  "shopProjectId": "d01ee5d5447d42a3bc3b028ff0232f99",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "d314b70d2c6c4496ac11b594aa926765",
                  "appointStartTime": 1524476509691,
                  "appointEndTime": 1523156400000,
                  "appointPeriod": 60,
                  "sysUserId": "73fa4810bb12479ab8423630a3e0aafe",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "b49c0f3cb63e46c081ef458113a2106f",
                  "createDate": 1523160000000,
                  "updateUser": "8184691336ee4d39b0b53edb62572681",
                  "updateDate": null
              }]
          }, {
              "week": "星期二",
              "Lunar": "廿五",
              "day": "10",
              "info": ""
          }],
          "C安迪": [{
              "week": "星期一",
              "Lunar": "十七",
              "day": "02",
              "info": ""
          }, {
              "week": "星期二",
              "Lunar": "十八",
              "day": "03",
              "info": ""
          }, {
              "week": "星期三",
              "Lunar": "十九",
              "day": "04",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": [{
                  "id": "id_7",
                  "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
                  "appointStartTime": 1524476446488,
                  "appointEndTime": 1522897200000,
                  "appointPeriod": 60,
                  "sysUserId": "bbc890bada834995ba814fdfc415e38d",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "efa9b254f8774016ac4f112854681848",
                  "createDate": 1522900800000,
                  "updateUser": "90329b53f9764df684ffddfb37e40667",
                  "updateDate": null
              }]
          }, {
              "week": "星期六",
              "Lunar": "廿二",
              "day": "07",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": [{
                  "id": "id_8",
                  "shopProjectId": "d01ee5d5447d42a3bc3b028ff0232f99",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "d314b70d2c6c4496ac11b594aa926765",
                  "appointStartTime": 1524476509691,
                  "appointEndTime": 1523156400000,
                  "appointPeriod": 60,
                  "sysUserId": "73fa4810bb12479ab8423630a3e0aafe",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "b49c0f3cb63e46c081ef458113a2106f",
                  "createDate": 1523160000000,
                  "updateUser": "8184691336ee4d39b0b53edb62572681",
                  "updateDate": null
              }]
          }, {
              "week": "星期二",
              "Lunar": "廿五",
              "day": "10",
              "info": ""
          }],
          "D安迪": [{
              "week": "星期一",
              "Lunar": "十七",
              "day": "02",
              "info": ""
          }, {
              "week": "星期二",
              "Lunar": "十八",
              "day": "03",
              "info": ""
          }, {
              "week": "星期三",
              "Lunar": "十九",
              "day": "04",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": [{
                  "id": "id_7",
                  "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
                  "appointStartTime": 1524476446488,
                  "appointEndTime": 1522897200000,
                  "appointPeriod": 60,
                  "sysUserId": "bbc890bada834995ba814fdfc415e38d",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "efa9b254f8774016ac4f112854681848",
                  "createDate": 1522900800000,
                  "updateUser": "90329b53f9764df684ffddfb37e40667",
                  "updateDate": null
              }]
          }, {
              "week": "星期六",
              "Lunar": "廿二",
              "day": "07",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": [{
                  "id": "id_8",
                  "shopProjectId": "d01ee5d5447d42a3bc3b028ff0232f99",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "d314b70d2c6c4496ac11b594aa926765",
                  "appointStartTime": 1524476509691,
                  "appointEndTime": 1523156400000,
                  "appointPeriod": 60,
                  "sysUserId": "73fa4810bb12479ab8423630a3e0aafe",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "b49c0f3cb63e46c081ef458113a2106f",
                  "createDate": 1523160000000,
                  "updateUser": "8184691336ee4d39b0b53edb62572681",
                  "updateDate": null
              }]
          }, {
              "week": "星期二",
              "Lunar": "廿五",
              "day": "10",
              "info": ""
          }],
          "F安迪": [{
              "week": "星期一",
              "Lunar": "十七",
              "day": "02",
              "info": ""
          }, {
              "week": "星期二",
              "Lunar": "十八",
              "day": "03",
              "info": ""
          }, {
              "week": "星期三",
              "Lunar": "十九",
              "day": "04",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": ""
          }, {
              "week": "星期四",
              "Lunar": "廿十",
              "day": "05",
              "info": [{
                  "id": "id_7",
                  "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
                  "appointStartTime": 1524476446488,
                  "appointEndTime": 1522897200000,
                  "appointPeriod": 60,
                  "sysUserId": "bbc890bada834995ba814fdfc415e38d",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "efa9b254f8774016ac4f112854681848",
                  "createDate": 1522900800000,
                  "updateUser": "90329b53f9764df684ffddfb37e40667",
                  "updateDate": null
              }]
          }, {
              "week": "星期六",
              "Lunar": "廿二",
              "day": "07",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": ""
          }, {
              "week": "星期日",
              "Lunar": "廿三",
              "day": "08",
              "info": [{
                  "id": "id_8",
                  "shopProjectId": "d01ee5d5447d42a3bc3b028ff0232f99",
                  "shopProjectName": "面部保洁",
                  "sysShopId": "3",
                  "sysShopName": "汉方美容院",
                  "sysClerkId": "1",
                  "sysClerkName": "王五",
                  "sysBossId": "d314b70d2c6c4496ac11b594aa926765",
                  "appointStartTime": 1524476509691,
                  "appointEndTime": 1523156400000,
                  "appointPeriod": 60,
                  "sysUserId": "73fa4810bb12479ab8423630a3e0aafe",
                  "sysUserName": "张欢",
                  "sysUserPhone": "181812839893",
                  "status": "0",
                  "detail": "测试",
                  "createBy": "b49c0f3cb63e46c081ef458113a2106f",
                  "createDate": 1523160000000,
                  "updateUser": "8184691336ee4d39b0b53edb62572681",
                  "updateDate": null
              }]
          }, {
              "week": "星期二",
              "Lunar": "廿五",
              "day": "10",
              "info": ""
          }]
      }
  }
   $scope.param.week.weekData = weekData.responseData;
  /*预约详情*/
  var details = {
    "result": "0x00001",
        "errorInfo": null,
        "responseData": {
        "id": "id_7",
            "shopProjectId": "6af580ecaf6e43698f1a9fa0333aad89",
            "shopProjectName": "面部保洁",
            "sysShopId": "3",
            "sysShopName": "汉方美容院",
            "sysClerkId": "1",
            "sysClerkName": "王五",
            "sysBossId": "963290b846694a21b5c3409cff0ef8a3",
            "appointStartTime": 1524476446488,
            "appointEndTime": 1522897200000,
            "appointPeriod": 60,
            "sysUserId": "bbc890bada834995ba814fdfc415e38d",
            "sysUserName": "张欢",
            "sysUserPhone": "181812839893",
            "status": "0",
            "detail": "测试",
            "createBy": "efa9b254f8774016ac4f112854681848",
            "createDate": 1522900800000,
            "updateUser": "90329b53f9764df684ffddfb37e40667",
            "updateDate": null
    }
}
    $scope.param.week.details = details.responseData;
    console.log(details)

});




