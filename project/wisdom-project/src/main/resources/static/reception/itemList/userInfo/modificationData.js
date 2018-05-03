PADWeb.controller('modificationDataCtrl', function($scope, $stateParams,ClerkInfo,UpateClerkInfo) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="修改资料"
    $scope.$parent.$parent.param.headerCash.backContent="取消"
    $scope.$parent.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightBackFlag = true
    console.log("修改资料");

    $scope.param = {
        openSexFlag:false
    };

    /*个人信息*/
    $scope.getUserInfo = function () {
        ClerkInfo.query({
            clerkId: "2"
        }, function(data) {
            $scope.userInfoDataMod = data[0]
        });
    }
    $scope.getUserInfo()
    /*---------------------------------------------方法--------------------------------------------------*/
    $scope.selectSex = function (sexType) {
        $scope.param.openSexFlag = true
    };
    
    $scope.selectFn = function (sexType) {
        $scope.userInfoDataMod.sex = sexType
        $scope.param.openSexFlag = false
    }
    $scope.$parent.$parent.leftTipFn = function () {
        UpateClerkInfo.save({
            id:"2",
            sysUserId:"2",
            sex:$scope.userInfoDataMod.sex,
            photo:$scope.userInfoDataMod.photo,
        },function (data) {
            if(data.result == "0x00001"){
                $scope.getUserInfo()
            }
        })
    }
});