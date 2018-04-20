function givingChange($scope,ngDialog){
    $scope.giving = function(){
        $scope.ngDialog = ngDialog;
                ngDialog.open({
                    template: 'giving',
                    scope: $scope, //这样就可以传递参数
                    controller: ['$scope', '$interval', function($scope, $interval) {
                        console.log($scope.$parent.content);
                        $scope.close = function() {
                            $scope.closeThisDialog();
                        };
                    }],
                    className: 'collectionCard ngdialog-theme-custom'
                });
    }

    $scope.givingChange = function(index){
        $scope.param.givingIndex = index;
        if(index == 2){
            $scope.param.givingVouchers = true;
            $scope.param.givingProduct = false;
        }else{
            $scope.param.givingVouchers = false;
            $scope.param.givingProduct = true;
        }
    }
}
