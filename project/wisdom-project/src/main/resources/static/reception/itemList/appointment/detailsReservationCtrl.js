function detailsReservation($scope){

    $scope.consumptionGo = function(){
        $scope.param.consumption = true;
        consumption && consumption($scope);
    }
}

