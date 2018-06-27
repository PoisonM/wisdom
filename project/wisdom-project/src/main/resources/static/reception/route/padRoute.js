var PADWeb = angular.module('app', ['angularFileUpload', 'ui.router', 'ngDialog', 'oc.lazyLoad', 'ngResource', 'ngSanitize', "ngTouch"]);
// var version = "1.0."+Math.random()+""
var version = "1.0"
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
                        files: [root + "top_bottom.js?version=" + version,
                            root + "top_bottom.css?version=" + version,
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
                        files: [root + "left_nav.js?version=" + version,
                            root + "left_nav.css?version=" + version,
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
                        files: [root + "demo/demo.js?version=" + version,
                            root + "demo/include.js?version=" + version,
                            root + "demo/include.css?version=" + version,
                            root + "demo/demo.css?version=" + version,
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
                            root + "login/login.css?version=" + version,
                            root + "login/login.js?version=" + version,
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
                            root + "userInfo/userInfo.css?version=" + version,
                            root + "userInfo/userInfo.js?version=" + version,
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
                            root + "userInfo/todayPerformance.css?version=" + version,
                            root + "userInfo/todayPerformance.js?version=" + version,
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
                            root + "userInfo/rechargeRecord.css?version=" + version,
                            root + "userInfo/cardRecords.css?version=" + version,
                            root + "userInfo/rechargeRecord.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.consumptionRecord', {
            url: '/consumptionRecord',
            templateUrl: root + '/userInfo/consumptionRecord.html',
            controller: 'consumptionRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费记录",
                        files: [
                            root + "userInfo/consumptionRecord.css?version=" + version,
                            root + "userInfo/consumptionRecord.js?version=" + version,
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
                            root + "userInfo/repaymentRecord.css?version=" + version,
                            root + "userInfo/repaymentRecord.js?version=" + version,
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
                            root + "userInfo/stampCardRecord.css?version=" + version,
                            root + "userInfo/stampCardRecord.js?version=" + version,
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
                            root + "userInfo/unclaimedAll.css?version=" + version,
                            root + "userInfo/unclaimedAll.js?version=" + version,
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
                            root + "userInfo/unclaimedAllClient.css?version=" + version,
                            root + "userInfo/unclaimedAllClient.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.usingHelp', {
            url: '/usingHelp',
            templateUrl: root + '/userInfo/usingHelp.html',
            controller: 'usingHelpCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "使用帮助",
                        files: [
                            root + "userInfo/usingHelp.css?version=" + version,
                            root + "userInfo/usingHelp.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.feedback', {
            url: '/feedback',
            templateUrl: root + '/userInfo/feedback.html',
            controller: 'feedbackCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "意见反馈",
                        files: [
                            root + "userInfo/feedback.css?version=" + version,
                            root + "userInfo/feedback.js?version=" + version,
                        ]
                    })
                }]
            }
        })

        .state('pad-web.userInfo.modificationData', {
            url: '/modificationData',
            templateUrl: root + '/userInfo/modificationData.html',
            controller: 'modificationDataCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "修改资料",
                        files: [
                            root + "userInfo/modificationData.css?version=" + version,
                            root + "userInfo/modificationData.js?version=" + version,
                        ]
                    })
                }]
            }
        })


        .state('pad-web.left_nav.addRecord', {
            url: '/addRecord/:id',
            templateUrl: root + '/addRecord/addRecord.html',
            controller: 'addRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "添加档案",
                        files: [
                            root + "addRecord/addRecord.css?version=" + version,
                            root + "addRecord/addRecord.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.addRecordDetail', {
            url: '/addRecordDetail/:id',
            templateUrl: root + '/addRecord/addRecordDetail.html',
            controller: 'addRecordDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "档案详情",
                        files: [
                            root + "addRecord/addRecordDetail.css?version=" + version,
                            root + "addRecord/addRecordDetail.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.bindMember', {
            url: '/bindMember/:shopId/:userId',
            templateUrl: root + '/addRecord/bindMember.html',
            controller: 'bindMemberCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "绑定用户",
                        files: [
                            root + "addRecord/bindMember.css?version=" + version,
                            root + "addRecord/bindMember.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.personalFile', {
            url: '/personalFile/:id/:shopid/:sysShopId/:sysUserId',
            templateUrl: root + '/cashier/personalFile.html',
            controller: 'personalFileCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "个人档案",
                        files: [root + "cashier/personalFileCtrl.js?version=" + version,
                            root + "cashier/personalFile.css?version=" + version,
                            root + "cashier/basicInfo.css?version=" + version,
                            root + "cashier/basicInfo.js?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.blankPage', {
            url: '/blankPage',
            templateUrl: root + '/cashier/blankPage.html',
            controller: 'blankPageCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "空白",
                        files: [root + "cashier/blankPage.js?version=" + version,
                            root + "cashier/blankPage.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.accountDetails', {
            url: '/accountDetails/:flowNo/:userId',
            templateUrl: root + '/cashier/accountDetails.html',
            controller: 'accountDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "账户明细",
                        files: [root + "cashier/accountDetailsCtrl.js?version=" + version,
                            root + "cashier/accountDetails.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        .state('pad-web.left_nav.selectRechargeType', {
            url: '/selectRechargeType/:type/:userId/:orderId',
            templateUrl: root + '/cashier/selectRechargeType.html',
            controller: 'selectRechargeTypeCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择充值卡",
                        files: [root + "cashier/selectRechargeTypeCtrl.js?version=" + version,
                            root + "cashier/selectRechargeType.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.selectRechargeCard', {
            url: '/selectRechargeCard/:type/:userId/:rechargeCardType',
            templateUrl: root + '/cashier/selectRechargeCard.html',
            controller: 'selectRechargeCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡充值详情",
                        files: [root + "cashier/selectRechargeCardCtrl.js?version=" + version,
                            root + "cashier/selectRechargeCard.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.chooseGifts', {
            url: '/chooseGifts',
            templateUrl: root + '/cashier/chooseGifts.html',
            controller: 'chooseGiftsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "选择赠送",
                        files: [root + "cashier/chooseGiftsCtrl.js?version=" + version,
                            root + "cashier/chooseGifts.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.consumptionList', {
            url: '/consumptionList/:userId',
            templateUrl: root + '/cashier/consumptionList.html',
            controller: 'consumptionListCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "消费",
                        files: [root + "cashier/consumptionListCtrl.js?version=" + version,
                            root + "cashier/consumptionList.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.makeSureOrder', {
            url: '/makeSureOrder/:orderId/:userId',
            templateUrl: root + '/cashier/makeSureOrder.html',
            controller: 'makeSureOrderCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "确认订单页",
                        files: [root + "cashier/makeSureOrderCtrl.js?version=" + version,
                            root + "cashier/makeSureOrder.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.sources', {
            url: '/sources',
            templateUrl: root + '/cashier/sources.html',
            controller: 'sourcesCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "来源渠道",
                        files: [root + "cashier/sourcesCtrl.js?version=" + version,
                            root + "cashier/sources.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.signConfirm', {
            url: '/signConfirm/:transactionId/:orderId/:userId',
            templateUrl: root + '/cashier/signConfirm.html',
            controller: 'signConfirmCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "签字确认",
                        files: [root + "cashier/signConfirmCtrl.js?version=" + version,
                            root + "cashier/signConfirm.css?version=" + version,
                            root + "cashier/flashcanvas.min.js?version=" + version,
                            root + "cashier/jSignature.min.js?version=" + version,
                            root + "cashier/flashcanvas.swf?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.confirmations', {
            url: '/confirmations/:consumeId/:shopProjectInfoName',
            templateUrl: root + '/cashier/confirmations.html',
            controller: 'confirmationsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "确认单",
                        files: [root + "cashier/confirmationsCtrl.js?version=" + version,
                            root + "cashier/confirmations.css?version=" + version,
                            root + "cashier/flashcanvas.min.js?version=" + version,
                            root + "cashier/jSignature.min.js?version=" + version,
                            root + "cashier/flashcanvas.swf?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.housekeeper', {
            url: '/housekeeper/:type/:index/:orderId/:tempAll/:clerkIds/:clerkNames',
            templateUrl: root + '/cashier/housekeeper.html',
            controller: 'housekeeperCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "关联员工",
                        files: [root + "cashier/housekeeperCtrl.js?version=" + version,
                            root + "cashier/housekeeper.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.drawCardConsumption', {
            url: '/drawCardConsumption/:type/:id',
            templateUrl: root + '/cashier/drawCardConsumption.html',
            controller: 'drawCardConsumptionCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡消费",
                        files: [root + "cashier/drawCardConsumptionCtrl.js?version=" + version,
                            root + "cashier/drawCardConsumption.css?version=" + version,
                            root + "appointment/style.css?version=" + version,
                            root + "cashier/flashcanvas.min.js?version=" + version,
                            root + "cashier/jSignature.min.js?version=" + version,
                            root + "cashier/flashcanvas.swf?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.getProduct', {
            url: '/getProduct/:id',
            templateUrl: root + '/cashier/getProduct.html',
            controller: 'getProductCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "领取产品",
                        files: [root + "cashier/getProductCtrl.js?version=" + version,
                            root + "cashier/getProduct.css?version=" + version,
                            root + "cashier/flashcanvas.min.js?version=" + version,
                            root + "cashier/jSignature.min.js?version=" + version,
                            root + "cashier/flashcanvas.swf?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.orderList', {
            url: '/orderList/:orderId/:userId',
            templateUrl: root + '/cashier/orderList.html',
            controller: 'orderListCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "订单页",
                        files: [root + "cashier/orderListCtrl.js?version=" + version,
                            root + "cashier/orderList.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.prepaidRecords', {
            url: '/prepaidRecords/:userId',
            templateUrl: root + '/cashier/prepaidRecords.html',
            controller: 'prepaidRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值记录",
                        files: [root + "cashier/prepaidRecordsCtrl.js?version=" + version,
                            root + "cashier/prepaidRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.drawCardRecords', {
            url: '/drawCardRecords/:userId',
            templateUrl: root + '/cashier/drawCardRecords.html',
            controller: 'drawCardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡记录",
                        files: [root + "cashier/drawCardRecordsCtrl.js?version=" + version,
                            root + "cashier/drawCardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.drawCardRecordsDetail', {
            url: '/drawCardRecordsDetail/:userId/:flowNo',
            templateUrl: root + '/cashier/drawCardRecordsDetail.html',
            controller: 'drawCardRecordsDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "划卡记录详情",
                        files: [root + "cashier/drawCardRecordsDetail.js?version=" + version,
                            root + "cashier/drawCardRecordsDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.stillOwed', {
            url: '/stillOwed',
            templateUrl: root + '/cashier/stillOwed.html',
            controller: 'stillOwedCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值记录",
                        files: [root + "cashier/stillOwedCtrl.js?version=" + version,
                            root + "cashier/stillOwed.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.accountRecords', {
            url: '/accountRecords/:userId',
            templateUrl: root + '/cashier/accountRecords.html',
            controller: 'accountRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "账户记录",
                        files: [root + "cashier/accountRecordsCtrl.js?version=" + version,
                            root + "cashier/accountRecords.css?version=" + version,
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
                        files: [root + "appointment/appointmentLis.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/collectionCardCtrl.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/consumptionCtrl.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/giving.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/relatedStaffCtrl.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/scratchCard.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/modifyingAppointmentCtrl.js?version=" + version,
                            root + "appointment/postion.css?version=" + version,
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
                        files: [root + "appointment/addCustomersCtrl.js?version=" + version,
                            root + "appointment/addCustomers.css?version=" + version,
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
                        files: [root + "appointment/detailsReservation.js?version=" + version,
                            root + "appointment/detailsReservation.css?version=" + version,
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
                        files: [root + "appointment/selectSingle.js?version=" + version,
                            root + "appointment/selectSingle.css?version=" + version,
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
                        files: [root + "appointment/CtreatmentCard.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
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
                        files: [root + "appointment/selectProduct.js?version=" + version,
                            root + "appointment/selectProduct.css?version=" + version,
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
                        files: [root + "appointment/selectCustomersCtrl.js?version=" + version,
                            root + "appointment/selectCustomers.css?version=" + version,
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
                        files: [root + "appointment/customerSignatureCtrl.js?version=" + version,
                            root + "appointment/customerSignature.css?version=" + version,
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
                        files: [root + "appointment/selectCouponsCtrl.js?version=" + version,
                            root + "appointment/selectCoupons.css?version=" + version,
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
                        files: [root + "appointment/selectTreatmentCard.js?version=" + version,
                            root + "appointment/selectTreatmentCard.css?version=" + version,
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
                        files: [root + "appointment/weeklyReservationCtrl.js?version=" + version,
                            root + "appointment/weeklyReservation.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        /*project//////////////////////////////////////////////////////////////*/
        .state('pad-web.left_nav.project', {
            url: '/project',
            templateUrl: root + '/priceList/project.html',
            controller: 'projectCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "项目",
                        files: [root + "priceList/projectCtrl.js?version=" + version,
                            root + "priceList/project.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.projectDetails', {
            url: '/projectDetails/:id',
            templateUrl: root + '/priceList/projectDetails.html',
            controller: 'projectDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "项目详情",
                        files: [root + "priceList/projectDetailsCtrl.js?version=" + version,
                            root + "priceList/projectDetails.css?version=" + version,
                            root + "../libs/swiper-3.4.0.min.js?version=" + version,
                            root + "../styles/swiper-3.4.0.min.css?version=" + version,
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
                        files: [root + "priceList/productCtrl.js?version=" + version,
                            root + "priceList/product.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.productDetails', {
            url: '/productDetails/:id',
            templateUrl: root + '/priceList/productDetails.html',
            controller: 'productDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "产品详情",
                        files: [root + "priceList/productDetailsCtrl.js?version=" + version,
                            root + "priceList/productDetails.css?version=" + version,
                            root + "../libs/swiper-3.4.0.min.js?version=" + version,
                            root + "../styles/swiper-3.4.0.min.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.setCard', {
            url: '/setCard',
            templateUrl: root + '/priceList/setCard.html',
            controller: 'setCardCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡页面",
                        files: [root + "priceList/setCardCtrl.js?version=" + version,
                            root + "priceList/setCard.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.cardDetails', {
            url: '/cardDetails/:id',
            templateUrl: root + '/priceList/cardDetails.html',
            controller: 'cardDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡详情",
                        files: [root + "priceList/cardDetailsCtrl.js?version=" + version,
                            root + "priceList/cardDetails.css?version=" + version,
                            root + "../libs/swiper-3.4.0.min.js?version=" + version,
                            root + "../styles/swiper-3.4.0.min.css?version=" + version,
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
                        name: "套卡页面",
                        files: [root + "priceList/rechargeableCardCtrl.js?version=" + version,
                            root + "priceList/rechargeableCard.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.rechargeableDetails', {
            url: '/rechargeableDetails/:shopRechargeCardId',
            templateUrl: root + '/priceList/rechargeableDetails.html',
            controller: 'rechargeableDetailsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡详情",
                        files: [root + "priceList/rechargeableDetailsCtrl.js?version=" + version,
                            root + "priceList/rechargeableDetails.css?version=" + version,
                            root + "../libs/swiper-3.4.0.min.js?version=" + version,
                            root + "../styles/swiper-3.4.0.min.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.left_nav.cardSearch', {
            url: '/cardSearch',
            templateUrl: root + '/priceList/cardSearch.html',
            controller: 'vCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡详情",
                        files: [root + "priceList/cardSearchCtrl.js?version=" + version,
                            root + "priceList/cardSearch.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        /*////////////////////////////////////////////////////*/
        .state('pad-web.dayAppointment', {
            url: '/dayAppointment',
            templateUrl: root + '/appointment/dayAppointment.html',
            controller: 'dayAppointmentCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "日预约",
                        files: [root + "appointment/dayAppointment.js?version=" + version,
                            // root + "appointment/laydate.css",
                            // root + "appointment/laydate.js",
                            root + "appointment/dayAppointment.css?version=" + version,
                            root + "appointment/consumptionCtrl.js?version=" + version,
                            root + "appointment/style.css?version=" + version,
                            root + "appointment/detailsReservation.css?version=" + version,
                            root + "appointment/detailsReservationCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                            root + "appointment/appointmentTypeCtrl.js?version=" + version,
                            root + "appointment/selectSingleCtrl.js?version=" + version,
                            root + "appointment/search.css?version=" + version,
                            root + "appointment/searchCtrl.js?version=" + version,
                            root + "appointment/selectTreatmentCard.css?version=" + version,
                            root + "appointment/selectSingle.css?version=" + version,
                            root + "appointment/selectSingleCtrl.js?version=" + version,
                            root + "appointment/selectProduct.css?version=" + version,
                            root + "appointment/payType.css?version=" + version,
                            root + "appointment/payTypeCtrl.js?version=" + version,
                            root + "appointment/balancePrepaid.css?version=" + version,
                            root + "appointment/givingCtrl.js?version=" + version,
                            root + "appointment/scratchCardCtrl.js?version=" + version,
                            root + "appointment/selectTreatmentCard.js?version=" + version,
                            root + "appointment/individualTravelerAppointment.css?version=" + version,
                            root + "appointment/individualTravelerAppointmentCtrl.js?version=" + version,
                            root + "appointment/postion.css?version=" + version,
                            root + "appointment/modifyingAppointmentCtrl.js?version=" + version,
                            root + "appointment/weeklyReservationCtrl.js?version=" + version,
                            root + "appointment/weeklyReservation.css?version=" + version,
                            root + "appointment/selectCustomers.css?version=" + version,
                            root + "appointment/selectCustomersCtrl.js?version=" + version,
                            root + "appointment/addCustomers.css?version=" + version,
                            root + "appointment/addCustomersCtrl.js?version=" + version,
                            root + "appointment/newProject.css?version=" + version,
                            root + "appointment/newProjectCtrl.js?version=" + version,
                            root + "appointment/timeLength.css?version=" + version,
                            root + "appointment/timeLengthCtrl.js?version=" + version,
                            root + "appointment/selectCouponsCtrl.js?version=" + version,
                            root + "appointment/selectCoupons.css?version=" + version,
                            root + "appointment/balancePrepaidCtrl.js?version=" + version,
                            root + "appointment/relatedStaffCtrl.js?version=" + version,
                            root + "appointment/collectionCardCtrl.js?version=" + version,
                            root + "appointment/dropload.css?version=" + version,
                            root + "../libs/angular-touch.js?version=" + version,
                            root + "../libs/swiper-3.4.0.min.js?version=" + version,
                            //root + "../styles/swiper-3.4.0.min.css",
                            root + "../libs/zepto.min.js?version=" + version,
                            "libs/fixedTab.js?version=" + version,
                            root + "appointment/laydate/laydate.js?version=" + version
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
                        files: [root + "appointment/appointmentTypeCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.arrangeWorkList', {
            url: '/arrangeWorkList',
            templateUrl: root + '/appointment/arrangeWorkList.html',
            controller: 'arrangeWorkListCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "排班列表",
                        files: [root + "appointment/arrangeWorkList.js?version=" + version,
                            root + "appointment/arrangeWorkList.css?version=" + version,
                            "libs/fixedTab.js?version=" + version
                        ]
                    })
                }]
            }
        })
        .state('pad-web.compileWorkList', {
            url: '/compileWorkList/:time',
            templateUrl: root + '/appointment/compileWorkList.html',
            controller: 'compileWorkListCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "编辑排班",
                        files: [root + "appointment/compileWorkList.js?version=" + version,
                            root + "appointment/compileWorkList.css?version=" + version,
                            "libs/fixedTab.js?version=" + version
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
                        files: [root + "appointment/payTypeCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                            root + "appointment/payType.css?version=" + version,
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
                        files: [root + "appointment/individualTravelerAppointmentCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                            root + "appointment/individualTravelerAppointment.css?version=" + version,
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
                        files: [root + "appointment/timeLengthCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                            root + "appointment/timeLength.css?version=" + version,
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
                        files: [root + "appointment/newProjectCtrl.js?version=" + version,
                            root + "appointment/appointmentType.css?version=" + version,
                            root + "appointment/newProject.css?version=" + version,
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
                        files: [root + "attendance/attendanceCtrl.js?version=" + version,
                            root + "./appointment/appointmentType.css?version=" + version,
                            root + "attendance/attendance.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //每个卡的消费记录
        .state('pad-web.left_nav.consumeCardDetail', {
            url: '/consumeCardDetail/:userId/:id/:type',
            templateUrl: root + '/cashier/consumeCardDetail.html',
            controller: 'consumeCardDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "每个卡的消费记录",
                        files: [root + "cashier/consumeCardDetail.js?version=" + version,
                            root + "cashier/consumeCardDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //疗程卡划卡记录
        .state('pad-web.left_nav.cureCardRecords', {
            url: '/cureCardRecords/:userId/:id',
            templateUrl: root + '/cashier/cureCardRecords.html',
            controller: 'cureCardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "疗程卡划卡记录",
                        files: [root + "cashier/cureCardRecords.js?version=" + version,
                            root + "cashier/cureCardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        //套卡划卡记录
        .state('pad-web.left_nav.completeCardRecords', {
            url: '/completeCardRecords/:userId/:consumeRecordId/:ids',
            templateUrl: root + '/cashier/completeCardRecords.html',
            controller: 'completeCardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡划卡记录",
                        files: [root + "cashier/completeCardRecords.js?version=" + version,
                            root + "cashier/completeCardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //疗程卡的划卡详情
        .state('pad-web.left_nav.cureCardDetail', {
            url: '/cureCardDetail/:userId/:flowNo',
            templateUrl: root + '/cashier/cureCardDetail.html',
            controller: 'cureCardDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "疗程卡划卡详情",
                        files: [root + "cashier/cureCardDetail.js?version=" + version,
                            root + "cashier/cureCardDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //消费详情
        .state('pad-web.left_nav.completeCardDetail', {
            url: '/completeCardDetail/:userId/:flowNo',
            templateUrl: root + '/cashier/completeCardDetail.html',
            controller: 'completeCardDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡划卡详情",
                        files: [root + "cashier/completeCardDetail.js?version=" + version,
                            root + "cashier/completeCardDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //套卡划卡详情
        .state('pad-web.left_nav.consumeGroupCardDetail', {
            url: '/consumeGroupCardDetail/:userId/:flowNo',
            templateUrl: root + '/cashier/consumeGroupCardDetail.html',
            controller: 'consumeGroupCardDetail',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "套卡划卡详情",
                        files: [root + "cashier/consumeGroupCardDetail.js?version=" + version,
                            root + "cashier/completeCardDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })



        //特殊充值卡充值记录
        .state('pad-web.left_nav.featureRechargeCardRecords', {
            url: '/featureRechargeCardRecords/:userId/:id/:sunAmount',
            templateUrl: root + '/cashier/featureRechargeCardRecords.html',
            controller: 'featureRechargeCardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "特殊充值卡充值记录",
                        files: [root + "cashier/featureRechargeCardRecords.js?version=" + version,
                            root + "cashier/featureRechargeCardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })


        //充值卡账户明细列表
        .state('pad-web.left_nav.rechargeCardRecords', {
            url: '/rechargeCardRecords/:userId',
            templateUrl: root + '/cashier/rechargeCardRecords.html',
            controller: 'rechargeCardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡账户明细列表",
                        files: [root + "cashier/rechargeCardRecords.js?version=" + version,
                            root + "cashier/rechargeCardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //充值卡详情
        .state('pad-web.left_nav.rechargeCardDetail', {
            url: '/rechargeCardDetail/:userId/:flowNo/:id',
            templateUrl: root + '/cashier/rechargeCardDetail.html',
            controller: 'rechargeCardDetailCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "充值卡详情",
                        files: [root + "cashier/rechargeCardDetail.js?version=" + version,
                            root + "cashier/rechargeCardDetail.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        //产品领取记录
        .state('pad-web.left_nav.getProductRecord', {
            url: '/getProductRecord/:userId/:id',
            templateUrl: root + '/cashier/getProductRecord.html',
            controller: 'getProductRecordCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "产品领取记录",
                        files: [root + "cashier/getProductRecord.js?version=" + version,
                            root + "cashier/getProductRecord.css?version=" + version,
                        ]
                    })
                }]
            }
        })

        //产品详情
        .state('pad-web.left_nav.cashProductDetails', {
        url: '/cashProductDetails/:userId/:flowNo',
        templateUrl: root + '/cashier/cashProductDetails.html',
        controller: 'cashProductDetailsCtrl',
        resolve: {
            loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                return $ocLazyLoad.load({
                    name: "产品详情",
                    files: [root + "cashier/cashProductDetails.js?version=" + version,
                        root + "cashier/cashProductDetails.css?version=" + version,
                    ]
                })
            }]
         }
       })
        .state('pad-web.userInfo.cardRecords', {
            url: '/cardRecords/:id',
            templateUrl: root + '/userInfo/cardRecords.html',
            controller: 'cardRecordsCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "我的-充值记录",
                        files: [root + "userInfo/cardRecordsCtrl.js?version=" + version,
                            root + "userInfo/cardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })
        .state('pad-web.userInfo.guide', {
            url: '/guide',
            templateUrl: root + '/userInfo/guide.html',
            controller: 'guideCtrl',
            resolve: {
                loadMyCtrl: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: "我的-操作指南",
                        files: [root + "userInfo/guideCtrl.js?version=" + version,
                            root + "userInfo/guide.css?version=" + version,
                            root + "userInfo/cardRecords.css?version=" + version,
                        ]
                    })
                }]
            }
        })


    ;
    $urlRouterProvider.otherwise('pad-web/userInfo/todayPerformance');
    $httpProvider.interceptors.push('httpInterceptor');
});