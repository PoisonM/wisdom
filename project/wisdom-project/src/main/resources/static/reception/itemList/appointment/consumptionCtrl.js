function consumption($scope,ngDialog){
    $scope.ngDialog = ngDialog;
    $scope.selsectConsumption = function(type){

        if(type == "selectSingle"){
            ngDialog.open({
                template: 'selectSingle',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="selectTreatmentCard"){
            ngDialog.open({
                template: 'selectTreatmentCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="selectProduct"){
            ngDialog.open({
                template: 'selectProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="collectionCard"){
            ngDialog.open({
                template: 'collectionCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }
    }
    $scope.subtractOrAdd = function(type){
        if(type == 0){
            if($scope.param.num == 1)return
            $scope.param.num--
        }else{
            $scope.param.num++
        }
    }
    balancePrepaid && balancePrepaid ($scope,ngDialog)/*余额充值*/
    givingChange && givingChange($scope,ngDialog);/*赠送*/
    relatedStaffCtrl && relatedStaffCtrl($scope,ngDialog);/*关联员工*/

    payTypeCtrl && payTypeCtrl($scope,ngDialog);/*消费-消费-下一步*/
    scratchCard && scratchCard($scope);
    selectSingle && selectSingle($scope);
    searchConsumption && searchConsumption($scope)
}
