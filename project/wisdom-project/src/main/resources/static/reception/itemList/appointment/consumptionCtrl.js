function consumption($scope) {
   $scope.subtractOrAdd = function(status){
       if(status == 0){
           if( $scope.param.num == 1)return;
           $scope.param.num --;
       }else{
           $scope.param.num ++;
       }
   }
   $scope.selsectConsumption = function(status){
       if(status == 'selectSingle'){
           $scope.param.selectSingle = true;
       }else if(status == 'selectTreatmentCard'){
           $scope.param.selectTreatmentCard = true;
       }else if(status == 'selectProduct'){
           $scope.param.selectProduct = true;
       }else if(status == 'collectionCard'){
           $scope.param.collectionCard = true;
       }
   }
  $scope.nextStepBtnConsumption = function(){
       $scope.param.consumption = false;
       $scope.param.consumptionNextStep = true;
  }
  $scope.balancePrepaid = function(){
      $scope.param.balancePrepaid = true;
      $scope.param.consumption = false;
  }
  $scope.relatedStaff = function(){
      $scope.param.relatedStaff = true;
      $scope.param.consumption = false;
  }
  $scope.giving = function(){
      $scope.param.giving = true;
      $scope.param.consumption = false;
  }
    givingChange && givingChange($scope)
}
