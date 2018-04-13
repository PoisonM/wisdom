function weeklyReservation ($scope,ngDialog){
    $scope.weekLis = function(lis){
        ngDialog.open({
            template: 'appointmentLis',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.timeLengthIndex = -1;
                $scope.param.timeLengthArr = ["李","网",'周'];
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'appointmentLisArea ngdialog-theme-custom'
        });

    }
}