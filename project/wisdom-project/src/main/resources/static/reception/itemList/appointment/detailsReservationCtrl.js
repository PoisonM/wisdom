function detailsReservation($scope,ngDialog){
    var singleByUserId  = {
        "result":"0x00001",
        "errorInfo":null,
        "responseData":[
            {
                "id":"2",
                "shopAppointmentId":"1",
                "sysUserId":"2",
                "useStyle":"2",
                "sysShopId":"2",
                "sysShopName":"汉方美容院2",
                "sysShopProjectId":"2",
                "sysShopProjectName":"汉方项目2",
                "sysShopProjectInitAmount":"123",
                "sysShopProjectSurplusAmount":null,
                "sysShopProjectSurplusTimes":null,
                "sysShopProjectInitTimes":null,
                "createBy":null,
                "createDate":null,
                "updateUser":null,
                "updateDate":null
            }
        ]
    };
    var treatmentCardByUserId = {
        "result":"0x00001",
        "errorInfo":null,
        "responseData":[
            {
                "id":"2",
                "shopAppointmentId":"1",
                "sysUserId":"2",
                "useStyle":"2",
                "sysShopId":"2",
                "sysShopName":"汉方美容院2",
                "sysShopProjectId":"2",
                "sysShopProjectName":"汉方项目2",
                "sysShopProjectInitAmount":"123",
                "sysShopProjectSurplusAmount":null,
                "sysShopProjectSurplusTimes":null,
                "sysShopProjectInitTimes":null,
                "createBy":null,
                "createDate":null,
                "updateUser":null,
                "updateDate":null
            }
        ]
    };

    $scope.SingeTreatmentCardProductCollectionCard = function(){
        /*{
            sysUserId:$scope.param.consumptionObj.sysUserId,
            sysShopId:$scope.param.consumptionObj.sysShopId ,
            cardStyle:1
        }*/   /*单次参数*/
        $scope.param.consumptionObj.singleByUserId = singleByUserId.responseData;
        /*{
         sysUserId:$scope.param.consumptionObj.sysUserId,
         sysShopId:$scope.param.consumptionObj.sysShopId ,
         cardStyle:0
         }*/   /*疗程卡参数*/
        $scope.param.consumptionObj.treatmentCardByUserId = treatmentCardByUserId.responseData;


    }
    $scope.goConsumption = function(status){
        if(status == "消费"){
            ngDialog.open({
                template: 'consumption',
                scope: $scope,
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.SingeTreatmentCardProductCollectionCard()
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
        /*$scope.param.AppointmentType="长客";
        $scope.param.appointmentNew = "no";*/
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
            className: 'ngdialog-theme-default ngdialog-theme-custom'
        });

    }
    /*$scope.modifyingAppointmentIndivdual = function(){
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
*/
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog);



    /*选择时长*/
    $scope.selectTimeLength = function(){
        if($scope.param.selectCustomersObject.sysUserName == ""){
            $scope.selectCustomersCtrl()
        }else {
            ngDialog.open({
                template: 'timeLength',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.param.timeLengthIndex = -1;
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
    var selectBeauticianData={
        "errorInfo": 1,
            "responseData": [
            {
                "address": 1,
                "delFlag": 1,
                "email": "12",
                "id": "2",
                "identifyNumber": 1,
                "loginDate": 1,
                "loginIp": 1,
                "mobile": 1,
                "name": "安迪",
                "nickname": "安迪",
                "password": "123",
                "photo": 1,
                "sysBossId": "3",
                "sysBossName": "老板2",
                "sysShopId": "11",
                "sysUserId": "2",
                "userOpenid": 1,
                "userType": 1,
                "weixinAttentionStatus": 1
            }
        ],
            "result": "0x00001"
    }
    $scope.selectBeautician = function(){
        if($scope.param.selectCustomersObject.sysUserName == ""){
            $scope.selectCustomersCtrl()
        }else {
            ngDialog.open({
                template: 'selectBeautician',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function ($scope, $interval) {
                    $scope.param.timeLengthIndex = -1;
                    $scope.param.timeLength = selectBeauticianData.responseData;

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

