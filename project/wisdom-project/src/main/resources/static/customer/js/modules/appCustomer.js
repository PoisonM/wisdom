/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('customerApp',['ngResource','ui.router','ngSanitize','ionic',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','customerGlobal','ionic-datepicker'])
        .config(['$httpProvider','Global',function($httpProvider,Global) {

            $httpProvider.interceptors.push(function(){
                return {
                    request: function(config){
                        config.headers = config.headers || {};

                        if(window.location.href.indexOf("beautyAppoint")!=-1||window.location.href.indexOf("beautyUserCenter")!=-1) {
                            config.headers.usertype = Global.userType.BEAUTY_USER;
                        }

                        if(window.localStorage.getItem("logintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("logintoken");
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

        }])
        .run(function($rootScope){
            $rootScope.returnRootNative = function(){
            };
            $rootScope.shopAppointInfo = {
                clerkId : '',
                shopProjectIds:[],
                shopProjectDetail:'',
                shopUserInfo:{}
            }
        })
    return app;
});