function scratchCard ($scope,ngDialog,ConsumeCourseCard,GetShopClerkList){
  $scope.minusOrAddScratchCardNum = function(type,index){
      if(type==0){
          if($scope.param.scratchCardObj.scratchCardData[index].num <=1)return;
          $scope.param.scratchCardObj.scratchCardData[index].num--;
      }else{
          $scope.param.scratchCardObj.scratchCardData[index].num++
      }
  }
$scope.candelScratchCard = function(index){
    $scope.param.scratchCardObj.scratchCardData.splice(index,1)
}

}

