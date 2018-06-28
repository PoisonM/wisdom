PADWeb.controller('addRecordCtrl', function($scope,$stateParams,$rootScope,$state,SaveArchiveInfo
    ,DeleteArchiveInfo,ImageBase64UploadToOSS,ImageBase64UploadToOSS,ArchivesDetail,UpdateArchiveInfo) {
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
    $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashAllFlag = true;
    $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = false;
    $scope.$parent.$parent.mainSwitch.headerLoginFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = true;
    $scope.$parent.mainLeftSwitch.peopleListFlag = true;
    $scope.$parent.mainLeftSwitch.priceListFlag = false;

/*----------------------------------初始化参数------------------------------------------*/
    $scope.param = {
        imgSrc:"",
        select_type:"",
        openSelectFlag:false,//选择页面开关
        selectContentName:"",
        selectContentPhone:"",
        selectContentSex:"女",
        selectContentBirthday:"",
        selectContentAge:"",
        selectContentConstellation:"",
        selectContentBlood:"",
        selectContentHeight:"",
        selectContentSource:"",
        selectContentChannel:"",//来源渠道
    }
    $scope.flagFn = function (backContent,title,backBool,saveBool) {
        $scope.$parent.$parent.param.headerCash.backContent = backContent
        $scope.$parent.$parent.param.headerCash.title = title
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = backBool//关闭返回
        $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = saveBool//保存
    }

    if($stateParams.id != ""){
        $scope.flagFn("","编辑档案",false,true)
        ArchivesDetail.get({id:$stateParams.id},function (data) {
            $scope.responseData = data.responseData
            $scope.param.imgSrc = data.responseData.imageUrl;
            $scope.param.selectContentName = data.responseData.sysUserName;
            $scope.param.selectContentSex = data.responseData.sex;
            $scope.param.selectContentPhone = data.responseData.phone;
            $scope.param.selectContentBirthday = data.responseData.birthday;
            $scope.param.selectContentAge = data.responseData.age;
            $scope.param.selectContentConstellation = data.responseData.constellation;
            $scope.param.selectContentBlood = data.responseData.bloodType;
            $scope.param.selectContentHeight = data.responseData.height;
            $scope.param.selectContentChannel = data.responseData.channel;
        })
    }else {
        $scope.flagFn("","添加档案",false,true)
    }


    /*---------------------------------方法-----------------------------------*/


    // $scope.flagFn("","添加档案",false,true)

   

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
            // sysClerkId:'',
            // sysClerkName:"",
            // sysShopId:'11',
            // sysShopName:'汉方美容院',
            sysUserName:$scope.param.selectContentName,
            channel:$scope.param.selectContentChannel
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
        if($stateParams.id == ""){
            SaveArchiveInfo.save($scope.ShopUserArchivesDTO,function (data) {
                if(data.result == "0x00001"){
                    alert("保存成功")
                    $state.go("pad-web.left_nav.blankPage")
                }else if(data.result == "0x00002"){
                    alert(data.responseData)
                }
            })
        }else{
            $scope.ShopUserArchivesDTO.id = $stateParams.id
            UpdateArchiveInfo.save($scope.ShopUserArchivesDTO,function (data) {
                if("0x00001"==data.result){
                    alert("更新成功");
                    // $state.go("pad-web.left_nav.blankPage")
                    window.history.go(-1)
                }
            });
        }

    }


    //打开选择页面
    $scope.openSelect = function (type,content) {
        if($stateParams.id != ""){
            $scope.flagFn("编辑档案",content,false,false)
        }else{
            $scope.flagFn("添加档案",content,false,false)
        }
        $scope.param.openSelectFlag = true
        $scope.param.select_type = type
    }
    
    //选择完成
    $scope.selectFn = function (type,content) {
        if($stateParams.id != ""){
            $scope.flagFn("","添加档案",false,true)
        }else{
            $scope.flagFn("","编辑档案",false,true)
        }


        $scope.flagFn("","添加档案",false,true)
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
        }else if(type == "channel"){
            $scope.param.selectContentChannel = content
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
});