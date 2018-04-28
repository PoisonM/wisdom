function detailsReservation($scope,ngDialog,GetUserProjectGroupList,GetUserProductList,GetUserCourseProjectList,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject,GetUserShopProjectList,ConsumeCourseCard,GetShopClerkList,GetAppointmentInfoById,$filter){

    $scope.SingeTreatmentCardProductCollectionCard = function(){
        /*{
            sysUserId:$scope.param.consumptionObj.sysUserId,
            sysShopId:$scope.param.consumptionObj.sysShopId ,
            cardStyle:1
        }*/   /*单次参数*/
        GetUserCourseProjectList.get({
            sysUserId:'1',
            cardStyle:"1"
        },function(data){
            $scope.param.consumptionObj.singleByUserId = data.responseData;
            if($scope.param.consumptionObj.singleByUserId.length<=0){
                $scope.param.consumptionObj.singleByUserIdFlag = false;
            }
        })

        /*{
         sysUserId:$scope.param.consumptionObj.sysUserId,
         sysShopId:$scope.param.consumptionObj.sysShopId ,
         cardStyle:0
         }*/   /*疗程卡参数*/
        GetUserCourseProjectList.get({
            sysUserId:'1',
            cardStyle:"0"
        },function(data){
            $scope.param.consumptionObj.treatmentCardByUserId = data.responseData;
            /*if($scope.param.consumptionObj.treatmentCardByUserId.length<=0){
                $scope.param.consumptionObj.treatmentCardByUserIdFlag = false;
            }*/

        })

       /* {
            sysUserId:$scope.param.consumptionObj.sysUserId,
            sysShopId:$scope.param.consumptionObj.sysShopId ,
        }*//* 查询某个用户的产品信息*/
        GetUserProductList.get({
            sysUserId:"1",
            sysShopId:"1"
        },function(data){
            $scope.param.consumptionObj.productByUserId = data.responseData;
            if($scope.param.consumptionObj.productByUserId.length<=0){
                $scope.param.consumptionObj.productByUserIdFlag = false;
            }
        })
       /* http://localhost:9051/productInfo/getUserProductList*/

        /*{
         sysUserId:$scope.param.consumptionObj.sysUserId,
         sysShopId:$scope.param.consumptionObj.sysShopId ,
         }*//*查询某个用户的套卡信息*/
        GetUserProjectGroupList.get({
            sysUserId:"1"
        },function (data) {
            $scope.param.consumptionObj.collectionCardByUserId = data.responseData;
            if($scope.param.consumptionObj.collectionCardByUserId.length<=0){
                $scope.param.consumptionObj.collectionCardByUserIdFlag = false;
            }
        })

    }
    $scope.goConsumption = function(status){
        if(status == "消费"){
            ngDialog.open({
                template: 'consumption',
                scope: $scope,
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.SingeTreatmentCardProductCollectionCard();
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
                    var scratchCard = $scope.param.individualTravelerAppointmentObj.individualTravelerAppointment.punchCard;
                    var scratchCardArr = [];
                    console.log(scratchCard);
                   /* for(var i=0;i<scratchCard.length;i++){
                        scratchCardArr[i]=scratchCard[i].
                    }*/
                    GetUserShopProjectList.save({
                        relationIds:["1","2"]
                    },function(data){
                        for(var i=0;i<data.responseData.length;i++){
                            data.responseData[i].num = 1
                        }
                        $scope.param.scratchCardObj.scratchCardData=data.responseData;

                        $scope.scratchCardNextStep = function(){
                            var shopUserConsumeDTO = {
                                clerkId:[],/*操作店员id*/
                                consumeId:[],/*用户与项目表主键，即需要划卡记录的主键  id*/
                                consumeNum:[],/*划掉数量*/
                                consumePrice:[],/*	划掉的价格*/
                                sysUserId:[]/*	*/
                            }
                            var scratchCardDataAll = $scope.param.scratchCardObj.scratchCardData;
                            for(var i=0;i<scratchCardDataAll.length;i++){
                               /* shopUserConsumeDTO.clerkId[i]=scratchCardDataAll[i].sysClerkId;
                                shopUserConsumeDTO.consumeId[i]=scratchCardDataAll[i].id;
                                shopUserConsumeDTO.consumeNum[i]=scratchCardDataAll[i].num;
                                shopUserConsumeDTO.consumePrice[i]=(scratchCardDataAll[i].sysShopProjectInitAmount/1)*(scratchCardDataAll[i].num/1);
                                shopUserConsumeDTO.sysUserId[i]=scratchCardDataAll[i].sysUserId;*/
                                shopUserConsumeDTO.clerkId[0]=1;
                                shopUserConsumeDTO.consumeId[0]="5b080f1a39634d4eb3b9bc82130402e5";
                                shopUserConsumeDTO.consumeNum[0]=12;
                                shopUserConsumeDTO.consumePrice[0]=100;
                                shopUserConsumeDTO.sysUserId[0]=110;

                            }
                            ConsumeCourseCard.save(shopUserConsumeDTO,function(data){

                            })
                        }
                       /* scratchCard && scratchCard($scope,ngDialog,ConsumeCourseCard);/!*划卡*!/*/

                    });

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
    modifyingAppointmentPage && modifyingAppointmentPage ($scope,ngDialog,$filter);



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
    consumption && consumption($scope,ngDialog,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject,GetUserShopProjectList,ConsumeCourseCard,GetShopClerkList);


}

