function addCustomersCtrl ($scope,ngDialog) {
    /*添加顾客*/
    $scope.addCustomersCtrl = function(){
        ngDialog.open({
            template: 'addCustomers',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'modifyingAppointment ngdialog-theme-custom'
        });

    }
}

