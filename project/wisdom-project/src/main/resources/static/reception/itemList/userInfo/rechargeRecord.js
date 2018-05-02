PADWeb.controller('rechargeRecordCtrl', function($scope, $stateParams, ngDialog,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="充值记录"
    //consumeType 0：充值 1：消费 2、还欠款 3、退款
    Consumes.save({
        sysClerkId:112,
        goodsType:0,
        consumeType:0,
        pageSize:12
    },function (data) {
        $scope.dataList = data.responseData
    })
});