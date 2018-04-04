function searchConsumption($scope){
    var index = []
    $scope.searchConsumption = function(){
        console.log($scope.param.serachContent);
        /*for(var i=0;i<$scope.param.data.length;i++){
            for(var j=0;j<$scope.param.data[i].content.length;j++){
                if($scope.param.data[i].content[j].indexOf($scope.param.serachContent) !=-1){
                    index.push(i)
                }
            }
        }
        if(index.length>0){
            var arr =[]
            for(var i=0;i<index.length;i++){
                arr.push($scope.param.data[index[i]])
            }
            $scope.param.data = arr;
            console.log($scope.param.data);
            index= [];
        }*/
    }
    $scope.searchActive = function(index,index2){
        $scope.param.index = index;
        $scope.param.index2 = index2;
        $scope.active = "bgff6666";
        console.log($scope.param.index2)
    }
}