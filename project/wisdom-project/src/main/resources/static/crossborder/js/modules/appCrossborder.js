/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('crossborderApp',['ngResource','ui.router','ngSanitize',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','crossborderGlobal'])
        .config(['$httpProvider',function($httpProvider,$rootScope) {

            $httpProvider.interceptors.push(function($rootScope){
                return {
                    request: function(config){
                        config.headers = config.headers || {};
                        if(window.localStorage.getItem("logintoken")!=undefined){
                            config.headers.logintoken = window.localStorage.getItem("logintoken");
                        }

                        return config;
                    }
                }
            })

        }])
    return app;
});