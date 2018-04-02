
PADWeb.controller("modifyingAppointmentCtrl", function($scope, $state, $stateParams) {
    console.log("modifyingAppointment");
    $scope.param = {
        dayTime:[],
        curDate:new Date(),
        time : ["09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:30","21:00","21:30","22:00","22:30","23:00","23:30","a"]
    };
    for(var i=1;i<=6;i++){
        nextDate = new Date($scope.param.curDate.getTime() +  24*60*60*1000*i); //后一天
        $scope.param.dayTime.push(nextDate)
    }
    $scope.selectDayTime = function(index){
         $scope.bgff6666 = 'bgff6666';
         $scope.index = index;
    }
    $scope.selectTime = function(index){
        $scope.bgff9b9b = 'bgff9b9b';
        $scope.index1 = index;
    }



});