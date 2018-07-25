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
                    .state('home', {
                        url: '/home',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'homeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.homeCtrl',
                                    [
                                        'js/controllers/homeCtrl.js',
                                        'js/css/home.css',
                                    ],
                                    'js/views/home.html');
                            }
                        }
                    })
                    //首页
                    .state('home.goodsList', {
                        url: '/goodsList',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'goodsListCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.goodsListCtrl',
                                    [
                                        'js/controllers/goodsListCtrl.js',
                                        'js/css/goodsList.css',
                                    ],
                                    'js/views/goodsList.html');
                            }
                        }
                    })



                    //详情页
                    .state('home.details', {
                        url: '/details',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'detailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.detailsCtrl',
                                    [
                                        'js/controllers/detailsCtrl.js',
                                        'js/css/details.css',
                                    ],
                                    'js/views/details.html');
                            }
                        }
                    })

                $urlRouterProvider.otherwise('home/goodsList')
            }])
})