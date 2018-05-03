function basicInfo($scope, $state, Archives, GetShopUserArchivesInfoByUserId) {
    $scope.queryUserInfo = function(id) {
        //根据id查用户信息
        GetShopUserArchivesInfoByUserId.get({ sysUserId: id }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData = data.responseData
            }
        })

        Archives.get({ userId: '66' }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData1 = data.responseData
            }
        })
    }



    console.log(Archives)
    //因接口需要写成死数据
    $scope.queryUserInfo("088e3dcc-0d08-40b3-ad29-13e7c5f5f017")

    $scope.$parent.selectSty = function(id) {
        $scope.$parent.param.selectSty = id
        $scope.queryUserInfo(id)
    }
    $scope.goAccountRecords = function() {
        $state.go('pad-web.left_nav.accountRecords');
    }
    $scope.goStillOwed = function() {
        $state.go('pad-web.left_nav.stillOwed');
    }
    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType');
    }
    $scope.goConsumptionList = function() {
        $state.go('pad-web.consumptionList');
    }
}