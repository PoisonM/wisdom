PADWeb.controller('unclaimedAllCtrl', function($scope, $stateParams, ngDialog,GetProductRecord) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="待领取汇总"
    console.log('待领取汇总');
    /*-----------------------------------------------------------------------------------------------------*/
    GetProductRecord.get({
        searchFile:"186",
        sysClerkId:"22",
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData.data
            $scope.totalWaitReceiveNumber = data.responseData.totalWaitReceiveNumber
            $scope.totalWaitReceivePeople = data.responseData.totalWaitReceivePeople
        }
    })
});