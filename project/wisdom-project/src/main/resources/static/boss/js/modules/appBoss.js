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

                        if(window.localStorage.getItem("beautyUserLoginToken")!=undefined
                            &&window.localStorage.getItem("beautyUserLoginToken")!=null){
                            config.headers.beautyuserlogintoken = window.localStorage.getItem("beautyUserLoginToken");
                        }

                        if(window.localStorage.getItem("beautyBossLoginToken")!=undefined
                            &&window.localStorage.getItem("beautyBossLoginToken")!=null){
                            config.headers.beautybosslogintoken = window.localStorage.getItem("beautyBossLoginToken");
                        }

                        if(window.localStorage.getItem("beautyClerkLoginToken")!=undefined
                            &&window.localStorage.getItem("beautyClerkLoginToken")!=null){
                            config.headers.beautyclerklogintoken = window.localStorage.getItem("beautyClerkLoginToken");
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