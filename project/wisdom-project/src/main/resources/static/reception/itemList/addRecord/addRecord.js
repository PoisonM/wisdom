PADWeb.controller('addRecordCtrl', function($scope,$rootScope,$state,SaveArchiveInfo
    ,DeleteArchiveInfo,ImageBase64UploadToOSS,ImageBase64UploadToOSS) {
    console.log($scope);
/*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftContent="档案("+$rootScope.recordNum+")"
    $scope.$parent.$parent.param.headerCash.leftAddContent="添加档案"
    $scope.$parent.$parent.param.headerCash.backContent="今日收银记录"
    $scope.$parent.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool,
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)



/*----------------------------------初始化参数------------------------------------------*/
    $scope.param = {
        imgSrc:"",
        select_type:"",
        openSelectFlag:false,//选择页面开关
        selectContentName:"",
        selectContentPhone:"",
        selectContentSex:"",
        selectContentBirthday:"",
        selectContentAge:"",
        selectContentConstellation:"",
        selectContentBlood:"",
        selectContentHeight:"",
        selectContentSource:"",
    }

    /*---------------------------------方法-----------------------------------*/
    $scope.flagFn = function (backContent,title,bool) {
        $scope.$parent.$parent.param.headerCash.backContent = backContent
        $scope.$parent.$parent.param.headerCash.title = title
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = bool//关闭返回
    }

    $scope.flagFn("","添加档案",false)

   

    $scope.$parent.$parent.leftTipFn = function () {
        $scope.ShopUserArchivesDTO = {
            age	:$scope.param.selectContentAge,//年龄
            birthday:$scope.param.selectContentBirthday,//生日
            bloodType:$scope.param.selectContentBlood,//血型
            channel:'',//渠道
            constellation:$scope.param.selectContentConstellation,//星座
            detail:'',//备注
            height:$scope.param.selectContentHeight,//身高
            imageUrl:$scope.param.imgSrc,//头像地址
            phone:$scope.param.selectContentPhone,//手机号
            sex:$scope.param.selectContentSex,//性别
            sysClerkId:'',
            sysClerkName:"",
            sysShopId:'11',
            sysShopName:'汉方美容院',
            sysUserName:$scope.param.selectContentName,
        }
        if($scope.param.selectContentName == ""){
            alert("请输入姓名")
            return
        }
        if($scope.param.selectContentPhone == ""){
            alert("请输入手机号")
            return
        }

        var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
        if(!reg.test($scope.param.selectContentPhone)){
            alert("请输入正确手机号")
            return
        }
        SaveArchiveInfo.save($scope.ShopUserArchivesDTO,function (data) {
            if(data.result == "0x00001"){
                alert("保存成功")
                $state.go("pad-web.left_nav.blankPage")
            }else if(data.result == "0x00002"){
                alert(data.responseData)
            }
        })
    }


    //打开选择页面
    $scope.openSelect = function (type,content) {
        $scope.flagFn("添加档案",content,false)
        $scope.param.openSelectFlag = true
        $scope.param.select_type = type
    }
    
    //选择完成
    $scope.selectFn = function (type,content) {
        $scope.flagFn("","添加档案",false)
        $scope.param.openSelectFlag = false
        if(type == "sex"){
            $scope.param.selectContentSex = content
        }else if(type == "birthday"){
            $scope.param.selectContentBirthday = content
        }else if(type == "age"){
            $scope.param.selectContentAge = content
        }else if(type == "constellation"){
            $scope.param.selectContentConstellation = content
        }else if(type == "bloodType"){
            $scope.param.selectContentBlood = content
        }else if(type == "height"){
            $scope.param.selectContentHeight = content
        }else if(type == "source"){
            $scope.param.selectContentSource = content
        }
    }


    /*上传图片*/
    $scope.reader = new FileReader();   //创建一个FileReader接口
    $scope.thumb = "";      //用于存放图片的base64
    $scope.img_upload = function(files) {
        var file = files[0];
        if(window.FileReader) {
            var fr = new FileReader();
            fr.onloadend = function(e) {
                console.log(e)
                $scope.thumb = e.target.result
            };
            fr.readAsDataURL(file);
        }else {
            alert("浏览器不支持")
        }
        debugger
        console.log($scope.thumb)
        ImageBase64UploadToOSS.save($scope.thumb,function (data) {
            /*if(data.result == "0x00001"){

            }*/
            $scope.param.imgSrc = data.responseData//图片地址
        })


    };

   /* $scope.img_del = function(key) {    //删除，删除的时候thumb和form里面的图片数据都要删除，避免提交不必要的
        var guidArr = [];
        for(var p in $scope.thumb){
            guidArr.push(p);
        }
        delete $scope.thumb[guidArr[key]];
        delete $scope.form.image[guidArr[key]];
    };
    $scope.submit_form = function(){    //图片选择完毕后的提交，这个提交并没有提交前面的图片数据，只是提交用户操作完毕后，
        // 到底要上传哪些，通过提交键名或者链接，后台来判断最终用户的选择,整个思路也是如此
        $http({
            method: 'post',
            url: '/comm/test.php',
            data:$scope.form,
        }).success(function(data) {
            console.log(data);
        })
    };*/


});