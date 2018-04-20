
function selectTimeLengthCtrl ($scope,ngDialog){
    $scope.selectTimeLengthIndex = function(index,item){
        $scope.param.timeLengthIndex = index;

        if($scope.param.ModifyAppointmentObject.type =='beautician'){
            $scope.param.ModifyAppointmentObject.beauticianName =item;
        }else{
            $scope.param.ModifyAppointmentObject.time = item;
            setTimeout(function(){
             ngDialog.close("timeLength");
            },600)
        }


    }

}

