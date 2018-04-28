function selectSingleCtrl ($scope){

    console.log($scope)
    $scope.selectSingle = function(index){
        if($scope.single[index] == true){
            $scope.selectSingleDataPic[index]='images/bt_Single%20election_select.png';
            $scope.single[index] = false;
        }else{
            $scope.selectSingleDataPic[index]='images/bt_Single%20election_nor_.png';
            $scope.single[index] = true;

        }

    }


}