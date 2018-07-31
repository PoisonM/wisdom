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
                    },

                    requestError: function (err) {
                        //return $q.reject(err);
                        console.log(err)
                    },
                    response: function (res) {
                        if(res.data.errorInfo=="0x00006"){
                            alert("登录已经失效,请重新登录")
                            window.location.href = window.location.href.split("#/")[0]+"#/login"
                        }
                        return res;
                    },
                    responseError: function (err) {
                        // return $q.reject(err);
                        console.log(err)
                    }
                }
            })

        }])
    return app;
});