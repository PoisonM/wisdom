
function selectTimeLengthCtrl ($scope){


    $scope.selectTimeLength = function(){
        $scope.param.timeLengthIndex = -1;
        $scope.param.timeLengthArr=["1","1.5","2","2.5","3","3.5","4"];
        $scope.param.timeLength = true;
        $scope.param.modifyingAppointment = false;
    };
    $scope.selectBeautician = function(){
        $scope.param.timeLengthIndex = -1;
        $scope.param.timeLengthArr = ["李","网",'周'];
        $scope.param.selectBeautician = true;
        $scope.param.modifyingAppointment = false;
    };
    $scope.selectTimeLengthIndex = function(index,item){
        $scope.param.timeLengthIndex = index;
        $scope.param.timeLengthObject.time = item;

    }

}

