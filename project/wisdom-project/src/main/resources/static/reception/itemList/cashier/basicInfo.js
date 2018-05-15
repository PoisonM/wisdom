function basicInfo($scope, $state, Archives, GetShopUserArchivesInfoByUserId) {
    //初始化参数
    $scope.param = {
        userId:"",//用户id
        id:"",//档案id
    }

    $scope.queryUserInfo = function(id,sysUserId) {
        //根据id查用户信息
        GetShopUserArchivesInfoByUserId.get({ sysUserId: "088e3dcc-0d08-40b3-ad29-13e7c5f5f017" }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData = data.responseData
            }
        })

        Archives.get({ id: '66' }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData1 = data.responseData
            }
        })
    }



    console.log(Archives)
    //因接口需要写成死数据
    $scope.queryUserInfo($scope.param.id,$scope.param.userId)

    $scope.$parent.selectSty = function(id,sysUserId) {
        $scope.$parent.param.selectSty = id
        $scope.param.userId = sysUserId
        $scope.param.id = id
        $scope.queryUserInfo(id,sysUserId)
    };
    $scope.goAccountRecords = function() {
        $state.go('pad-web.left_nav.accountRecords');
    };
    $scope.goStillOwed = function() {
        $state.go('pad-web.left_nav.stillOwed');
    };
    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType');
    };
    $scope.goConsumptionList = function() {
        $state.go('pad-web.consumptionList');
    };
    $scope.goBindMember = function() {
        $state.go('pad-web.bindMember');
    };
}