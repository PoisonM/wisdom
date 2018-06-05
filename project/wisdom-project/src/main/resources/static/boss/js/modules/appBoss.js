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
                            window.localStorage.setItem("userType",Global.userType.BEAUTY_BOSS);
                        }

                        if(window.localStorage.getItem("userType")!=undefined
                            &&window.localStorage.getItem("userType")!=null)
                        {
                            config.headers.usertype = window.localStorage.getItem("userType");
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
            $rootScope.settingAddsome={
                editedRecharge:'',/*充值卡信息*/
                editorCard:'',/*套卡信息*/
                extShopProjectInfoDTO:'',/*项目信息*/
                product:"",/*产品信息*/
                productId:''/*用于查询产品详情*/
            }
        })
    return app;
});