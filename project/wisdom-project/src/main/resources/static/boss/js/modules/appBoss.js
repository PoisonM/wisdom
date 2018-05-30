/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('bossApp',['ngResource','ui.router','ngSanitize','ionic',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','bossGlobal','ionic-datepicker'])
        .config(['$httpProvider','$ionicConfigProvider','Global',function($httpProvider,$ionicConfigProvider,Global) {

            $httpProvider.interceptors.push(function($rootScope){
                return {
                    request: function(config){
                        config.headers = config.headers || {};
                        if(window.location.href.indexOf("workHome")!=-1) {
                            config.headers.usertype = Global.userType.BEAUTY_BOSS;
                        }

                        if(window.localStorage.getItem("beautyBossLoginToken")!=undefined){
                            config.headers.beautyBossLoginToken = window.localStorage.getItem("beautyBossLoginToken");
                        }
                        return config;
                    }
                }
            });

            $ionicConfigProvider.tabs.position('bottom');// other values: top

        }])
        .run(function($rootScope){
            $rootScope.shopInfo = {
                shopId:'11',
                entryShopProductList:[],
                outShopProductList:[],
                outShopStockType : '',
                shopStoreId:''
            };
        })
    return app;
});