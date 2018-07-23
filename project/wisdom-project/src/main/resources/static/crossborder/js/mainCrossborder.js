/**
 * 入口文件
 * 2014-11-30 mon
 */

require.config({
    baseUrl: "js/",
    paths: {
        "angular" : "libs/angular.min",
        "angular-resource" : "libs/angular-resource.min",
        "angular-sanitize" : "libs/angular-sanitize.min",
        "ng-file-upload" : "libs/ng-file-upload.min",
        "ng-file-upload-shim" : "libs/ng-file-upload-shim.min",
        "angular-ui-router" : "libs/angular-ui-router.min",
        "angular-locale_zh-cn": "libs/i18n/angular-locale_zh-cn",
        "ocLazyLoad":"libs/ocLazyLoad.require.min",
        "highcharts":"libs/highcharts",
        "highcharts-ng":"libs/highcharts-ng",
        "ng-infinite-scroll":"libs/ng-infinite-scroll.min",
        "moment":'libs/moment.min',
        "fullCalendar" : "libs/fullcalendar",
        "calendar" : "libs/calendar",
        "gcal" : "libs/gcal",
        "bootstrap-datepicker":"libs/bootstrap-datepicker",
        "bootstrap-datepicker.zh-CN":"libs/bootstrap-datepicker.zh-CN",
        "bootstrap-datetimepicker":"libs/bootstrap-datetimepicker.min",
        "bootstrap-datetimepicker.zh-CN":"libs/bootstrap-datetimepicker.zh-CN",
        "uiBootstrapTpls" : "libs/ui-bootstrap-tpls-0.9.0",
        "crossborderFactory" : "services/crossborderFactory",
        "crossborderGlobal" : "services/crossborderGlobal",
        "crossborderDirective" : "directives/crossborderDirective",
        "crossborderRoute" : "routes/appCrossborderRoute",
        "appCrossborder" : "modules/appCrossborder"
    },
    waitSeconds: 0,
    shim: {
        'fullCalendar' : ['moment'],
        'gcal':['fullCalendar'],
        'angular': {
            deps: ["gcal"],
            exports: 'angular'
        },
        'angular-resource':{
            deps: ["angular"],
            exports: 'angular-resource'
        },
        'ng-file-upload':{
            deps: ['angular'],   //依赖什么模块
        },
        'ng-file-upload-shim':{
            deps: ['angular'],   //依赖什么模块
        },
        'angular-ui-router':{
            deps: ['angular'],   //依赖什么模块
            exports: 'angular-route'
        },
        'angular-sanitize':{
            deps: ['angular'],   //依赖什么模块
            exports: 'angular-sanitize'
        },
        'angular-locale_zh-cn':{
            deps: ['angular'],
        },
        'ocLazyLoad': ['angular'],
        'ng-infinite-scroll': ['angular'],
        'calendar': ['angular'],
        'uiBootstrapTpls': ['angular'],
        'highcharts': {
            deps: ['angular'],   //依赖什么模块
            exports: 'highcharts'
        },
        'highcharts-ng': {
            deps: ['highcharts'],   //依赖什么模块
        },
        'crossborderGlobal': ['angular'],
        'app':['ocLazyLoad'],
        'bootstrap-datepicker.zh-CN':{
            deps:['bootstrap-datepicker']      //依赖什么模块
        },
        'bootstrap-datetimepicker.zh-CN':{
            deps:['bootstrap-datetimepicker']      //依赖什么模块
        }
    }
});

require(['angular','angular-resource','angular-sanitize','ng-file-upload','ng-file-upload-shim','angular-ui-router','angular-locale_zh-cn',
        'ocLazyLoad', 'highcharts','highcharts-ng','ng-infinite-scroll','moment','fullCalendar',
        'calendar', 'gcal','bootstrap-datepicker','bootstrap-datepicker.zh-CN','bootstrap-datetimepicker','bootstrap-datetimepicker.zh-CN',
        'uiBootstrapTpls','crossborderFactory', 'crossborderGlobal','crossborderDirective', 'crossborderRoute','appCrossborder'],
    function (angular){
        angular.bootstrap(document,["crossborderApp"]);
    });

