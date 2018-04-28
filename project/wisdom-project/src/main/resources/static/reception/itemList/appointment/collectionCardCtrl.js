function collectionCardCtrl ($scope,GetShopProjectGroups){
    $scope.searchCollectionCard = function(){
        GetShopProjectGroups.get({
            projectGroupName:$scope.param.consumptionObj.singleFilterStr,
            pageSize:100
        },function(data){
            $scope.param.consumptionObj.collectionCardByShowId= data.responseData;
            for(var i=0;i<$scope.param.consumptionObj.collectionCardByShowId.length;i++){
                $scope.selectCollectionCardDataPic[i] = 'images/bt_Single%20election_nor_.png';
                $scope.collectionCard[i] = true;
            }
        })
    }
  $scope.CollectionCardPic = function(index){
      if($scope.collectionCard[index] == true){
          $scope.selectCollectionCardDataPic[index]='images/bt_Single%20election_select.png';
          $scope.collectionCard[index] = false;
      }else{
          $scope.selectCollectionCardDataPic[index]='images/bt_Single%20election_nor_.png';
          $scope.collectionCard[index] = true;

      }
  }
}
