﻿/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('bossApp',['ngResource','ui.router','ngSanitize','ionic',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','bossGlobal','ionic-datepicker'])
        .config(['$httpProvider',function($httpProvider) {

            $httpProvider.interceptors.push(function($rootScope){
                return {
                    request: function(config){
                        config.headers = config.headers || {};
                        if(window.localStorage.getItem("logintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("logintoken");
                        }
                        if(window.localStorage.getItem("beautylogintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("beautylogintoken");
                        }
                        return config;
                    }
                }
            });

        }])
        .run(function($rootScope){
            $rootScope.returnRootNative = function(){
            };
        })
    return app;
});