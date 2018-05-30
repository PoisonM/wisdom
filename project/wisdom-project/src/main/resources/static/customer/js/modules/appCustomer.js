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
                        if(window.location.href.indexOf("beautyAppoint")!=-1) {
                            config.headers.usertype = Global.userType.BEAUTY_USER;
                        }
                        if(window.localStorage.getItem("logintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("logintoken");
                        }
                        if(window.localStorage.getItem("beautyUserLoginToken")!=undefined){
                            config.headers.beautyUserLoginToken = window.localStorage.getItem("beautyUserLoginToken");
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