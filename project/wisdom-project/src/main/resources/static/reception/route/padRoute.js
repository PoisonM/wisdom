var PADWeb = angular.module('app', ['angularFileUpload', 'ui.router', 'ngDialog', 'oc.lazyLoad', 'ngResource', 'ngSanitize',"ngTouch"]);

PADWeb.config(["$provide", "$compileProvider", "$controllerProvider", "$filterProvider",
    function($provide, $compileProvider, $controllerProvider, $filterProvider) {
        PADWeb.controller = $controllerProvider.register;
        PADWeb.directive = $compileProvider.directive;
        PADWeb.filter = $filterProvider.register;
        PADWeb.factory = $provide.factory;
        PADWeb.service = $provide.service;
        PADWeb.constant = $provide.constant;
    }
]);

PADWeb.config(function($httpProvider, $stateProvider, $urlRouterProvider) {
    var root = 'itemList/';
    $stateProvider
        .state('pad-web', {
            url: '/pad-web',
            templateUrl: root + 'top_bottom.html',
            controller: 'mallCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "头部底部",
                        files: [root + "top_bottom.js",
                            root + "top_bottom.css",
                        ]
                    })
                }]
            }
        })

        .state('pad-web.left_nav', {
            url: '/left_nav',
            templateUrl: root + 'left_nav.html',
            controller: 'left_navCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "侧边导航",
                        files: [root + "left_nav.js",
                            root + "left_nav.css",
                        ]
                    })
                }]
            }
        })

        .state('pad-web.left_nav.demo', {
            url: '/demo',
            templateUrl: root + '/demo/demo.html',
            controller: 'demoCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "demo",
                        files: [root + "demo/demo.js",
                            root + "demo/include.js",
                            root + "demo/include.css",
                            root + "demo/demo.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.login', {
            url: '/login',
            templateUrl: root + '/login/login.html',
            controller: 'loginCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "登 录",
                        files: [
                            root + "login/login.css",
                            root + "login/login.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo', {
            url: '/userInfo',
            templateUrl: root + '/userInfo/userInfo.html',
            controller: 'userInfoCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "个人中心",
                        files: [
                            root + "userInfo/userInfo.css",
                            root + "userInfo/userInfo.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.todayPerformance', {
            url: '/todayPerformance',
            templateUrl: root + '/userInfo/todayPerformance.html',
            controller: 'todayPerformanceCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "今日业绩",
                        files: [
                            root + "userInfo/todayPerformance.css",
                            root + "userInfo/todayPerformance.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.rechargeRecord', {
            url: '/rechargeRecord',
            templateUrl: root + '/userInfo/rechargeRecord.html',
            controller: 'rechargeRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值记录",
                        files: [
                            root + "userInfo/rechargeRecord.css",
                            root + "userInfo/rechargeRecord.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.repaymentRecord', {
            url: '/repaymentRecord',
            templateUrl: root + '/userInfo/repaymentRecord.html',
            controller: 'repaymentRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "还款记录",
                        files: [
                            root + "userInfo/repaymentRecord.css",
                            root + "userInfo/repaymentRecord.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.stampCardRecord', {
            url: '/stampCardRecord',
            templateUrl: root + '/userInfo/stampCardRecord.html',
            controller: 'stampCardRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡记录",
                        files: [
                            root + "userInfo/stampCardRecord.css",
                            root + "userInfo/stampCardRecord.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.unclaimedAll', {
            url: '/unclaimedAll',
            templateUrl: root + '/userInfo/unclaimedAll.html',
            controller: 'unclaimedAllCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "待领取汇总",
                        files: [
                            root + "userInfo/unclaimedAll.css",
                            root + "userInfo/unclaimedAll.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.unclaimedAllClient', {
            url: '/unclaimedAllClient',
            templateUrl: root + '/userInfo/unclaimedAllClient.html',
            controller: 'unclaimedAllClientCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "待领取汇总客户",
                        files: [
                            root + "userInfo/unclaimedAllClient.css",
                            root + "userInfo/unclaimedAllClient.js",
                        ]
                    })
                }]
            }
        })


        .state('pad-web.left_nav.addRecord', {
            url: '/addRecord',
            templateUrl: root + '/addRecord/addRecord.html',
            controller: 'addRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "添加档案",
                        files: [
                            root + "addRecord/addRecord.css",
                            root + "addRecord/addRecord.js",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.personalFile', {
            url: '/personalFile',
            templateUrl: root + '/cashier/personalFile.html',
            controller: 'personalFileCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "个人档案",
                        files: [root + "cashier/personalFileCtrl.js",
                            root + "cashier/personalFile.css",
                            root + "cashier/basicInfo.css",
                            root + "cashier/basicInfo.js",
                        ]
                    })
                }]
            }
        })

        .state('pad-web.left_nav.appointment', {
            url: '/appointmentLis',
            templateUrl: root + '/appointment/appointmentLis.html',
            controller: 'appointmentLisCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "预约列表",
                        files: [root + "appointment/appointmentLis.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.collectionCard', {
            url: '/collectionCard',
            templateUrl: root + '/appointment/collectionCard.html',
            controller: 'collectionCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择套卡",
                        files: [root + "appointment/collectionCard.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.consumption', {
            url: '/consumption',
            templateUrl: root + '/appointment/consumption.html',
            controller: 'consumptionCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费",
                        files: [root + "appointment/consumptionCtrl.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.giving', {
            url: '/giving',
            templateUrl: root + '/appointment/giving.html',
            controller: 'givingCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择赠送",
                        files: [root + "appointment/giving.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.relatedStaff', {
            url: '/relatedStaff',
            templateUrl: root + '/appointment/relatedStaff.html',
            controller: 'relatedStaffCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "关联员工",
                        files: [root + "appointment/relatedStaff.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.scratchCard', {
            url: '/scratchCard',
            templateUrl: root + '/appointment/scratchCard.html',
            controller: 'scratchCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡",
                        files: [root + "appointment/scratchCard.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.modifyingAppointment', {
            url: '/modifyingAppointment',
            templateUrl: root + '/appointment/modifyingAppointment.html',
            controller: 'modifyingAppointmentCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "修改预约",
                        files: [root + "appointment/modifyingAppointmentCtrl.js",
                            root + "appointment/postion.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.addCustomers', {
            url: '/addCustomers',
            templateUrl: root + '/appointment/addCustomers.html',
            controller: 'addCustomersCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "添加顾客",
                        files: [root + "appointment/addCustomersCtrl.js",
                            root + "appointment/addCustomers.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.detailsReservation', {
            url: '/detailsReservation',
            templateUrl: root + '/appointment/detailsReservation.html',
            controller: 'detailsReservationCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "预约详情",
                        files: [root + "appointment/detailsReservation.js",
                            root + "appointment/detailsReservation.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectSingle', {
            url: '/selectSingle',
            templateUrl: root + '/appointment/selectSingle.html',
            controller: 'selectSingleCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费-选择单次",
                        files: [root + "appointment/selectSingle.js",
                            root + "appointment/selectSingle.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.CtreatmentCard', {
            url: '/CtreatmentCard',
            templateUrl: root + '/appointment/CtreatmentCard.html',
            controller: 'CtreatmentCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡-选择疗程卡",
                        files: [root + "appointment/CtreatmentCard.js",
                            root + "appointment/style.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectProduct', {
            url: '/selectProduct',
            templateUrl: root + '/appointment/selectProduct.html',
            controller: 'selectProductCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费-选择产品",
                        files: [root + "appointment/selectProduct.js",
                            root + "appointment/selectProduct.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectCustomers', {
            url: '/selectCustomers',
            templateUrl: root + '/appointment/selectCustomers.html',
            controller: 'selectCustomersCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择顾客",
                        files: [root + "appointment/selectCustomersCtrl.js",
                            root + "appointment/selectCustomers.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.customerSignature', {
            url: '/customerSignature',
            templateUrl: root + '/appointment/customerSignature.html',
            controller: 'customerSignatureCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "顾客签字",
                        files: [root + "appointment/customerSignatureCtrl.js",
                            root + "appointment/customerSignature.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectCoupons', {
            url: '/selectCoupons',
            templateUrl: root + '/appointment/selectCoupons.html',
            controller: 'selectCouponsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择优惠券",
                        files: [root + "appointment/selectCouponsCtrl.js",
                            root + "appointment/selectCoupons.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectTreatmentCard', {
            url: '/selectTreatmentCard',
            templateUrl: root + '/appointment/selectTreatmentCard.html',
            controller: 'selectTreatmentCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费-选择产品",
                        files: [root + "appointment/selectTreatmentCard.js",
                            root + "appointment/selectTreatmentCard.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.weeklyReservation', {
            url: '/weeklyReservation',
            templateUrl: root + '/appointment/weeklyReservation.html',
            controller: 'weeklyReservationCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "周预约",
                        files: [root + "appointment/weeklyReservationCtrl.js",
                            root + "appointment/weeklyReservation.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.project', {
            url: '/project',
            templateUrl: root + '/priceList/project.html',
            controller: 'projectCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "项目",
                        files: [root + "priceList/projectCtrl.js",
                            root + "priceList/project.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.projectDetails', {
            url: '/projectDetails',
            templateUrl: root + '/priceList/projectDetails.html',
            controller: 'projectDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "项目详情",
                        files: [root + "priceList/projectDetailsCtrl.js",
                            root + "priceList/projectDetails.css",
                            root + "../libs/swiper-3.4.0.min.js",
                            root + "../styles/swiper-3.4.0.min.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.product', {
            url: '/product',
            templateUrl: root + '/priceList/product.html',
            controller: 'productCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "产品",
                        files: [root + "priceList/productCtrl.js",
                            root + "priceList/product.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.productDetails', {
            url: '/productDetails',
            templateUrl: root + '/priceList/productDetails.html',
            controller: 'productDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "产品详情",
                        files: [root + "priceList/productDetailsCtrl.js",
                            root + "priceList/productDetails.css",
                            root + "../libs/swiper-3.4.0.min.js",
                            root + "../styles/swiper-3.4.0.min.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.cardDetails', {
            url: '/cardDetails',
            templateUrl: root + '/priceList/cardDetails.html',
            controller: 'cardDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡详情",
                        files: [root + "priceList/cardDetailsCtrl.js",
                            root + "priceList/cardDetails.css",
                            root + "../libs/swiper-3.4.0.min.js",
                            root + "../styles/swiper-3.4.0.min.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.rechargeableCard', {
            url: '/rechargeableCard',
            templateUrl: root + '/priceList/rechargeableCard.html',
            controller: 'rechargeableCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡页面",
                        files: [root + "priceList/rechargeableCardCtrl.js",
                            root + "priceList/rechargeableCard.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.rechargeableDetails', {
            url: '/rechargeableDetails',
            templateUrl: root + '/priceList/rechargeableDetails.html',
            controller: 'rechargeableDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡详情",
                        files: [root + "priceList/rechargeableDetailsCtrl.js",
                            root + "priceList/rechargeableDetails.css",
                            root + "../libs/swiper-3.4.0.min.js",
                            root + "../styles/swiper-3.4.0.min.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.dayAppointment', {
            url: '/dayAppointment',
            templateUrl: root + '/appointment/dayAppointment.html',
            controller: 'dayAppointmentCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "日预约",
                        files: [root + "appointment/dayAppointment.js",
                            root + "appointment/laydate.css",
                            root + "appointment/laydate.js",
                            root + "appointment/dayAppointment.css",
                            root + "appointment/consumptionCtrl.js",
                            root + "appointment/style.css",
                            root + "appointment/detailsReservation.css",
                            root + "appointment/detailsReservationCtrl.js",
                            root + "appointment/appointmentType.css",
                            root + "appointment/appointmentTypeCtrl.js",
                            root + "appointment/selectSingleCtrl.js",
                            root + "appointment/search.css",
                            root + "appointment/searchCtrl.js",
                            root + "appointment/selectTreatmentCard.css",
                            root + "appointment/selectSingle.css",
                            root + "appointment/selectSingleCtrl.js",
                            root + "appointment/selectProduct.css",
                            root + "appointment/payType.css",
                            root + "appointment/payTypeCtrl.js",
                            root + "appointment/balancePrepaid.css",
                            root + "appointment/givingCtrl.js",
                            root + "appointment/scratchCardCtrl.js",
                            root + "appointment/individualTravelerAppointment.css",
                            root + "appointment/individualTravelerAppointmentCtrl.js",
                            root + "appointment/postion.css",
                            root + "appointment/modifyingAppointmentCtrl.js",
                            root + "appointment/weeklyReservationCtrl.js",
                            root + "appointment/weeklyReservation.css",
                            root + "appointment/selectCustomers.css",
                            root + "appointment/selectCustomersCtrl.js",
                            root + "appointment/addCustomers.css",
                            root + "appointment/addCustomersCtrl.js",
                            root + "appointment/newProject.css",
                            root + "appointment/newProjectCtrl.js",
                            root + "appointment/timeLength.css",
                            root + "appointment/timeLengthCtrl.js",
                            root + "appointment/selectCouponsCtrl.js",
                            root + "appointment/selectCoupons.css",
                            root + "appointment/balancePrepaidCtrl.js",
                            root + "appointment/relatedStaffCtrl.js",
                            root+"appointment/dropload.css",
                            root + "../libs/angular-touch.js",
                            root + "../libs/dropload.js",
                            root + "../libs/zepto.min.js",

                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.appointmentType', {
            url: '/appointmentType',
            templateUrl: root + '/appointment/appointmentType.html',
            controller: 'appointmentTypeCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "预约类型",
                        files: [root + "appointment/appointmentTypeCtrl.js",
                            root + "appointment/appointmentType.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.payType', {
            url: '/payType',
            templateUrl: root + '/appointment/payType.html',
            controller: 'payTypeCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费-消费-下一步",
                        files: [root + "appointment/payTypeCtrl.js",
                            root + "appointment/appointmentType.css",
                            root + "appointment/payType.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.individualTravelerAppointment', {
            url: '/individualTravelerAppointment',
            templateUrl: root + '/appointment/individualTravelerAppointment.html',
            controller: 'individualTravelerAppointmentCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "散客-预约详情",
                        files: [root + "appointment/individualTravelerAppointmentCtrl.js",
                            root + "appointment/appointmentType.css",
                            root + "appointment/individualTravelerAppointment.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.timeLength', {
            url: '/timeLength',
            templateUrl: root + '/appointment/timeLength.html',
            controller: 'timeLengthCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "时长",
                        files: [root + "appointment/timeLengthCtrl.js",
                            root + "appointment/appointmentType.css",
                            root + "appointment/timeLength.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.newProject', {
            url: '/newProject',
            templateUrl: root + '/appointment/newProject.html',
            controller: 'newProjectCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "新建预约-选择项目",
                        files: [root + "appointment/newProjectCtrl.js",
                            root + "appointment/appointmentType.css",
                            root + "appointment/newProject.css",
                        ]
                    })
                }]
            }
        })
        .state('pad-web.attendance', {
            url: '/attendance',
            templateUrl: root + '/attendance/attendance.html',
            controller: 'attendanceCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "新建预约-选择项目",
                        files: [root + "attendance/attendanceCtrl.js",
                            root + "./appointment/appointmentType.css",
                            root + "attendance/attendance.css",
                        ]
                    })
                }]
            }
        })
    ;

    $urlRouterProvider.otherwise('pad-web/left_nav/demo');
    $httpProvider.interceptors.push('httpInterceptor');
});