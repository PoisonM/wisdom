function detailsReservation($scope){
    $scope.consumptionGo = function(status){
        $scope.param.detailsReservation = false;
        $scope.param.individualTravelerAppointment = false;
        if(status == "去消费"){
            $scope.param.consumption = true;
        }else{
            $scope.param.scratchCard = true;
        }
    };
    $scope.startAppointment = function(){
        $scope.param.ModifyAppointment = false;
    };
    $scope.modifyingAppointment = function(){
        $scope.param.modifyingAppointment = true;
        $scope.param.detailsReservation = false;
        $scope.param.AppointmentType="长客"
    }
    consumption && consumption($scope);
    scratchCard && scratchCard($scope);
    modifyingAppointmentPage && modifyingAppointmentPage ($scope)
}

