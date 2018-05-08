define(['appBoss','jquery'], function (app,$) {
    app
        .directive('bossFooter', ['$rootScope','$state',
            function ($rootScope,$state) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    template: '<ion-tabs class="tabs-icon-top tabs-color-active-positive">' +
                    '<ion-tab title="工作" icon-off="ion-ios-personadd" icon-on="ion-ios-personadd" ui-sref="workHome">' +
                    '<ion-nav-view name="tab-account"></ion-nav-view></ion-tab>' +
                    '<ion-tab title="档案" icon-off="ion-ios-filing" icon-on="ion-ios-filing" ui-sref="partialFiles">' +
                    '<ion-nav-view name="tab-account"></ion-nav-view></ion-tab>' +
                    '<ion-tab title="我的" icon-off="ion-android-contact" icon-on="ion-android-contact" ui-sref="aboutMine">' +
                    '<ion-nav-view name="tab-dash"></ion-nav-view></ion-tab></ion-tabs>',
                    link: function(scope,ele,attrs) {
                    }
                }
        }])
})
