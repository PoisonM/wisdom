function givingChange($scope){
    $scope.param.givingProduct = true;
    console.log($scope.param.givingProduct)
    $scope.givingChange = function(index){
        $scope.param.givingIndex = index;
        if(index == 2){
            $scope.param.givingVouchers = true;
            $scope.param.givingProduct = false;
        }else{
            $scope.param.givingVouchers = false;
            $scope.param.givingProduct = true;
        }
    }
}
