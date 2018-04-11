function detailsReservation($scope,ngDialog){
    $scope.consumptionGo = function(status){
        $scope.ngDialog = ngDialog;
        if(status == "去消费"){
            ngDialog.open({
                template: 'consumption',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                    ngDialog.close("detailsWrap")
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',

            });
        }else{
            ngDialog.open({
                template: 'scratchCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.close = function(type) {
                        if(type==0){
                            $scope.closeThisDialog();
                        }
                        else if(type == 3){
                            ngDialog.closeAll()
                        }
                        else{
                            ngDialog.open({
                                template: 'scratchCardSelectTreatmentCard',
                                scope: $scope, //这样就可以传递参数
                                controller: ['$scope', '$interval', function($scope, $interval) {

                                    console.log($scope.$parent.content);
                                    $scope.close = function(type) {
                                            $scope.closeThisDialog();
                                    };
                                }],
                                className: 'trueScratchCard ngdialog-theme-custom',

                            });
                        }

                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',

            });
        }


    };
    /*$scope.startAppointment = function(){
        $scope.param.ModifyAppointment = false;
    };*/
  /*修改预约*/
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog);
    consumption && consumption($scope,ngDialog);
    scratchCard && scratchCard($scope,ngDialog);

}

