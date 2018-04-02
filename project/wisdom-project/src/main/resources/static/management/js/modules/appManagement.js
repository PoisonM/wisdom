/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('managementApp',['ngResource','ui.router','ngSanitize',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','managementGlobal'])
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