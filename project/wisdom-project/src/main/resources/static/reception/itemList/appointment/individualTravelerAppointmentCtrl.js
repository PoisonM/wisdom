function individualTravelerAppointment ($scope,ngDialog,UpdateAppointmentInfoById){
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
           shopAppointServiceId:"1",
           status:2
       },function(data){

       })
   };
    $scope.startSevier = function(){
        $scope.seriverColor=false;
        UpdateAppointmentInfoById.get({
            shopAppointServiceId:"1",
            status:3
        },function(data){

        })
    }
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog)
}

