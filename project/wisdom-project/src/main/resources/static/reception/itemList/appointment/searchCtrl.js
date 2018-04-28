function searchConsumption($scope){
    var index = [];
    $scope.selectSingleDataPic =[];
    $scope.single =[];

    $scope.searchConsumption = function(){/*搜索三级*/
      if( $scope.param.consumptionObj.consumptionType == "selectSingle"){
          /* {
           filterStr:$scope.param.consumptionObj.singleFilterStr,
           useStyle:"1"
           }*//*参数*/
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
          };
          $scope.param.consumptionObj.singleByshopId.detailProject = singleByshopId.responseData.detailProject;/*三级数据*/
          for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
              $scope.selectSingleDataPic[i] = 'images/bt_Single%20election_nor_.png';
              $scope.single[i] = true;
          }
      }else if($scope.param.consumptionObj.consumptionType == "selectTreatmentCard"){
          $scope.selectTreatmentDataPic =[];
          $scope.treatment =[];

          /* {
           filterStr:$scope.param.consumptionObj.singleFilterStr,
           useStyle:"0"
           }*//*参数*/
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
          $scope.param.consumptionObj.singleByshopId.detailProject = singleByshopId.responseData.detailProject;/*三级数据*/
          for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
              $scope.selectTreatmentDataPic[i] = 'images/bt_Single%20election_nor_.png';
              $scope.treatment[i] = true;
          }
      }else if($scope.param.consumptionObj.consumptionType=="selectProduct"){
          console.log(1)
          $scope.selectProductDataPic = []
          $scope.product = [];
          /*{filterStr:$scope.param.consumptionObj.singleFilterStr}*/
          var productByshopId = {
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
          $scope.param.consumptionObj.singleByshopId.detailProject = productByshopId.responseData.detailProduct;/*三级数据*/
          for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
              $scope.selectProductDataPic[i] = 'images/bt_Single%20election_nor_.png';
              $scope.product[i] = true;
          }
          }



        /*for(var i=0;i<$scope.param.data.length;i++){
            for(var j=0;j<$scope.param.data[i].content.length;j++){
                if($scope.param.data[i].content[j].indexOf($scope.param.serachContent) !=-1){
                    index.push(i)
                }
            }
        }
        if(index.length>0){
            var arr =[]
            for(var i=0;i<index.length;i++){
                arr.push($scope.param.data[index[i]])
            }
            $scope.param.data = arr;
            console.log($scope.param.data);
            index= [];
        }*/
    }


    $scope.searchActive = function(projectTypeOneId,projectTypeTwoId){  /*点击查询三级*/
        /*$scope.param.index = index;
        $scope.param.index2 = index2;
        $scope.active = "bgff6666";*/

        if($scope.param.consumptionObj.consumptionType == "selectTreatmentCard"||$scope.param.consumptionObj.consumptionType == "selectSingle"){
            var singleMessByshopId={
                "errorInfo": 1,
                "responseData": [
                    {
                        "cardType": "测试内容714j",
                        "createBy": 1,
                        "createDate": 1,
                        "discountPrice": 1,
                        "functionIntr": 1,
                        "isDisplay": 1,
                        "marketPrice": 1,
                        "maxContainTimes": 1,
                        "oncePrice": 1,
                        "projectDuration": 1,
                        "projectUrl": 1,
                        "sysBossId": 1,
                        "updateDate": 1,
                        "updateUser": 1,
                        "useStyle": "测试内容443u",
                        "visitDateTime": 1,
                        "id": "1",
                        "productType": "1",
                        "projectName": "足疗疗程卡",
                        "projectTypeOneId": "1",
                        "projectTypeOneName": "足疗",
                        "projectTypeTwoId": "4",
                        "projectTypeTwoName": "足疗",
                        "status": "0",
                        "sysShopId": "11"
                    },
                    {
                        "cardType": "测试内容714j",
                        "createBy": 1,
                        "createDate": 1,
                        "discountPrice": 1,
                        "functionIntr": 1,
                        "isDisplay": 1,
                        "marketPrice": 1,
                        "maxContainTimes": 1,
                        "oncePrice": 1,
                        "projectDuration": 1,
                        "projectUrl": 1,
                        "sysBossId": 1,
                        "updateDate": 1,
                        "updateUser": 1,
                        "useStyle": "测试内容443u",
                        "visitDateTime": 1,
                        "id": "2",
                        "productType": "null",
                        "projectName": "足疗单次",
                        "projectTypeOneId": "1",
                        "projectTypeOneName": "足疗",
                        "projectTypeTwoId": "4",
                        "projectTypeTwoName": "null",
                        "status": "0",
                        "sysShopId": "11"
                    }
                ],
                "result": "0x00001"
            };
            $scope.param.consumptionObj.singleByshopId.detailProject =  singleMessByshopId.responseData;
        }else if($scope.param.consumptionObj.consumptionType == "selectProduct"){
            var productMess = {
                "errorInfo": 1,
                "responseData": [
                    {
                        "createBy": 1,
                        "createDate": 1,
                        "discountPrice": 300,
                        "id": "1",
                        "introduce": 1,
                        "isDisplay": 1,
                        "marketPrice": 300,
                        "productCode": "2222",
                        "productFunction": "功效",
                        "productName": "产品名称",
                        "productPosition": "产品适用部位",
                        "productSpec": "规格",
                        "productType": "3",
                        "productTypeOneId": "2",
                        "productTypeOneName": "产品一级名字",
                        "productTypeTwoId": "3",
                        "productTypeTwoName": "产品二级名字",
                        "productUnit": 111,
                        "productUrl": "www.baidu.com",
                        "productWarningDay": 30,
                        "productWarningNum": 30,
                        "qrCodeUrl": "二维产品地址",
                        "qualityPeriod": 30,
                        "status": 1,
                        "sysShopId": "11",
                        "updateDate": 1,
                        "updateUser": 1
                    }
                ],
                "result": "0x00001"
            }
            $scope.param.consumptionObj.singleByshopId.detailProject =  productMess.responseData;
        }

    }
    selectSingleCtrl && selectSingleCtrl($scope);
    selectTreatmentCardCtrl && selectTreatmentCardCtrl($scope)
}