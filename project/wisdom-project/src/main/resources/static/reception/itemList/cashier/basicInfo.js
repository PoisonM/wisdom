function basicInfo($scope, $state,$stateParams,Archives, ArchivesDetail,$rootScope) {
    //初始化参数
    $scope.param = {
        userId: "", //用户id
        id: "", //档案id
        shopId: ""
    }

    //确定用户选中样式
    $scope.$parent.param.selectSty = $state.params.sysUserId
    clearInterval($rootScope.timeInfo)


    $scope.queryUserInfo = function(sysUserId, id) {
        //根据id查用户信息
        //member    会员状态    string  0:绑定 1：未绑定
        ArchivesDetail.get({ id: id }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData = data.responseData
            }
        })

    }
    $scope.queryUserInfo($state.params.sysUserId, $state.params.id)


    $scope.goAccountRecords = function() {
        $state.go('pad-web.left_nav.accountRecords',{userId:$state.params.sysUserId});
    };
    $scope.goStillOwed = function() {
        $state.go('pad-web.left_nav.stillOwed');
    };
    $scope.goSelectRechargeType = function() {
        $state.go('pad-web.left_nav.selectRechargeType',{userId:$state.params.sysUserId});
    };
    $scope.goBindMember = function(shopId,userId) {
        $state.go('pad-web.bindMember', {
            shopId: shopId,
            userId: userId
        });
    };
    $scope.goAddRecordDetail = function() {
        $state.go("pad-web.left_nav.addRecordDetail", {
            id: $state.params.id
        })
    }
    $scope.toConsumptionList = function() {
        $state.go('pad-web.consumptionList',{userId:$state.params.sysUserId});
    }
}