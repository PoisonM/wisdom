
function selectTimeLengthCtrl ($scope,ngDialog){
    $scope.selectTimeLengthIndex = function(index,item){
        $scope.param.timeLengthPic = index;
        $scope.param.ModifyAppointmentObject.appointPeriod = item;
        setTimeout(function(){
            ngDialog.close("timeLength");
        },600)

    }
    $scope.selectbeauticianIndex = function(index,beauticianName){
        $scope.param.beauticianIndex = index;
        $scope.param.ModifyAppointmentObject.beauticianName =beauticianName;
        setTimeout(function(){
            ngDialog.close("timeLength");
        },600)

    }
}

