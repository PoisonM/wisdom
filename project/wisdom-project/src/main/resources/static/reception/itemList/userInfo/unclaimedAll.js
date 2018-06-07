PADWeb.controller('unclaimedAllCtrl', function($scope, $stateParams, ngDialog,GetProductRecord,GetWaitReceivePeopleAndNumber) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="待领取汇总"
    console.log('待领取汇总');
    /*-----------------------------------------------------------------------------------------------------*/
    GetProductRecord.get({
        searchFile:"",
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData.data
        }
    })
    GetWaitReceivePeopleAndNumber.get({},function (data) {
        if(data.result == "0x00001"){
            $scope.numbers = data.responseData
        }
    })
});