function individualTravelerAppointment ($scope){
    $scope.startAppointmentIndivdual = function(){
        $scope.param.ModifyAppointment = false;
    };
    $scope.modifyingAppointmentIndivdual = function(){
        $scope.param.modifyingAppointment = true;
        $scope.param.individualTravelerAppointment = false;
        $scope.param.AppointmentType="散客"
    };
    modifyingAppointmentPage && modifyingAppointmentPage ($scope)
}

