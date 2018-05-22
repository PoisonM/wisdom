function basicInfo($scope, $state, Archives, GetShopUserArchivesInfoByUserId) {
    //初始化参数
    $scope.param = {
        userId:"",//用户id
        id:"",//档案id
        shopId:""
    }
    var timeIn = setInterval(function () {
        if($(".user_info").length!=0){
            $(".user_info").eq(0).trigger("click");
            clearInterval(timeIn)
            //因接口需要写成死数据
            $scope.queryUserInfo($scope.param.userId)
        }
    },100)


    $scope.queryUserInfo = function(sysUserId) {
        //根据id查用户信息
        GetShopUserArchivesInfoByUserId.get({ sysUserId:sysUserId}, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData = data.responseData
            }
        })

        Archives.get({ userId:sysUserId }, function(data) {
            if (data.result == "0x00001") {
                $scope.responseData1 = data.responseData
            }
        })
    }



    console.log(Archives)


    $scope.$parent.selectSty = function(id,sysUserId,shopId) {
        $scope.$parent.param.selectSty = id
        $scope.param.userId = sysUserId
        $scope.param.shopId = shopId
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
        $state.go('pad-web.bindMember',{
            shopId:$scope.param.shopId,
            userId:$scope.param.userId
        });
    };
}