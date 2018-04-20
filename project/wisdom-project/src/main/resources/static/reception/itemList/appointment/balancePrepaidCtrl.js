function balancePrepaid ($scope,ngDialog){
    $scope.ngDialog = ngDialog;
    $scope.balancePrepaid = function(){
        ngDialog.open({
            template: 'balancePrepaid',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'payType ngdialog-theme-custom'
        });
    }
}
