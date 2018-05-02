/**
 * 路由
 */
define(['appCustomer'], function(app){
    return app
        .config(['$stateProvider','$urlRouterProvider',
            function($stateProvider,$urlRouterProvider) {
                var loadFunction = function($templateCache, $ocLazyLoad, $q, $http,name,files,htmlURL){
                    lazyDeferred = $q.defer();
                    return $ocLazyLoad.load ({
                        name: name,
                        files: files
                    }).then(function() {
                        return $http.get(htmlURL)
                            .success(function(data, status, headers, config) {
                                return lazyDeferred.resolve(data);
                            }).
                            error(function(data, status, headers, config) {
                                return lazyDeferred.resolve(data);
                            });
                    });
                };

                $stateProvider
                    .state('addressEdit', {
                        url: '/addressEdit/:type,:addressId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addressEditCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addressEditCtrl',
                                    ['js/controllers/business/addressEditCtrl.js?ver='+ customerVersion,
                                        "js/libs/ydui.js?ver='+ customerVersion",
                                        "styles/ydui.css?ver='+ customerVersion",
                                    ],
                                    'views/business/addressEdit.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('addressManagement', {
                        url: '/addressManagement/:routePath',//从哪个页面来，回哪个页面去
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addressManagementCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addressManagementCtrl',
                                    ['js/controllers/business/addressManagementCtrl.js?ver='+ customerVersion],
                                    'views/business/addressManagement.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('shopHome', {
                        url: '/shopHome',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'shopHomeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.shopHomeCtrl',
                                    ['js/controllers/business/shopHomeCtrl.js?ver='+ customerVersion],
                                    'views/business/shopHome.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('shareHome', {
                        url: '/shareHome',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'shareHomeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.shareHomeCtrl',
                                    ['js/controllers/business/shareHomeCtrl.js?ver='+ customerVersion],
                                    'views/business/shareHome.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('sharePage', {
                        url: '/sharePage',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'sharePageCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.sharePageCtrl',
                                    ['js/controllers/business/sharePageCtrl.js?ver='+ customerVersion],
                                    'views/business/sharePage.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('buyCart', {
                        url: '/buyCart',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'buyCartCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.buyCartCtrl',
                                    ['js/controllers/business/buyCartCtrl.js?ver='+ customerVersion],
                                    'views/business/buyCart.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('login', {
                        url: '/login/:redirectUrl',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'loginCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.loginCtrl',
                                    ['js/controllers/business/loginCtrl.js?ver='+ customerVersion],
                                    'views/business/login.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('paySuccess', {
                        url: '/paySuccess',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'paySuccessCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.paySuccessCtrl',
                                    ['js/controllers/business/paySuccessCtrl.js?ver='+ customerVersion],
                                    'views/business/paySuccess.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('offlineProductDetail', {
                        url: '/offlineProductDetail/:productId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'offlineProductDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.offlineProductDetailCtrl',
                                    ['js/controllers/business/offlineProductDetailCtrl.js?ver='+ customerVersion],
                                    'views/business/offlineProductDetail.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('trainingProductList', {
                        url: '/trainingProductList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'trainingProductListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.trainingProductListCtrl',
                                    ['js/controllers/business/trainingProductListCtrl.js?ver='+ customerVersion],
                                    'views/business/trainingProductList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('trainingProductLearning', {
                        url: '/trainingProductLearning/:productId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'trainingProductLearningCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.trainingProductLearningCtrl',
                                    ['js/controllers/business/trainingProductLearningCtrl.js?ver='+ customerVersion],
                                    'views/business/trainingProductLearning.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('withDraw', {
                        url: '/withDraw',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'withDrawCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.withDrawCtrl',
                                    ['js/controllers/business/withDrawCtrl.js?ver='+ customerVersion],
                                    'views/business/withDraw.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('account', {
                        url: '/account',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'accountCtrl',
                        cache:false,
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.accountCtrl',
                                    ['js/controllers/business/accountCtrl.js?ver='+ customerVersion],
                                    'views/business/account.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('myselfCenter', {
                        url: '/myselfCenter',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'myselfCenterCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.myselfCenterCtrl',
                                    ['js/controllers/business/myselfCenterCtrl.js?ver='+ customerVersion],
                                    'views/business/myselfCenter.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('orderManagement', {
                        url: '/orderManagement/:type',//type的状态用来表示不同的订单状态
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'orderManagementCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.orderManagementCtrl',
                                    ['js/controllers/business/orderManagementCtrl.js?ver='+ customerVersion],
                                    'views/business/orderManagement.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('suggestion', {
                        url: '/suggestion',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'suggestionCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.suggestionCtrl',
                                    ['js/controllers/business/suggestionCtrl.js?ver='+ customerVersion],
                                    'views/business/suggestion.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('transactionList', {
                        url: '/transactionList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'transactionListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionListCtrl',
                                    ['js/controllers/business/transactionListCtrl.js?ver='+ customerVersion],
                                    'views/business/transactionList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('transactionDetail', {
                        url: '/transactionDetail/:transactionId,:type',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'transactionDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/transactionDetailCtrl.js?ver='+ customerVersion],
                                    'views/business/transactionDetail.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('personalInformation', {
                        url: '/personalInformation',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'personalInformationCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/personalInformationCtrl.js?ver='+ customerVersion],
                                    'views/business/personalInformation.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('aboutMine', {
                        url: '/aboutMine',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'aboutMineCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/aboutMineCtrl.js?ver='+ customerVersion],
                                    'views/business/aboutMine.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('invoice', {
                        url: '/invoice/:orderId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'invoiceCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/invoiceCtrl.js?ver='+ customerVersion],
                                    'views/business/invoice.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('supportBank', {
                        url: '/supportBank',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'supportBankCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/supportBankCtrl.js?ver='+ customerVersion],
                                    'views/business/supportBank.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beans', {
                        url: '/beans',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beansCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/beansCtrl.js?ver='+ customerVersion],
                                    'views/business/beans.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('orderDetails', {
                        url: '/orderDetails/:orderId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'orderDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/orderDetailsCtrl.js?ver='+ customerVersion],
                                    'views/business/orderDetails.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('myTeam', {
                        url: '/myTeam',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'myTeamCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/myTeamCtrl.js?ver='+ customerVersion],
                                    'views/business/myTeam.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('trainingHome', {
                        url: '/trainingHome',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'trainingHomeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/trainingHomeCtrl.js?ver='+ customerVersion],
                                    'views/business/trainingHome.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('experience', {
                        url: '/experience',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'experienceCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/experienceCtrl.js?ver='+ customerVersion],
                                    'views/business/experience.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('drawDetails', {
                        url: '/drawDetails/:status,:withDrawAmount',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'drawDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/drawDetailsCtrl.js?ver='+ customerVersion],
                                    'views/business/drawDetails.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('logisticDetails', {
                        url: '/logisticDetails/:orderId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'logisticDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/logisticDetailsCtrl.js?ver='+ customerVersion],
                                    'views/business/logisticDetails.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('shopActivity', {
                        url: '/shopActivity',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'shopActivityCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/shopActivityCtrl.js?ver='+ customerVersion],
                                    'views/business/shopActivity.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('protocol', {
                        url: '/protocol',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'protocolCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.transactionDetailCtrl',
                                    ['js/controllers/business/protocolCtrl.js?ver='+ customerVersion],
                                    'views/business/protocol.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('specialProductList', {
                        url: '/specialProductList/:specialShopId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'specialProductListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.specialProductListCtrl',
                                    ['js/controllers/business/specialProductListCtrl.js?ver='+ customerVersion],
                                    'views/business/specialProductList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('specialShopTransactionList', {
                        url: '/specialShopTransactionList/:specialShopId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'specialShopTransactionListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.specialShopTransactionListCtrl',
                                    ['js/controllers/business/specialShopTransactionListCtrl.js?ver='+ customerVersion],
                                    'views/business/specialShopTransactionList.html?ver=' + customerVersion);
                            }
                        }
                    })
                 .state('specialTransactionDetail', {
                        url: '/specialTransactionDetail/:orderId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'specialTransactionDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.specialTransactionDetailCtrl',
                                    ['js/controllers/business/specialTransactionDetailCtrl.js?ver='+ customerVersion],
                                    'views/business/specialTransactionDetail.html?ver=' + customerVersion);
                            }
                        }
                    })

                    .state('beautyAppoint', {
                        url: '/beautyAppoint',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyAppointCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyAppointCtrl',
                                    ['js/controllers/beauty/beautyAppointCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyAppoint.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyProjectList', {
                        url: '/beautyProjectList/:shopProjectId',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyProjectListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyProjectListCtrl',
                                    ['js/controllers/beauty/beautyProjectListCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyProjectList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyClerkList', {
                        url: '/beautyClerkList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyClerkListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyClerkListCtrl',
                                    ['js/controllers/beauty/beautyClerkListCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyClerkList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('expenseCardRecord', {
                        url: '/expenseCardRecord',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'expenseCardRecordCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.expenseCardRecordCtrl',
                                    ['js/controllers/beauty/expenseCardRecordCtrl.js?ver='+ customerVersion],
                                    'views/beauty/expenseCardRecord.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('projectCardList', {
                        url: '/projectCardList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'projectCardListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.projectCardListCtrl',
                                    ['js/controllers/beauty/projectCardListCtrl.js?ver='+ customerVersion],
                                    'views/beauty/projectCardList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('projectCardDetail', {
                        url: '/projectCardDetail',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'projectCardDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.projectCardDetailCtrl',
                                    ['js/controllers/beauty/projectCardDetailCtrl.js?ver='+ customerVersion],
                                    'views/beauty/projectCardDetail.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautySendMessage', {
                        url: '/beautySendMessage',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautySendMessageCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautySendMessageCtrl',
                                    ['js/controllers/beauty/beautySendMessageCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautySendMessage.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyUserCenter', {
                        url: '/beautyUserCenter',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyUserCenterCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyUserCenterCtrl',
                                    ['js/controllers/beauty/beautyUserCenterCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyUserCenter.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyUserQRCode', {
                        url: '/beautyUserQRCode',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyUserQRCodeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyUserQRCodeCtrl',
                                    ['js/controllers/beauty/beautyUserQRCodeCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyUserQRCode.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyUserAppoint', {
                        url: '/beautyUserAppoint',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyUserAppointCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyUserAppointCtrl',
                                    ['js/controllers/beauty/beautyUserAppointCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyUserAppoint.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyUserAppointDetail', {
                        url: '/beautyUserAppointDetail',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyUserAppointDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyUserAppointDetailCtrl',
                                    ['js/controllers/beauty/beautyUserAppointDetailCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyUserAppointDetail.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyShopList', {
                        url: '/beautyShopList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyShopListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyShopListCtrl',
                                    ['js/controllers/beauty/beautyShopListCtrl.js?ver='+ customerVersion],
                                    'views/beauty/beautyShopList.html?ver=' + customerVersion);
                            }
                        }
                    })
                    .state('beautyLogin', {
                        url: '/beautyLogin/:redirectUrl',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'beautyLoginCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.beautyLoginCtrl',
                                    ['js/controllers/business/beautyLogin.js?ver='+ customerVersion],
                                    'views/business/beautyLogin.html?ver=' + customerVersion);
                            }
                        }
                    })

                $urlRouterProvider.otherwise('/shopHome')
            }])
})