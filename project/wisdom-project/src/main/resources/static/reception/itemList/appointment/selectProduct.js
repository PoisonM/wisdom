function selectProductCtrl($scope,ngDialog){
    console.log(12323)
  $scope.ProductPic = function(index){
      console.log(12)
      if($scope.product[index] == true){
          $scope.selectProductDataPic[index]='images/bt_Single%20election_select.png';
          $scope.product[index] = false;
      }else{
          $scope.selectProductDataPic[index]='images/bt_Single%20election_nor_.png';
          $scope.product[index] = true;
      }
  }
}