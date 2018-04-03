function givingChange($scope){
    $scope.givingChange = function(index){
        $scope.param.givingIndex = index;
        console.log($scope.param.givingIndex)
        if(index == 2){
            $scope.param.givingVouchers = true;
            $scope.param.givingProduct = false;
        }else{
            $scope.param.givingVouchers = false;
            $scope.param.givingProduct = true;
        }
    }
}
