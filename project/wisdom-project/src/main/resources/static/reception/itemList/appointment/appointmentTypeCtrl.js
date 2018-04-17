function appointmentTypeCtrl($scope, ngDialog){
    $scope.newAppointment = function(){
        $scope.param.appointmentNew = "yes";
        ngDialog.open({
            template: 'modifyingAppointment',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.cancellationFlag = false;
                $scope.close = function() {
                    if($scope.param.appointmentNew =="yes"){
                        ngDialog.closeAll()
                        $scope.param.appointmentObject.list[$scope.param.index.index1].status[$scope.param.index.index2] = 0;
                        $scope.param.appointmentObject.list[$scope.param.index.index1].sysUserName[$scope.param.index.index2] = null;
                    }
                };
            }],
            className: 'modifyingAppointment ngdialog-theme-custom'
        });
    }


}

