function collectionCardCtrl ($scope){
  $scope.CollectionCardPic = function(index){
      console.log($scope)
      if($scope.collectionCard[index] == true){
          $scope.selectCollectionCardDataPic[index]='images/bt_Single%20election_select.png';
          $scope.collectionCard[index] = false;
      }else{
          $scope.selectCollectionCardDataPic[index]='images/bt_Single%20election_nor_.png';
          $scope.collectionCard[index] = true;

      }
  }
}
