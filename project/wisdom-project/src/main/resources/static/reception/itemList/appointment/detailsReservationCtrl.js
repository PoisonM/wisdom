function detailsReservation($scope){
    

    $scope.consumptionGo = function(status){
        $scope.param.detailsReservation = false;
        if(status == "去消费"){
            $scope.param.consumption = true;
        }else{
            $scope.param.scratchCard = true;
        }
        
        consumption && consumption($scope);
        scratchCard && scratchCard($scope);
        
        console.log(status)
    }
    $scope.startAppointment = function(){
        $scope.param.ModifyAppointment = false;
    }
}

