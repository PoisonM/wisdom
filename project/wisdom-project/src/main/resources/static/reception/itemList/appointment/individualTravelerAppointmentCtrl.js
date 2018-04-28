function individualTravelerAppointment ($scope,ngDialog){
   /* $scope.startAppointmentIndivdual = function(){
        $scope.param.ModifyAppointment = false;
    };
    $scope.modifyingAppointmentIndivdual = function(){
        $scope.param.modifyingAppointment = true;
        $scope.param.individualTravelerAppointment = false;
        $scope.param.AppointmentType="散客"
    };*/
   $scope.startAppointmentIndivdual = function(){
       $scope.param.ModifyAppointment = false;
       UpdateAppointmentInfoById.get({
           shopAppointServiceId:id,
           status:0
       },function(data){

       })
   };
    $scope.startSevier = function(){
        $scope.seriverColor=false;
        UpdateAppointmentInfoById.get({
            shopAppointServiceId:id,
            status:0
        },function(data){

        })
    }
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog)
}

