function selectCustomersCtrl($scope,ngDialog,FindArchives){

   /* /!*选择顾客*!/
    $scope.selectCustomersFun = function(){
    FindArchives.get({
        queryField:"",
        pageNo:1,
        pageSize:100
    },function(data){
        $scope.param.selectCustomersObject.data=data.responseData.info;
    })
        /!* {
            queryField:$scope.param.selectCustomersObject.queryField,/!*顾客查询条件，可为空*!/
            pageNo:1,
            pageSize:100
        }*!//!*选择顾客参数  get*!/
    }
    $scope.selectCustomersCtrl = function(){
        ngDialog.open({
            template: 'selectCustomersWrap',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.selectCustomersFun();
                $scope.close = function() {
                     if(status == 0){
                     /!*$scope.param.selectCustomersObject.sysUserName = "";
                     $scope.param.selectCustomersObject.sysUserId = "";
                     $scope.param.selectCustomersObject.sysUserPhone = "";*!/
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
    }*/
    addCustomersCtrl && addCustomersCtrl($scope,ngDialog)
}

