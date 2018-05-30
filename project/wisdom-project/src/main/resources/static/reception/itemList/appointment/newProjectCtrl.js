function selectProductCtrl($scope,ngDialog,GetShopProjectList){
   /* var  data={
        "errorInfo": 1,
        "responseData": [
            {
                "cardType": "1",
                "createBy": 1,
                "createDate": 1,
                "discountPrice": 1,
                "functionIntr": 1,
                "id": "1",
                "isDisplay": 1,
                "marketPrice": 1,
                "maxContainTimes": 1,
                "oncePrice": 1,
                "productType": "1",
                "projectDuration": 1,
                "projectName": "足疗疗程卡",
                "projectTypeOneId": "1",
                "projectTypeOneName": "足疗",
                "projectTypeTwoId": "1",
                "projectTypeTwoName": "足疗",
                "projectUrl": 1,
                "status": "0",
                "sysShopId": "1",
                "shopProjectId":"12",
                "updateDate": 1,
                "updateUser": 1,
                "useStyle": "0",
                "visitDateTime": 1
            },
            {
                "cardType": "1",
                "createBy":1,
                "createDate": 1,
                "discountPrice": 1,
                "functionIntr": 1,
                "id": "1",
                "isDisplay": 1,
                "marketPrice": 1,
                "maxContainTimes": 1,
                "oncePrice": 1,
                "productType": "1",
                "projectDuration": 1,
                "projectName": "足疗疗程卡",
                "projectTypeOneId": "1",
                "projectTypeOneName": "足疗",
                "projectTypeTwoId": "1",
                "projectTypeTwoName": "足疗",
                "projectUrl": 1,
                "status": "0",
                "sysShopId": "1",
                "shopProjectId":"12",
                "updateDate": 1,
                "updateUser": 1,
                "useStyle": "0",
                "visitDateTime": 1
            }
        ],
        "result": "0x00001"
    };
    /!*疗程卡*!/
    $scope.newProjectFun = function(){
        /!*{useStyle:"0",
            filterStr:$scope.param.newProductObject.filterStr  //参数
        }*!/
        /!*请求数据的位置*!/
        $scope.param.newProductObject.newProjectData=data.responseData;

    };
    $scope.newProjectFun()
    var selfData={
        "result":"0x00001",
        "errorInfo":null,
        "responseData":[
            {
                "面部":[
                    {
                        "id":"3",
                        "sysShopId":"11",
                        "sysBossId":null,
                        "projectName":"足疗",
                        "projectTypeOneName":"足疗",
                        "projectTypeTwoName":"足疗",
                        "projectTypeOneId":"1",
                        "projectTypeTwoId":"4",
                        "productType":"1",
                        "useStyle":"1",
                        "cardType":"1",
                        "projectUrl":null,
                        "projectDuration":12,
                        "marketPrice":null,
                        "discountPrice":null,
                        "maxContainTimes":"12",
                        "visitDateTime":null,
                        "oncePrice":null,
                        "functionIntr":null,
                        "isDisplay":null,
                        "status":"0",
                        "shopProjectId":"123",
                        "createBy":null,
                        "createDate":null,
                        "updateUser":null,
                        "updateDate":null
                    },
                    {
                        "id":"4",
                        "sysShopId":"11",
                        "sysBossId":null,
                        "projectName":"面部补水",
                        "projectTypeOneName":"面部",
                        "projectTypeTwoName":"面部",
                        "projectTypeOneId":"1",
                        "projectTypeTwoId":"2",
                        "productType":"2",
                        "useStyle":"1",
                        "cardType":"1",
                        "projectUrl":null,
                        "projectDuration":"12",
                        "marketPrice":null,
                        "discountPrice":null,
                        "maxContainTimes":12,
                        "visitDateTime":null,
                        "oncePrice":null,
                        "functionIntr":null,
                        "isDisplay":null,
                        "status":"0",
                        "shopProjectId":"123",
                        "createBy":null,
                        "createDate":null,
                        "updateUser":null,
                        "updateDate":null
                    }
                ]
            }
        ]
    }
    /!*本店项目*!/
    $scope.selfProduct=function(){
        /!*{pageNo:1,
         pageSize:100,
         filterStr:$scope.param.newProductObject.filterStr}
         *!/ //参数


       $scope.param.newProductObject.selfProductData = selfData.responseData;
        $scope.falseAll()
    }
    $scope.newProductSearch = function(){
        console.log($scope.param.newProductObject.filterStr)
    }
    /!*选择项目*!/
    $scope.selectNewProduct = function(){
        if($scope.param.selectCustomersObject.sysUserName == ""){
            $scope.selectCustomersCtrl()
        }else{
            ngDialog.open({
                template: 'newProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    /!*$scope.newProjectFun();*!/
                    $scope.close = function(status) {
                        if(status == 1){
                            $scope.param.newProductObject.shopProjectIdArr = [];/!*Id数组*!/
                            $scope.param.newProductObject.shopProjectNameArr = [];/!*项目名数组*!/
                            var timeLength = 0;/!*项目时长*!/
                           /!* $scope.param.ModifyAppointmentObject.productNum = 0;/!*项目个数*!/!*!/
                            for(var i=0;i<$scope.param.ModifyAppointmentObject.selfProductDataFlag.length;i++){
                                if($scope.param.ModifyAppointmentObject.selfProductDataFlag[i]==true){
                                    $scope.param.newProductObject.shopProjectIdArr.push($(".selfProductDataIndex").eq(i).attr('shopProjectId'))
                                    $scope.param.newProductObject.shopProjectNameArr.push($(".selfProductDataIndex").eq(i).attr('projectName'));
                                    timeLength += $(".selfProductDataIndex").eq(i).attr('appointPeriod')/1;
                                    $scope.param.ModifyAppointmentObject.productNum ++;
                                }
                            }
                            for(var i=0;i<$scope.param.newProductObject.newProjectData.length;i++){
                                if($scope.param.ModifyAppointmentObject.newProjectDataFlag[i]==true){
                                    $scope.param.newProductObject.shopProjectIdArr.push($(".newProjectIndex").eq(i).attr('shopProjectId'))
                                    $scope.param.newProductObject.shopProjectNameArr.push($(".newProjectIndex").eq(i).attr('projectName'));
                                    timeLength += $(".newProjectIndex").eq(i).attr('appointPeriod')/1;
                                    $scope.param.ModifyAppointmentObject.productNum ++;

                                }
                            }
          /!*项目ID*!/         $scope.param.newProductObject.shopProjectId=$scope.param.newProductObject.shopProjectIdArr.join(",");
        /!*项目名称*!/         $scope.param.newProductObject.shopProjectName=$scope.param.newProductObject.shopProjectNameArr.join(",");
        /!*项目时长*!/         $scope.param.ModifyAppointmentObject.appointPeriod = timeLength;
        console.log($scope.param.ModifyAppointmentObject.appointPeriod)
                            ngDialog.close("selectCustomersWrap")
                        }else{
                            $scope.param.ModifyAppointmentObject.productNum = "0";
                            $scope.falseAll()
                            $scope.param.newProductObject.shopProjectId='';
                            $scope.param.newProductObject.shopProjectName=""
                        }
                        $scope.closeThisDialog();

                    };
                }],
                className: 'newProject ngdialog-theme-custom'
            });
        }


    }
    $scope.param.newProductObject.content = true;
    $scope.newProductBtn = function(index){
        $scope.param.newProductObject.index =index;
        if(index == 1){
            $scope.param.newProductObject.titleFlag = true;
            $scope.param.newProductObject.content = false;
            $scope.selfProduct();
        }else{
            $scope.param.newProductObject.titleFlag = false;
            $scope.param.newProductObject.content = true;
            $scope.newProjectFun();
        }
    };
    $scope.falseAll = function(){
        for(var i=0;i<$scope.param.newProductObject.newProjectData.length;i++){
            $scope.param.ModifyAppointmentObject.newProjectDataFlag[i] = false;
        }
        for(var i=0;i<$scope.param.newProductObject.selfProductData.length;i++){
            for(var key in $scope.param.newProductObject.selfProductData[i]){
                $scope.param.ModifyAppointmentObject.selfProductDataFlag.push(false);
            }
        }
    }

    var a = -1;

    $scope.selectTheProduct = function(index,type){

        if(type == "疗程"){
            $scope.param.ModifyAppointmentObject.newProjectDataFlag[index] =!$scope.param.ModifyAppointmentObject.newProjectDataFlag[index];

        }else{

            $scope.param.ModifyAppointmentObject.selfProductDataFlag[index] = !$scope.param.ModifyAppointmentObject.selfProductDataFlag[index];



        }


    }
*/
}