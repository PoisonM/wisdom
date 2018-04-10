 function modifyingAppointmentPage ($scope,ngDialog){
     $scope.param.dayTime=[];
     $scope.param.curDate=new Date();
     $scope.param.time=["09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:30","21:00","21:30","22:00","22:30","23:00","23:30","a"]
     $scope.param.selectedTime = ["1","0","1","1","1","0","0","0","0","0","0","0","0","0","0","0","0"];
     $scope.bgf5f5f5 = "bgf5f5f5";
     for(var i=1;i<=6;i++){
         nextDate = new Date($scope.param.curDate.getTime() +  24*60*60*1000*i); //后一天
         $scope.param.dayTime.push(nextDate)
     }


     $scope.modifyingAppointment = function(){
         $scope.param.AppointmentType="长客"
         ngDialog.open({
             template: 'modifyingAppointment',
             scope: $scope, //这样就可以传递参数
             controller: ['$scope', '$interval', function($scope, $interval) {
                 $scope.close = function() {
                     $scope.closeThisDialog();
                 };
             }],
             className: 'modifyingAppointment ngdialog-theme-custom'
         });

     }
     $scope.modifyingAppointmentIndivdual = function(){
         ngDialog.open({
             template: 'modifyingAppointment',
             scope: $scope, //这样就可以传递参数
             controller: ['$scope', '$interval', function($scope, $interval) {
                 $scope.close = function() {
                     $scope.closeThisDialog();
                 };
             }],
             className: 'modifyingAppointment ngdialog-theme-custom'
         });
         $scope.param.AppointmentType="散客"
     };
     /*选择时间（天为单位）*/
     $scope.selectDayTime = function(index){
         $scope.bgff6666 = 'bgff6666';
         $scope.index = index;
     };
     /*选择时间（半小时为单位）*/
     $scope.selectTime = function(index,NoOrYes){
         $scope.param.selectedTime = ["1","0","1","1","1","0","0","0","0","0","0","0","0","0","0","0","0"];
         if($scope.param.timeLengthObject.time == ""){
             $scope.selectTimeLength()
         }
         if(NoOrYes == 1)return;
         $scope.bgff9b9b = 'bgff9b9b';
         $scope.index1 = index;
         var time=$scope.param.timeLengthObject.time/1/0.5;
         for(var i=index;i<time+index;i++){
             if($scope.param.selectedTime[i] ==1){
                 alert("请重新选择时间");
                 $scope.param.selectedTime = ["1","0","1","1","1","0","0","0","0","0","0","0","0","0","0","0","0"];
                 return;
             }else{
                 $scope.param.selectedTime[i] = "2";
             }
         }
     };
     selectCustomersCtrl && selectCustomersCtrl($scope,ngDialog);/*选择顾客*/
     selectProductCtrl && selectProductCtrl($scope,ngDialog);
     selectTimeLengthCtrl && selectTimeLengthCtrl($scope,ngDialog);
 }
