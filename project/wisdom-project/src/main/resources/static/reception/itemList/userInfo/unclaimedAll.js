PADWeb.controller('unclaimedAllCtrl', function($scope, $stateParams, ngDialog,GetProductRecord,GetWaitReceivePeopleAndNumber) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="待领取汇总"
    $scope.$parent.$parent.param.headerCash.backContent = "今日收银记录"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false
    $scope.searchNameAndPhone = "";
    $scope.dataNewValueList = [];

    /*-----------------------------------------------------------------------------------------------------*/
    GetProductRecord.get({
        searchFile:"",
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = data.responseData;
            $scope.dataNewValueList = angular.copy($scope.dataList);
        }
    })

    GetWaitReceivePeopleAndNumber.get({},function (data) {
        if(data.result == "0x00001"){
            $scope.numbers = data.responseData
        }
    })

    $scope.$watch('searchNameAndPhone',  function(newValue, oldValue) {
        $scope.dataNewValueList = [];
        angular.forEach($scope.dataList,function (val,index) {
            if (val.nickname.indexOf($scope.searchNameAndPhone) != -1 ||val.mobile.indexOf($scope.searchNameAndPhone)!=-1)
            {
                $scope.dataNewValueList.push(val);
            }
        })
    });
    $scope.cancel = function () {
        $scope.searchNameAndPhone = "";
    }
});