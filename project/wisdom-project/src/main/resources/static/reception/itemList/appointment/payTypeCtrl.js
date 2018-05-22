function payTypeCtrl ($scope,ngDialog){
    $scope.nextStepBtnConsumption = function(){
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'consumptionNextStep',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {

                $scope.close = function() {
                    $scope.closeThisDialog();
                };
                $scope.closeAll = function(){
                    ngDialog.closeAll();
                }
            }],
            className: 'payType ngdialog-theme-custom'
        });
    }
    selectCouponsCtrl && selectCouponsCtrl($scope,ngDialog)/*优惠券*/

}