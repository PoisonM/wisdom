function selectCustomersCtrl($scope){
    $scope.selectCustomersCtrl = function(){
        $scope.param.selectCustomersWrap = true;
        $scope.param.modifyingAppointment = false;

    }
    addCustomersCtrl && addCustomersCtrl($scope)
}

