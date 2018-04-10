function selectCustomersCtrl($scope,ngDialog){
    /*选择顾客*/
    $scope.selectCustomersCtrl = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'modifyingAppointment ngdialog-theme-custom'
        });

    }
    addCustomersCtrl && addCustomersCtrl($scope,ngDialog)
}

