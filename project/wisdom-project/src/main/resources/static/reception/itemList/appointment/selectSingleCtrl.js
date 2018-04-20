function selectSingle ($scope){
    $scope.selectSingleData = [0,1];
    $scope.selectSingleDataPic =[];
    var single =[];
    for(var i=0;i<$scope.selectSingleData.length;i++){
        $scope.selectSingleDataPic[i] = 'images/bt_Single%20election_nor_.png';
        single[i] = true;
    }
    $scope.selectSingle = function(index){
        for(var i=0;i<$scope.selectSingleData.length;i++){
            $scope.selectSingleDataPic[i] = 'images/bt_Single%20election_nor_.png';
        }
        if(single[index] == true){
            $scope.selectSingleDataPic[index]='images/bt_Single%20election_select.png';
            single[index] = false;
        }else{
            $scope.selectSingleDataPic[index]='images/bt_Single%20election_nor_.png';
            single[index] = true;

        }

    }


}