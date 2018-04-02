PADWeb.controller('demoCtrl', function($scope, $stateParams, ngDialog) {
    console.log("memeda");
    /*-------------------------弹窗插件用法------------------------*/
    $scope.content = "么么哒"

    $scope.haha = "abc";
    $scope.showMessage = function() {
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'showMessage',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'ngdialog-theme-default ngdialog-theme-custom',
            width: 600
        });
    };


    $scope.clickInclude = function(num) {
        $scope.num = num
        console.log($scope.num);
    }
    //这边引入include
    include && include($scope);
});