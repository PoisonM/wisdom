function selectTreatmentCardCtrl ($scope){
  $scope.selectTreatment = function(index){
      console.log($scope.selectTreatmentDataPic)
      if($scope.treatment[index] == true){
          $scope.selectTreatmentDataPic[index]='images/bt_Single%20election_select.png';
          $scope.treatment[index] = false;
      }else{
          $scope.selectTreatmentDataPic[index]='images/bt_Single%20election_nor_.png';
          $scope.treatment[index] = true;

      }
  }
}
