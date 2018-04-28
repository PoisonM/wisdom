 function modifyingAppointmentPage ($scope,ngDialog){

     console.log(ngDialog);
     $scope.param.dayTime=[];
     $scope.param.curDate=new Date();

     $scope.bgf5f5f5 = "bgf5f5f5";
     var date={
         "startTime": "07:00",
         "endTime": "21:00",
         "scheduling":"20,21,22"
     };
    /* var a = $filter("date")(Date.parse($scope.param.ModifyAppointmentObject.appointStartTime),"yyyy-MM-dd");
     console.log(a);*/

     $scope.param.ModifyAppointmentObject.hoursType=[];
     $scope.param.ModifyAppointmentObject.hoursTime=[];
     for(var i=0;i<$scope.param.code.length;i++){
         $scope.param.ModifyAppointmentObject.hoursType[i]="0";
         for(key in $scope.param.code[i] ){
             for (var k = 0;k <date.scheduling.split(",").length; k++) {
                 if(date.scheduling.split(",")[k]==key){
                     $scope.param.ModifyAppointmentObject.hoursType[i]="1";
                 }
             }
             $scope.param.ModifyAppointmentObject.hoursTime[i] = $scope.param.code[i][key];
             if($scope.param.code[i][key] == date.startTime ){
                 var a= i;
             }
             if($scope.param.code[i][key] == date.endTime ){
                 var b= i;
             }
         }
     }

     $scope.param.ModifyAppointmentObject.hoursTimeShow=$scope.param.ModifyAppointmentObject.hoursTime.slice(a,b+1);
     $scope.param.selectedTime = $scope.param.ModifyAppointmentObject.hoursType.slice(a,b+1);

     for(var i=1;i<=6;i++){
         nextDate = new Date($scope.param.curDate.getTime() +  24*60*60*1000*i); //后一天
         $scope.param.dayTime.push(nextDate)
     }
     /*默认选择时间段*/
     var time=$scope.param.ModifyAppointmentObject.appointPeriod/1/0.5;
     $scope.timeLength = $scope.param.ModifyAppointmentObject.appointPeriod/1/0.5;
     var numTime = [];
     var timeIntervalArr = [];
     for(var i=0;i<$scope.param.selectedTime.length;i++){
         if($scope.param.selectedTime[i] !=1){
            for(var j=1;j<time;j++){
                if($scope.param.selectedTime[i] ==$scope.param.selectedTime[i+j]){
                    numTime.push(i)
                    for(var j=0;j<time;j++){
                        $scope.param.selectedTime[numTime[0]+j] ="2";
                        for(var i=0;i<$scope.param.code.length;i++){
                            for(key in $scope.param.code[i] ){
                                if($scope.param.code[i][key] == $scope.param.ModifyAppointmentObject.hoursTimeShow[numTime[0]+j] ){
                                    timeIntervalArr.push($scope.param.code[i][key])
                                }
                            }
                        }
                    }
                }
            }
         }
     }
     $scope.param.ModifyAppointmentObject.appointStartTime=timeIntervalArr[0];
     $scope.param.ModifyAppointmentObject.appointEndTime=timeIntervalArr[timeIntervalArr.length-1]
     /*选择时间（天为单位）*/
     $scope.selectDayTime = function(index){
         $scope.bgff6666 = 'bgff6666';
         $scope.index = index;
     };
     /*选择时间（半小时为单位）*/
     $scope.selectTime = function(index,NoOrYes){
         var timeIntervalArr = [];
         $scope.param.selectedTime = $scope.param.ModifyAppointmentObject.hoursType.slice(a,b+1);
         if($scope.param.ModifyAppointmentObject.time == ""){
             $scope.selectTimeLength()
         }
         if(NoOrYes == 1)return;
         $scope.bgff9b9b = 'bgff9b9b';
         $scope.index1 = index;
         var time=$scope.param.ModifyAppointmentObject.appointPeriod/1/0.5;
         for(var i=index;i<time+index;i++){
             if($scope.param.selectedTime[i] ==1){
                 alert("请重新选择时间");
                 $scope.param.selectedTime = $scope.param.ModifyAppointmentObject.hoursType.slice(a,b+1);
                 return;
             }else{

                 $scope.param.selectedTime[i] = "2";
                 for(var j=0;j<$scope.param.code.length;j++){
                     for(key in $scope.param.code[j] ){
                         if($scope.param.code[j][key] == $scope.param.ModifyAppointmentObject.hoursTimeShow[i] ){
                             timeIntervalArr.push($scope.param.code[j][key])
                         }
                     }
                 }



             }
         }
         $scope.param.ModifyAppointmentObject.appointStartTime=timeIntervalArr[0];
         $scope.param.ModifyAppointmentObject.appointEndTime=timeIntervalArr[timeIntervalArr.length-1]

     };

     selectCustomersCtrl && selectCustomersCtrl($scope,ngDialog);/*选择顾客*/
     selectProductCtrl && selectProductCtrl($scope,ngDialog);
     selectTimeLengthCtrl && selectTimeLengthCtrl($scope,ngDialog);
 }
