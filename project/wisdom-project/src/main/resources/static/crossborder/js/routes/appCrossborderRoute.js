/**
 * 路由
 */
define(['appCrossborder'], function(app){
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
                    //公共部分
                    // .state('home', {
                    //     url: '/home',
                    //     templateProvider: function() { return lazyDeferred.promise; },
                    //     controller: 'homeCtrl',
                    //     resolve: {
                    //         load: function($templateCache, $ocLazyLoad, $q, $http) {
                    //             loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.homeCtrl',
                    //                 [
                    //                     'js/controllers/homeCtrl.js',
                    //                     'js/css/home.css',
                    //                 ],
                    //                 'js/views/home.html');
                    //         }
                    //     }
                    // })
                    //首页
                    .state('goodsList', {
                        url: '/goodsList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'goodsListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.goodsListCtrl',
                                    [
                                        'js/controllers/goodsListCtrl.js',
                                        'js/css/goodsList.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/goodsList.html');
                            }
                        }
                    })



                    //详情页
                    .state('details', {
                        url: '/details/:id',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'detailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.detailsCtrl',
                                    [
                                        'js/controllers/detailsCtrl.js',
                                        'js/css/details.css',
                                        'js/css/home.css',
                                        'js/libs/idangerous.swiper.min.js',
                                    ],
                                    'js/views/details.html');
                            }
                        }
                    })

                    //购物车
                    .state('shoppingCart', {
                        url: '/shoppingCart',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'shoppingCartCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.shoppingCartCtrl',
                                    [
                                        'js/controllers/shoppingCartCtrl.js',
                                        'js/css/shoppingCart.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/shoppingCart.html');
                            }
                        }
                    })
                    //我的订单
                    .state('orderList', {
                        url: '/orderList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'orderListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.orderListCtrl',
                                    [
                                        'js/controllers/orderListCtrl.js',
                                        'js/css/orderList.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/orderList.html');
                            }
                        }
                    })
                    .state('orderSubmit', {
                        url: '/orderSubmit',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'orderSubmitCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.orderSubmitCtrl',
                                    [
                                        'js/controllers/orderSubmitCtrl.js',
                                        'js/css/orderSubmit.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/orderSubmit.html');
                            }
                        }
                    })
                    .state('login', {
                        url: '/login',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'loginCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.loginCtrl',
                                    [
                                        'js/controllers/loginCtrl.js',
                                        'js/css/login.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/login.html');
                            }
                        }
                    })
                    .state('scanPay', {
                        url: '/scanPay',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'scanPayCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.scanPayCtrl',
                                    [
                                        'js/controllers/scanPayCtrl.js',
                                        'js/libs/qrcode/qrcode.min.js',
                                        'js/css/scanPay.css',
                                        'js/css/home.css',
                                    ],
                                    'js/views/scanPay.html');
                            }
                        }
                    })
                $urlRouterProvider.otherwise('goodsList')
            }])
})