
function selectTimeLengthCtrl ($scope,ngDialog){


    $scope.selectTimeLengthIndex = function(index,item){
        $scope.param.timeLengthIndex = index;

        if($scope.param.timeLengthObject.type =='beautician'){
            $scope.param.timeLengthObject.userName =item;
        }else{
            $scope.param.timeLengthObject.time = item;
        }
    }

}

