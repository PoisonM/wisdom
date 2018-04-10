function relatedStaffCtrl ($scope,ngDialog){
    $scope.relatedStaff = function(){
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'relatedStaff',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'scratchCard ngdialog-theme-custom'
        });
    }
}

