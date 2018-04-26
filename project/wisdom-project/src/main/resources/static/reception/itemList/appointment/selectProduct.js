function selectProductCtrl($scope,ngDialog){
  $scope.ProductPic = function(index){
      if($scope.product[index] == true){
          $scope.selectProductDataPic[index]='images/bt_Single%20election_select.png';
          $scope.product[index] = false;
      }else{
          $scope.selectProductDataPic[index]='images/bt_Single%20election_nor_.png';
          $scope.product[index] = true;
      }
  }
}