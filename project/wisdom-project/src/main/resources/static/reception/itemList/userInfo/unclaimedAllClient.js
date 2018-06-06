PADWeb.controller('unclaimedAllClientCtrl', function($scope, $stateParams
    , ngDialog,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="领取记录"
    /*---------------------------------------------------------------------------------------------*/
    /*GetWaitReceiveDetail.get({
        "sysUserId":"088e3dcc-0d08-40b3-ad29-13e7c5f5f017"
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData
        }
    })*/

    Consumes.save({
        goodsType:4,
        consumeType:1,
        pageSize:12
    },function (data) {
        $scope.dataList = data.responseData
    })
});