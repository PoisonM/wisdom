function selectProductCtrl($scope){

    $scope.selectProduct = function(){
        $scope.param.newProduct = true;
        $scope.param.modifyingAppointment = false;
    }
    $scope.newProductBtn = function(index){
        $scope.param.newProductObject.index =index;
        if(index == 1){
            $scope.param.newProductObject.titleFlag = true;
        }else{
            $scope.param.newProductObject.titleFlag = false;
        }
    }

}