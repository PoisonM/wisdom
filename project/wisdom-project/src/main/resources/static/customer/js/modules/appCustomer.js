/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('customerApp',['ngResource','ui.router','ngSanitize','ionic',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','customerGlobal','ionic-datepicker'])
        .config(['$httpProvider',function($httpProvider) {

            $httpProvider.interceptors.push(function(){
                return {
                    request: function(config){
                        config.headers = config.headers || {};
                        if(window.location.href.indexOf("beautyAppoint")!=-1) {
                            config.headers.usertype = "beautyUser";
                        }
                        if(window.localStorage.getItem("logintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("logintoken");
                        }
                        if(window.localStorage.getItem("beautyLogintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("beautyLogintoken");
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