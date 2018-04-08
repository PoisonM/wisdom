function weeklyReservation ($scope){
    console.log($scope.param.weekFlag);
    $scope.weekLis = function(lis,e){
        var appointmentLisArea = document.getElementsByClassName("appointmentLisArea")[0];
        if(e.clientY >350){
            appointmentLisArea.style.top = 3.2+"rem";
            appointmentLisArea.style.left = (e.clientX +100)/128+"rem";
        }else{
            appointmentLisArea.style.top = (e.clientY +100)/128+"rem";
            appointmentLisArea.style.left = (e.clientX +100)/128+"rem";
        }
        $scope.allFalse();
        $scope.param.appointmentLis = true;

    }
}