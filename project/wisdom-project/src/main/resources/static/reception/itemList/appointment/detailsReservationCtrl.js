function detailsReservation($scope,ngDialog){
    $scope.goConsumption = function(status){
        if(status == "去消费"){
            ngDialog.open({
                template: 'consumption',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                    ngDialog.close("detailsWrap")
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',
            });
        }else{
            ngDialog.open({
                template: 'scratchCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.close = function(type) {
                        if(type==0){
                            $scope.closeThisDialog();
                        }
                        else if(type == 3){
                            ngDialog.closeAll()
                        }
                        else{
                            ngDialog.open({
                                template: 'scratchCardSelectTreatmentCard',
                                scope: $scope, //这样就可以传递参数
                                controller: ['$scope', '$interval', function($scope, $interval) {

                                    console.log($scope.$parent.content);
                                    $scope.close = function(type) {
                                            $scope.closeThisDialog();
                                    };
                                }],
                                className: 'trueScratchCard ngdialog-theme-custom',
                            });
                        }
                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom',
            });
        }


    };
    $scope.startAppointment = function(){
        $scope.param.ModifyAppointment = false;
    };
  /*修改预约*/
    $scope.modifyingAppointment = function(){
        $scope.param.AppointmentType="长客";
        $scope.param.appointmentNew = "no";
        $scope.param.cancellationFlag = true;
        ngDialog.open({
            template: 'modifyingAppointment',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.cancellationFlag = true;
                $scope.close = function(type) {
                    $scope.closeThisDialog();
                    if(type == 3){

                    }
                };
            }],
            className: 'ngdialog-theme-default ngdialog-theme-custom'
        });

    }
    $scope.modifyingAppointmentIndivdual = function(){
        $scope.param.appointmentNew = "no";
        $scope.param.cancellationFlag = true;
        ngDialog.open({
            template: 'modifyingAppointment',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.cancellationFlag = true;
                $scope.close = function(status) {
                    $scope.closeThisDialog();
                };
            }],
            className: 'modifyingAppointment ngdialog-theme-custom'
        });
        $scope.param.AppointmentType="散客"
    };

    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog);

    /*选择顾客*/
    $scope.openCustomers = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.close = function() {
                   /* if(status == 0){
                        $scope.param.ModifyAppointmentObject.customer =""
                    }*/
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });
    }
    $scope.selectCustomersCtrl = function(){
        $scope.openCustomers()

    };
    $scope.selectTheCustomer = function(index,item){
        $scope.param.ModifyAppointmentObject.customerIndex = index;
        $scope.param.ModifyAppointmentObject.customer = item;
        if($scope.param.ModifyAppointmentObject.productNum == "0"){
            setTimeout(function(){
                $scope.selectNewProduct();
                ngDialog.close("selectCustomersWrap")
            },800)
        }




    }
    /*添加顾客*/
    $scope.addCustomersCtrl = function(){
        ngDialog.open({
            template: 'addCustomers',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });

    };
    /*选择项目*/
    $scope.selectNewProduct = function(){
        if($scope.param.ModifyAppointmentObject.customer == ""){
            $scope.openCustomers()
        }else{
            ngDialog.open({
                template: 'newProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    console.log($scope.$parent.content);
                    $scope.close = function(status) {
                        if(status == 1){
                            $scope.param.ModifyAppointmentObject.productData =[];
                            for(var i=0;i<$scope.param.ModifyAppointmentObject.productIndex.length;i++){
                                if($scope.param.ModifyAppointmentObject.productIndex[i]==true){
                                    $scope.param.ModifyAppointmentObject.productData.push($scope.param.ModifyAppointmentObject.productIndex[i])
                                }
                            }
                            $scope.param.ModifyAppointmentObject.productNum = $scope.param.ModifyAppointmentObject.productData.length;
                            ngDialog.close("selectCustomersWrap")
                        }else{
                            $scope.param.ModifyAppointmentObject.productNum = "0";
                            $scope.falseAll()
                            $scope.param.ModifyAppointmentObject.productData = []

                        }
                        $scope.closeThisDialog();

                    };
                }],
                className: 'newProject ngdialog-theme-custom'
            });
        }


    }
    /*选择时长*/
    $scope.selectTimeLength = function(){
        if($scope.param.ModifyAppointmentObject.customer == ""){
            $scope.openCustomers()
        }else {
            ngDialog.open({
                template: 'timeLength',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.param.timeLengthIndex = -1;
                    $scope.param.ModifyAppointmentObject.type = 'timeLength';
                    $scope.param.timeLengthArr = ["1", "1.5", "2", "2.5", "3", "3.5", "4"];

                    $scope.close = function (status) {
                        if(status == 0){
                            $scope.param.ModifyAppointmentObject.time =""
                        }
                        $scope.closeThisDialog();
                    };
                }],
                className: 'timeLength ngdialog-theme-custom'
            });
        }
    };
    /*选择美容师*/
    $scope.selectBeautician = function(){
        if($scope.param.ModifyAppointmentObject.customer == ""){
            $scope.openCustomers()
        }else {
            ngDialog.open({
                template: 'selectBeautician',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.param.timeLengthIndex = -1;
                    $scope.param.ModifyAppointmentObject.type = 'beautician';
                    $scope.param.timeLengthArr = ["李", "网", '周'];
                    $scope.close = function (status) {
                        if(status == 0){
                            $scope.param.ModifyAppointmentObject.beauticianName =""
                        }
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectBeautician ngdialog-theme-custom'
            });
        }
    }
    consumption && consumption($scope,ngDialog);
    scratchCard && scratchCard($scope,ngDialog);

}

