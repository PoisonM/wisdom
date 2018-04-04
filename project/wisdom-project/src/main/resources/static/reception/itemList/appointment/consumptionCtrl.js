function consumption($scope){
    $scope.selsectConsumption = function(type){
        if(type == "selectSingle"){
            $scope.param.selectSingle = true;
        }else if (type=="selectTreatmentCard"){
            $scope.param.selectTreatmentCard = true;
        }else if (type=="selectProduct"){
            $scope.param.selectProduct = true;
        }else if (type=="collectionCard"){
            $scope.param.collectionCard = true;
        }
    }
    $scope.subtractOrAdd = function(type){
        if(type == 0){
            if($scope.param.num == 1)return
            $scope.param.num--
        }else{
            $scope.param.num++
        }
    }
   $scope.balancePrepaid = function(){
       $scope.param.balancePrepaid = true;
   }
   $scope.giving = function(){
       $scope.param.giving = true;
   }
   $scope.relatedStaff = function(){
       $scope.param.relatedStaff = true;
   }
   $scope.nextStepBtnConsumption = function(){
       $scope.param.consumptionNextStep = true;
   }
   givingChange && givingChange($scope);
    payTypeCtrl && payTypeCtrl($scope);
    scratchCard && scratchCard($scope);
}
