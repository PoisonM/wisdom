PADWeb.controller('modificationDataCtrl', function($scope, $stateParams,ClerkInfo,UpateClerkInfo,ImageBase64UploadToOSS) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="修改资料"
    $scope.$parent.$parent.param.headerCash.backContent="取消"
    $scope.$parent.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightBackFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
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

    /*上传图片*/
    /*上传图片*/
    $scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.thumb = "";      //用于存放图片的base64
    $scope.img_upload = function(files) {
        var file = files[0];
        if (window.FileReader) {
            var fr = new FileReader();
            fr.onloadend = function (e) {
                console.log(e)
                $scope.thumb = e.target.result
            };
            fr.readAsDataURL(file);
        } else {
            alert("浏览器不支持")
        }
        debugger
        console.log($scope.thumb)
        ImageBase64UploadToOSS.save($scope.thumb, function (data) {
            /*if(data.result == "0x00001"){

             }*/
            $scope.param.imgSrc = data.responseData//图片地址
        })
    }

    $scope.$parent.$parent.leftTipFn = function () {
        UpateClerkInfo.save({
            id:$scope.userInfoDataMod.id,
            sysUserId:$scope.userInfoDataMod.sysUserId,
            sex:$scope.userInfoDataMod.sex,
            photo:$scope.param.imgSrc,
        },function (data) {
            if(data.result == "0x00001"){
                alert("保存成功")
                $scope.getUserInfo()
            }
        })
    }
});