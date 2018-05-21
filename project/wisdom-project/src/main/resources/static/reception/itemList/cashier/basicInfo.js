function basicInfo($scope, $state, Archives, GetShopUserArchivesInfoByUserId, ArchivesDetail) {
    //初始化参数
    $scope.param = {
        userId: "", //用户id
        id: "", //档案id
        shopId: ""
    }



    $scope.queryUserInfo = function(sysUserId, id) {
        //根据id查用户信息
        //member    会员状态    string  0:绑定 1：未绑定
        Archives.get({ userId: sysUserId }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData = data.responseData
            }
        })

    }
    $scope.queryUserInfo($state.params.sysUserId, $state.params.id)


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
        $state.go('pad-web.bindMember', {
            shopId: $scope.param.shopId,
            userId: $scope.param.userId
        });
    };
    $scope.goAddRecordDetail = function() {
        $state.go("pad-web.left_nav.addRecordDetail", {
            id: $state.params.id
        })
    }
}