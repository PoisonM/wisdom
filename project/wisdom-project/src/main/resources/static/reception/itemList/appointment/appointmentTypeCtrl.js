function appointmentTypeCtrl($scope, ngDialog,UpdateAppointmentInfoById,FindArchives){
    $scope.newAppointment = function(){
        $scope.param.appointmentNew = "yes";
        ngDialog.open({
            template: 'modifyingAppointment',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.cancellationFlag = false;
                $scope.close = function(status) {
                    if($scope.param.appointmentNew =="yes"){

                        $scope.param.appointmentObject.list[$scope.param.index.index1].status[$scope.param.index.index2] = 0;
                        $scope.param.appointmentObject.list[$scope.param.index.index1].sysUserName[$scope.param.index.index2] = null;
                    }
                    if(status == 1){
                        $scope.shopAppointServiceDTO = {
                            appointStartTime:$scope.param.ModifyAppointmentObject.appointStartTime,
                            appointEndTime:$scope.param.ModifyAppointmentObject.appointEndTime,
                            appointPeriod:$scope.param.ModifyAppointmentObject.appointPeriod,
                            detail:$scope.param.ModifyAppointmentObject.detail,
                            shopProjectId:$scope.param.newProductObject.shopProjectId,
                            shopProjectName:$scope.param.newProductObject.shopProjectName,
                            sysUserId:$scope.param.selectCustomersObject.sysUserId,
                            sysUserName:$scope.param.selectCustomersObject.sysUserName,
                            sysUserPhone:$scope.param.selectCustomersObject.sysUserPhone,
                            status:$scope.param.ModifyAppointmentObject.status
                        };
                    if(status == 3){
                        UpdateAppointmentInfoById.get({
                            shopAppointServiceId:id,
                            status:0
                        },function(data){

                        })
                    }
                        console.log($scope.shopAppointServiceDTO)

                    }
                    /* ngDialog.closeAll()*/
                };
            }],
            className: 'modifyingAppointment ngdialog-theme-custom'
        });
    }


}

