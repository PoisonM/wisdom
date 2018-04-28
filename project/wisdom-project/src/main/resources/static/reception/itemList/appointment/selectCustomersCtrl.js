function selectCustomersCtrl($scope,ngDialog){

    /*选择顾客*/
var data = {
    "result": "0x00001",
    "errorInfo": null,
    "responseData": {
        "data": 2,
        "info": [{
            "M": [{
                "id": "44",
                "sysShopId": "11",
                "sysShopName": "毛欢",
                "sysClerkId": "毛欢",
                "sysClerkName": "毛欢",
                "imageRul": "毛欢",
                "sysUserName": "毛欢",
                "phone": '123',
                "sysUserId": "12",
                "sex": null,
                "birthday": null,
                "age": null,
                "constellation": null,
                "bloodType": null,
                "height": null,
                "weight": null,
                "sysUserType": null,
                "channel": null,
                "detail": null,
                "createBy": null,
                "createDate": null,
                "updateUser": null,
                "updateDate": null
            }]
        }, {
            "Z": [{
                "id": "66",
                "sysShopId": "11",
                "sysShopName": "店名字2",
                "sysClerkId": "11",
                "sysClerkName": "张欢",
                "imageRul": null,
                "sysUserName": "张欢2",
                "phone": '123',
                "sysUserId": 12,
                "sex": null,
                "birthday": null,
                "age": null,
                "constellation": null,
                "bloodType": null,
                "height": null,
                "weight": null,
                "sysUserType": null,
                "channel": null,
                "detail": null,
                "createBy": null,
                "createDate": null,
                "updateUser": null,
                "updateDate": null
            }]
        }]
    }
};
$scope.selectCustomersFun = function(){
        /* {
            queryField:$scope.param.selectCustomersObject.queryField,/!*顾客查询条件，可为空*!/
            pageNo:1,
            pageSize:100
        }*//*选择顾客参数  get*/
    }
    $scope.selectCustomersCtrl = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.selectCustomersObject.data=data.responseData.info;
                $scope.close = function() {
                     if(status == 0){
                     $scope.param.selectCustomersObject.sysUserName = "";
                     $scope.param.selectCustomersObject.sysUserId = "";
                     $scope.param.selectCustomersObject.sysUserPhone = "";
                     }
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });
    };
    $scope.searchCustomer = function(){
        console.log($scope.param.selectCustomersObject.queryField)
        $scope.selectCustomersFun()

    }
    $scope.selectTheCustomer = function(index,sysUserName,sysUserId,sysUserPhone){
        $scope.param.ModifyAppointmentObject.customerIndex = index;
        $scope.param.selectCustomersObject.sysUserName = sysUserName;
        $scope.param.selectCustomersObject.sysUserId = sysUserId;
        $scope.param.selectCustomersObject.sysUserPhone = sysUserPhone;
        if($scope.param.ModifyAppointmentObject.productNum == "0"){
            setTimeout(function(){
                $scope.selectNewProduct();
                ngDialog.close("selectCustomersWrap")
            },800)
        }




    }
    addCustomersCtrl && addCustomersCtrl($scope,ngDialog)
}

