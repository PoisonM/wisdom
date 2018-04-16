function detailsReservation($scope,ngDialog){
    $scope.goConsumption = function(status){
        $scope.ngDialog = ngDialog;
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
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog);

    /*选择顾客*/
    $scope.selectCustomersCtrl = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });

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

    }
    /*选择项目*/
    $scope.selectNewProduct = function(){
        ngDialog.open({
            template: 'newProduct',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });

    }
    /*选择时长*/
    $scope.selectTimeLength = function(){
        ngDialog.open({
            template: 'timeLength',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.timeLengthIndex = -1;
                $scope.param.timeLengthObject.type ='timeLength';
                $scope.param.timeLengthArr=["1","1.5","2","2.5","3","3.5","4"];

                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'timeLength ngdialog-theme-custom'
        });
    };
    /*选择美容师*/
    $scope.selectBeautician = function(){
        ngDialog.open({
            template: 'selectBeautician',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.timeLengthIndex = -1;
                $scope.param.timeLengthObject.type ='beautician';
                $scope.param.timeLengthArr = ["李","网",'周'];
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'selectBeautician ngdialog-theme-custom'
        });
    }

    consumption && consumption($scope,ngDialog);
    scratchCard && scratchCard($scope,ngDialog);

}

