function consumption($scope,ngDialog){

    $scope.ngDialog = ngDialog;
    $scope.selsectConsumption = function(type){
        $scope.param.consumptionObj.consumptionType = type;
        if(type == "selectSingle"){/*单次*/
            ngDialog.open({
                template: 'selectSingle',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    /*{
                        filterStr:$acope.consumptionObj.singleFilterStr,
                        useStyle:"1"
                    }*/
                    var singleByshopId = {
                        "errorInfo":1,
                        "responseData":{
                            "detailLevel":[
                                {
                                    "面部":{
                                        "脚部按摩":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"3",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"1",
                                            "projectDuration":30,
                                            "projectName":"足疗",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"足疗",
                                            "projectTypeTwoId":"4",
                                            "projectTypeTwoName":"脚部按摩",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        },
                                        "面部":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"4",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"2",
                                            "projectDuration":30,
                                            "projectName":"面部补水",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"面部",
                                            "projectTypeTwoId":"2",
                                            "projectTypeTwoName":"面部",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        }
                                    },
                                    "twoLevelSize":2
                                },
                                {
                                    "面部":{
                                        "脚部按摩":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"3",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"1",
                                            "projectDuration":30,
                                            "projectName":"足疗",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"足疗",
                                            "projectTypeTwoId":"4",
                                            "projectTypeTwoName":"脚部按摩",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        },
                                        "面部":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"4",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"2",
                                            "projectDuration":30,
                                            "projectName":"面部补水",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"面部",
                                            "projectTypeTwoId":"2",
                                            "projectTypeTwoName":"面部",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        }
                                    },
                                    "twoLevelSize":1
                                }
                            ],
                            "detailProject":[
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"1",
                                    "id":"3",
                                    "productType":"1",
                                    "projectDuration":30,
                                    "projectName":"足疗",
                                    "projectTypeOneId":"1",
                                    "projectTypeOneName":"足疗",
                                    "projectTypeTwoId":"4",
                                    "projectTypeTwoName":"脚部按摩",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"1"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"1",
                                    "id":"4",
                                    "productType":"2",
                                    "projectDuration":30,
                                    "projectName":"面部补水",
                                    "projectTypeOneId":"1",
                                    "projectTypeOneName":"面部",
                                    "projectTypeTwoId":"2",
                                    "projectTypeTwoName":"面部",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"1"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"null",
                                    "id":"5",
                                    "productType":"null",
                                    "projectDuration":30,
                                    "projectName":"腿部水光针",
                                    "projectTypeOneId":"3",
                                    "projectTypeOneName":"腿部",
                                    "projectTypeTwoId":"5",
                                    "projectTypeTwoName":"大腿",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"null"
                                }
                            ]
                        },
                        "result":"0x00001"
                    }
                    $scope.param.consumptionObj.singleByshopId.detailLevel = singleByshopId.responseData.detailLevel;/*一二级数据*/



                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom'
            });
        }else if (type=="selectTreatmentCard"){/*疗程卡*/
            ngDialog.open({
                template: 'selectTreatmentCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    /*{
                     filterStr:$acope.consumptionObj.singleFilterStr,
                     useStyle:"0"
                     }*/
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    var singleByshopId = {
                        "errorInfo":1,
                        "responseData":{
                            "detailLevel":[
                                {
                                    "面部":{
                                        "脚部按摩":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"3",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"1",
                                            "projectDuration":30,
                                            "projectName":"足疗",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"足疗",
                                            "projectTypeTwoId":"4",
                                            "projectTypeTwoName":"脚部按摩",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        },
                                        "面部":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"4",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"2",
                                            "projectDuration":30,
                                            "projectName":"面部补水",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"面部",
                                            "projectTypeTwoId":"2",
                                            "projectTypeTwoName":"面部",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        }
                                    },
                                    "twoLevelSize":2
                                },
                                {
                                    "面部":{
                                        "脚部按摩":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"3",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"1",
                                            "projectDuration":30,
                                            "projectName":"足疗",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"足疗",
                                            "projectTypeTwoId":"4",
                                            "projectTypeTwoName":"脚部按摩",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        },
                                        "面部":{
                                            "cardType":"1",
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "functionIntr":1,
                                            "id":"4",
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "maxContainTimes":1,
                                            "oncePrice":1,
                                            "productType":"2",
                                            "projectDuration":30,
                                            "projectName":"面部补水",
                                            "projectTypeOneId":"1",
                                            "projectTypeOneName":"面部",
                                            "projectTypeTwoId":"2",
                                            "projectTypeTwoName":"面部",
                                            "projectUrl":1,
                                            "status":"0",
                                            "sysBossId":"11",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1,
                                            "useStyle":"1",
                                            "visitDateTime":1
                                        }
                                    },
                                    "twoLevelSize":1
                                }
                            ],
                            "detailProject":[
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"1",
                                    "id":"3",
                                    "productType":"1",
                                    "projectDuration":30,
                                    "projectName":"足疗",
                                    "projectTypeOneId":"1",
                                    "projectTypeOneName":"足疗",
                                    "projectTypeTwoId":"4",
                                    "projectTypeTwoName":"脚部按摩",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"1"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"1",
                                    "id":"4",
                                    "productType":"2",
                                    "projectDuration":30,
                                    "projectName":"面部补水",
                                    "projectTypeOneId":"1",
                                    "projectTypeOneName":"面部",
                                    "projectTypeTwoId":"2",
                                    "projectTypeTwoName":"面部",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"1"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "discountPrice":1,
                                    "functionIntr":1,
                                    "isDisplay":1,
                                    "marketPrice":1,
                                    "maxContainTimes":1,
                                    "oncePrice":1,
                                    "projectUrl":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "visitDateTime":1,
                                    "cardType":"null",
                                    "id":"5",
                                    "productType":"null",
                                    "projectDuration":30,
                                    "projectName":"腿部水光针",
                                    "projectTypeOneId":"3",
                                    "projectTypeOneName":"腿部",
                                    "projectTypeTwoId":"5",
                                    "projectTypeTwoName":"大腿",
                                    "status":"0",
                                    "sysBossId":"11",
                                    "sysShopId":"11",
                                    "useStyle":"null"
                                }
                            ]
                        },
                        "result":"0x00001"
                    }
                    $scope.param.consumptionObj.singleByshopId.detailLevel = singleByshopId.responseData.detailLevel;/*一二级数据*/
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="selectProduct"){/*产品*/
            ngDialog.open({
                template: 'selectProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    var productData = {
                        "errorInfo":1,
                        "responseData":{
                            "detailLevel":[
                                {
                                    "水光针一级名称":{
                                        "水光针二级名称":{
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "id":"3",
                                            "introduce":1,
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "productCode":1,
                                            "productFunction":1,
                                            "productName":"水光针",
                                            "productPosition":1,
                                            "productSpec":1,
                                            "productType":1,
                                            "productTypeOneId":"1",
                                            "productTypeOneName":"水光针一级名称",
                                            "productTypeTwoId":"2",
                                            "productTypeTwoName":"水光针二级名称",
                                            "productUnit":1,
                                            "productUrl":1,
                                            "productWarningDay":1,
                                            "productWarningNum":1,
                                            "qrCodeUrl":1,
                                            "qualityPeriod":1,
                                            "status":"0",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1
                                        }
                                    }
                                },
                                {
                                    "水光针一级名称":{
                                        "水光针二级名称":{
                                            "createBy":1,
                                            "createDate":1,
                                            "discountPrice":1,
                                            "id":"3",
                                            "introduce":1,
                                            "isDisplay":1,
                                            "marketPrice":1,
                                            "productCode":1,
                                            "productFunction":1,
                                            "productName":"水光针",
                                            "productPosition":1,
                                            "productSpec":1,
                                            "productType":1,
                                            "productTypeOneId":"1",
                                            "productTypeOneName":"水光针一级名称",
                                            "productTypeTwoId":"2",
                                            "productTypeTwoName":"水光针二级名称",
                                            "productUnit":1,
                                            "productUrl":1,
                                            "productWarningDay":1,
                                            "productWarningNum":1,
                                            "qrCodeUrl":1,
                                            "qualityPeriod":1,
                                            "status":"0",
                                            "sysShopId":"11",
                                            "updateDate":1,
                                            "updateUser":1
                                        }
                                    }
                                }
                            ],
                            "detailProduct":[
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "introduce":1,
                                    "isDisplay":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "discountPrice":300,
                                    "id":"1",
                                    "marketPrice":300,
                                    "productCode":"2222",
                                    "productFunction":"功效",
                                    "productName":"产品名称",
                                    "productPosition":"产品适用部位",
                                    "productSpec":"规格",
                                    "productType":"3",
                                    "productTypeOneId":"2",
                                    "productTypeOneName":"产品一级名字",
                                    "productTypeTwoId":"3",
                                    "productTypeTwoName":"产品二级名字",
                                    "productUnit":111,
                                    "productUrl":"www.baidu.com",
                                    "productWarningDay":30,
                                    "productWarningNum":30,
                                    "qrCodeUrl":"二维产品地址",
                                    "qualityPeriod":30,
                                    "status":"0",
                                    "sysShopId":"11"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "introduce":1,
                                    "isDisplay":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "id":"2",
                                    "productCode":"null",
                                    "productFunction":"null",
                                    "productName":"名",
                                    "productPosition":"null",
                                    "productSpec":"null",
                                    "productType":"null",
                                    "productTypeOneId":"2",
                                    "productTypeOneName":"产品一级名字",
                                    "productTypeTwoId":"3",
                                    "productTypeTwoName":"产品二级名字",
                                    "productUrl":"null",
                                    "qrCodeUrl":"null",
                                    "status":"0",
                                    "sysShopId":"11"
                                },
                                {
                                    "createBy":1,
                                    "createDate":1,
                                    "introduce":1,
                                    "isDisplay":1,
                                    "updateDate":1,
                                    "updateUser":1,
                                    "discountPrice":300,
                                    "id":"3",
                                    "marketPrice":300,
                                    "productCode":"null",
                                    "productFunction":"null",
                                    "productName":"水光针",
                                    "productPosition":"null",
                                    "productSpec":"null",
                                    "productType":"null",
                                    "productTypeOneId":"1",
                                    "productTypeOneName":"水光针一级名称",
                                    "productTypeTwoId":"2",
                                    "productTypeTwoName":"水光针二级名称",
                                    "productUnit":111,
                                    "productUrl":"null",
                                    "productWarningDay":30,
                                    "productWarningNum":30,
                                    "qrCodeUrl":"null",
                                    "qualityPeriod":30,
                                    "status":"0",
                                    "sysShopId":"11"
                                }
                            ]
                        },
                        "result":"0x00001"
                   };
                    $scope.param.consumptionObj.singleByshopId.detailLevel = productData.responseData.detailLevel;
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="collectionCard"){/*套卡*/
            ngDialog.open({
                template: 'collectionCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    /*{
                     projectGroupName:"",
                     pageSize:100
                     }*//*参数*/
                    $scope.selectCollectionCardDataPic =[];
                    $scope.collectionCard =[]
                    var collectionCardByShowId= {
                        "errorInfo": 1,
                            "responseData": [
                            {
                                "createBy": 1,
                                "createDate": 1,
                                "detail": 1,
                                "discountPrice": 1,
                                "marketPrice": 1,
                                "projectGroupUrl": 1,
                                "status": 1,
                                "updateDate": 1,
                                "updateUser": 1,
                                "validDate": 1,
                                "id": "1",
                                "projectGroupName": "5",
                                "sysShopId": "11"
                            },
                            {
                                "createBy": 1,
                                "createDate": 1,
                                "detail": 1,
                                "discountPrice": 1,
                                "marketPrice": 1,
                                "projectGroupUrl": 1,
                                "status": 1,
                                "updateDate": 1,
                                "updateUser": 1,
                                "validDate": 1,
                                "id": "2",
                                "projectGroupName": "5555",
                                "sysShopId": "11"
                            }
                        ],
                            "result": "0x00001"
                    }
                    $scope.param.consumptionObj.collectionCardByShowId= collectionCardByShowId.responseData;
                    for(var i=0;i<$scope.param.consumptionObj.collectionCardByShowId.length;i++){
                        $scope.selectCollectionCardDataPic[i] = 'images/bt_Single%20election_nor_.png';
                        $scope.collectionCard[i] = true;
                    }

                    collectionCardCtrl && collectionCardCtrl($scope);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }
    }
    $scope.subtractOrAdd = function(type){
        if(type == 0){
            if($scope.param.num == 1)return
            $scope.param.num--
        }else{
            $scope.param.num++
        }
    };

    $scope.candel = function(type,index){
        if(type=="singleByUserId"){
            $scope.param.consumptionObj.singleByUserId.splice(index,1)
            if($scope.param.consumptionObj.singleByUserId.length>=0){
                $scope.param.consumptionObj.singleByUserIdFlag = false;
            }
        } else if(type=="treatmentCardByUserId"){
            $scope.param.consumptionObj.treatmentCardByUserId.splice(index,1)
            if($scope.param.consumptionObj.treatmentCardByUserId.length>=0){
                $scope.param.consumptionObj.treatmentCardByUserIdFlag = false;
            }
        }else if(type=="productByUserId"){
            $scope.param.consumptionObj.productByUserId.splice(index,1)
            if($scope.param.consumptionObj.productByUserId.length>=0){
                $scope.param.consumptionObj.productByUserIdFlag = false;
            }
        }else if(type=="collectionCardByUserId"){
            $scope.param.consumptionObj.collectionCardByUserId.splice(index,1);
            if($scope.param.consumptionObj.collectionCardByUserId.length>=0){
                $scope.param.consumptionObj.collectionCardByUserIdFlag = false;
            }
        }

    };
    balancePrepaid && balancePrepaid ($scope,ngDialog)/*余额充值*/
    givingChange && givingChange($scope,ngDialog);/*赠送*/
    relatedStaffCtrl && relatedStaffCtrl($scope,ngDialog);/*关联员工*/

    payTypeCtrl && payTypeCtrl($scope,ngDialog);/*消费-消费-下一步*/
    scratchCard && scratchCard($scope);
    selectSingleCtrl && selectSingleCtrl($scope);/*单次*/
    searchConsumption && searchConsumption($scope);
    selectTreatmentCardCtrl && selectTreatmentCardCtrl($scope);/*套卡*/

}
