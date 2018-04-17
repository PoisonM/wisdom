function selectProductCtrl($scope){

    $scope.newProductBtn = function(index){
        $scope.param.newProductObject.index =index;
        if(index == 1){
            $scope.param.newProductObject.titleFlag = true;
            $scope.param.newProductObject.content = false;
        }else{
            $scope.param.newProductObject.titleFlag = false;
            $scope.param.newProductObject.content = true;
        }
    };
    $scope.falseAll = function(){
        for(var i=0;i<7;i++){
            $scope.param.ModifyAppointmentObject.productIndex[i] = false;
        }
    }
    $scope.falseAll()
    $scope.selectTheProduct = function(index,type){
        if(type == "疗程"){
            $scope.param.ModifyAppointmentObject.productIndex[index] =!$scope.param.ModifyAppointmentObject.productIndex[index];

        }else{
            $scope.param.ModifyAppointmentObject.productIndex[index+3] = !$scope.param.ModifyAppointmentObject.productIndex[index+3];
        }
    }

}