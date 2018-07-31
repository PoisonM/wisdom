define(['appCrossborder','jquery'], function (app,$) {
    app
        .directive('managementMenu', ['$rootScope','$state','UserGetUser','UserLoginOut','Global','ManagementUtil',
            function ($rootScope,$state,UserGetUser,UserLoginOut,Global,ManagementUtil) {
                return {
                    restrict: 'EAC',
                    template: function(tElement,tAttrs){

                        var _html = '';
                        _html += '<nav class="navbar navbar-default navbar-cls-top "role="navigation" style="margin-bottom: 0;width:100%;"> ' +
                            '<div class="navbar-header"> 美享99微商城运营管理平台' +
                            ' </div> <div style="color: white;padding: 15px 50px 5px 50px;float: right;font-size: 16px;">' +
                            '欢迎您！{{loginUser.phone}} &nbsp;&nbsp;<a class="btn btn-danger square-btn-adjust" ng-click="loginOut()">退出</a> ' +
                            '</div> </nav> <!-- /. NAV TOP  --> <nav class="navbar-default navbar-side" role="navigation" id="navBar" style="background:#f5f5f5;overflow-y:auto;"> ' +
                            '<div class="sidebar-collapse" style="padding-bottom:100px;"> <ul class="nav" id="main-menu" style="background: #f5f5f5"> ' +
                            '<li style="display:block;" class="text-center"> <img src="images/logo.png" class="user-image img-responsive"/> </li>';

                        if(tAttrs.title == "index")
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 2"><a class="active-menu" ui-sref="index"><img src="images/index02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 2"><a ui-sref="index"><img src="images/index01.png">';
                        }
                        _html += '首页 </a> </li>';

                        if(tAttrs.title == "member")
                        {
                            _html += '<li style="display:block;"><a class="active-menu" ui-sref="member"><img src="images/member02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;"><a ui-sref="member"><img src="images/member01.png">';
                        }
                        _html += '会员管理 </a> </li>';

                        if(tAttrs.title=='practitioner')
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a class="active-menu" ui-sref="practitioner"><img src="images/practitioner02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a ui-sref="practitioner"><img src="images/practitioner01.png">';
                        }
                        _html += '医护管理 </a> </li>';

                        if(tAttrs.title=='product')
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a class="active-menu" ui-sref="product"><img src="images/product02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a ui-sref="product"><img src="images/product01.png">';
                        }
                        _html += '产品服务 </a> </li>';

                        if(tAttrs.title=='device')
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a class="active-menu" ui-sref="device"><img src="images/device02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a ui-sref="device"><img src="images/device01.png">';
                        }
                        _html += '设备管理 </a> </li>';

                        if(tAttrs.title=='setting')
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 2 || loginUser.sysManagementUserDTO.sysRoleID == 3"><a class="active-menu" ui-sref="setting"><img src="images/setting02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 2 || loginUser.sysManagementUserDTO.sysRoleID == 3"><a ui-sref="setting"><img src="images/setting01.png">';
                        }
                        _html += '机构设置 </a> </li>';

                        if(tAttrs.title=='active')
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a class="active-menu" ui-sref="active"><img src="images/device02.png">';
                        }
                        else
                        {
                            _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a ui-sref="active"><img src="images/device01.png">';
                        }
                        _html += '活动管理 </a> </li>';

                        _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a href="javascript:;"><img src="images/device01.png">讲堂管理<span class="fa arrow"></span></a><ul id="videoMenu" class="nav nav-second-level" style="padding-left:22px;">';

                        if(tAttrs.title=='liveVideo')
                        {
                            _html += '<li><a class="active-menu" ui-sref="video({type:\'liveVideo\'})">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="video({type:\'liveVideo\'})">';
                        }
                        _html += '直播管理 </a> </li>';

                        if(tAttrs.title=='video')
                        {
                            _html += '<li><a class="active-menu" ui-sref="video({type:\'video\'})">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="video({type:\'video\'})">';
                        }
                        _html += '视频管理 </a></ul></li> </li>';

                        _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a href="javascript:;"><img src="images/device01.png">用户与权限<span class="fa arrow"></span></a><ul id="limitsMenu" class="nav nav-second-level" style="padding-left:22px;">';

                        if(tAttrs.title=='limitsSetting')
                        {
                            _html += '<li><a class="active-menu" ui-sref="limitsSetting">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="limitsSetting">';
                        }
                        _html += '机构管理 </a> </li>';

                        if(tAttrs.title=='limitsUsers')
                        {
                            _html += '<li><a class="active-menu" ui-sref="limitsUsers">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="limitsUsers">';
                        }
                        _html += '用户管理 </a></ul></li> </li>';

                        _html += '<li style="display:block;" ng-show="loginUser.sysManagementUserDTO.sysRoleID == 1 || loginUser.sysManagementUserDTO.sysRoleID == 2"><a href="javascript:;"><img src="images/device01.png">CMS<span class="fa arrow"></span></a><ul id="cmsMenu" class="nav nav-second-level" style="padding-left:22px;">';

                        if(tAttrs.title=='cmsPicture')
                        {
                            _html += '<li><a class="active-menu"  ui-sref="cmsPicture">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="cmsPicture">';
                        }
                        _html += '图片资源位管理 </a> </li>';

                        if(tAttrs.title=='cmsTemplate')
                        {
                            _html += '<li><a class="active-menu" ui-sref="cmsTemplate">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="cmsTemplate">';
                        }
                        _html += '消息与提醒模板 </a> </li>';

                        if(tAttrs.title=='cmsSend')
                        {
                            _html += '<li><a class="active-menu" ui-sref="cmsSend">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="cmsSend">';
                        }
                        _html += '发送消息与提醒 </a> </li>';

                        if(tAttrs.title=='cmsHistory')
                        {
                            _html += '<li><a class="active-menu" ui-sref="cmsHistory">';
                        }
                        else
                        {
                            _html += '<li><a ui-sref="cmsHistory">';
                        }
                        _html += '发送历史 </a></ul></li> </li>';

                        _html += '</ul><div style="color:#9b9b9b;font-size:12px;position:fixed;left:10px;bottom:15px;">版权所有 © 华录健康养老发展有限公司</div></div></nav>';
                        return _html;
                    },
                    link:function(scope,ele,attrs){
                        UserGetUser.get({},function(data){
                            ManagementUtil.checkResponseData(data);
                            if(data.result == Global.SUCCESS){
                                scope.loginUser = data.responseData;
                            }
                        });
                        scope.loginOut = function(){
                            UserLoginOut.get({},function(data){
                                if(data.result == Global.LOGIN_OUT){
                                    $state.go('login')
                                }
                            })
                        };
                        $(function(){
                            //左侧二级菜单
                            $('#navBar').css('height',$(window).height() + 'px');
                            $('#main-menu li').click(function(){
                                $(this).find('ul li').toggle();
                            })
                            if(attrs.title == 'liveVideo' || attrs.title == 'video'){
                                $('#videoMenu li').show();
                            }
                            if(attrs.title.substr(0,6) == 'limits'){
                                $('#limitsMenu li').show();
                            }
                            if(attrs.title.substr(0,3) == 'cms'){
                                $('#cmsMenu li').show();
                            }
                        })
                    }
                }
        }])
        .directive('pageTool', ['$rootScope','$state','$timeout',
            function ($rootScope,$state,$timeout) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    template: '<div class="count"><div class="col-sm-6"> ' +
                    '<div class="dataTables_info" role="alert" aria-live="polite" aria-relevant="all">' +
                    '显示第{{param.pageFrom}}到{{param.pageTo}}条记录 </div> ' +
                    '</div> <div class="col-sm-6"> <div class="dataTables_paginate paging_simple_numbers"> ' +
                    '<ul class="pagination"> <li class="paginate_button previous">'+'<a href="javascript:;" ng-click="startPage()" >' +
                    '首 页</a><a href="javascript:;" ng-click="prevPage()" style="border-radius: 0">&lt;&lt;</a></li>'+
                    ' <li ng-class="{\'active\': item == pageNo}" ng-repeat="item in param.pageNos"><a href="javascript:;" ' +
                    'ng-click="choosePage(item)">{{item}}</a></li> <li class="paginate_button next">' +
                    '<a href="javascript:;" ng-click="nextPage()" style="border-radius: 0">&gt;&gt;</a>'+'<a href="javascript:;" ng-click="endPage()" >尾 页</a>'+'</li> </ul> </div> </div> </div>',

                    link: function(scope,ele,attrs) {
                        scope.pageNo = 1;
                        scope.pageSize = 5;
                        scope.pageSizes = [10,25,50,100];
                        scope.param = {
                            searchValue : "",
                            pageNos: [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4,scope.pageNo+5],
                            pageFrom : (scope.pageNo-1)*scope.pageSize+1,
                            pageTo : (scope.pageNo-1)*scope.pageSize+scope.pageSize
                        };

                        scope.prevPage = function()
                        {
                            scope.judgeDivisor(scope.response.count,scope.pageSize)?scope.thisPageNo = Math.ceil(scope.response.count/scope.pageSize):scope.thisPageNo = scope.thisPageNo;
                            if(scope.pageNo > 1){
                                scope.pageNo--;
                                scope.loadPageList();
                            }
                            if(scope.pageNo+1>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo];

                            }
                            else if(scope.pageNo+2>scope.thisPageNo)
                            {

                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1];
                            }
                            else if(scope.pageNo+3>scope.thisPageNo)
                            {

                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2];
                            }
                            else if(scope.pageNo+4>scope.thisPageNo)
                            {

                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3];
                            }
                            else if(scope.pageNo+5>scope.thisPageNo)
                            {

                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4];
                            }
                            else
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4,scope.pageNo+5];

                            }
                        };

                        scope.nextPage = function()
                        {

                            scope.judgeDivisor(scope.response.count,scope.pageSize)?scope.thisPageNo = Math.ceil(scope.response.count/scope.pageSize):scope.thisPageNo = scope.thisPageNo;

                            if(scope.pageNo < scope.thisPageNo)
                            {
                                scope.pageNo++;
                                scope.loadPageList();

                            }
                            if(scope.pageNo+1>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo];

                            }
                            else if(scope.pageNo+2>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1];
                            }
                            else if(scope.pageNo+3>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2];


                            }
                            else if(scope.pageNo+4>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3];

                            }
                            else if(scope.pageNo+5>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4];

                            }
                            else
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4,scope.pageNo+5];

                            }
                        };
                        scope.judgeDivisor = function(m, n) {
                            var num = {};
                            var i = 0;
                            var x = parseInt(m / n);
                            m = m % n;
                            var result = "";
                            while (m != 0 && !(m in num)) {
                                num[m] = i++;
                                result += parseInt(m * 10 / n);
                                m = m * 10 % n;
                            }
                            return m == 0;
                        }
                        scope.choosePage = function(val)
                        {
                            scope.judgeDivisor(scope.response.count,scope.pageSize)?scope.thisPageNo = Math.ceil(scope.response.count/scope.pageSize):scope.thisPageNo = scope.thisPageNo;
                            scope.pageNo = val;
                            scope.loadPageList();
                            if(scope.pageNo+1>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo];
                            }
                            else if(scope.pageNo+2>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1];
                            }
                            else if(scope.pageNo+3>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2];
                            }
                            else if(scope.pageNo+4>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3];
                            }
                            else if(scope.pageNo+5>scope.thisPageNo)
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4];
                            }
                            else
                            {
                                scope.param.pageNos = [scope.pageNo,scope.pageNo+1,scope.pageNo+2,scope.pageNo+3,scope.pageNo+4,scope.pageNo+5];
                            }
                        };

                        scope.searchPageList = function()
                        {
                            scope.loadPageList();
                        };
                        scope.startPage = function(){
                            scope.choosePage(1)
                        };
                        scope.endPage = function(){
                            scope.choosePage(Math.ceil(scope.response.count/scope.pageSize))
                        };
                        scope.displayPageList = function()
                        {
                            scope.pageNo = 1;
                            scope.loadPageList();
                        };

                        scope.loadPageList();
                    }
                }
            }])
        .directive('contenteditable',function(){
            return {
            restrict:'A',
            require:'?ngModel',
            link:function(scope,element,atrrs,ngModel){
                if(!ngModel)return;
                ngModel.$render=function(){
                    element.html(ngModel.$viewValue||'');
                }
                element.on('blur keyup change', function() {
                    scope.$apply(read);
                });
                function read() {
                    var html = element.html();
                    ngModel.$setViewValue(html);
                }
            }
        }})
        .directive('imgUpload',function(){
            return {
                //通过设置项来定义
                restrict: 'AE',
                scope: false,
                template: '<div class="fl"><input type="button" id="storeBtn" style="padding:0; position: absolute; top: 0; left: 0; background: none; border: none;color: #fff; width:84px; height: 30px; line-height: 30px;" value="选择文件"><input type="file" name="img" id="file" ng-disabled="imgDisabled" style="position: absolute; top: 0; left: 0; opacity: 0;height: 30px;" accept=".jpg,.png"></div>', //name:img 与接口字段相对应。
                replace: true,
                link: function(scope, ele, attrs) {
                    ele.bind('click', function() {
                        $('#file').val('');
                    });
                    ele.bind('change', function() {
                        scope.file = ele[0].children[1].files;
                        if (scope.file[0].size > 52428800) {
                            alert("图片大小不大于50M");
                            scope.file = null;
                            return false;
                        }
                        scope.fileName = scope.file[0].name;
                        var postfix = scope.fileName.substring(scope.fileName.lastIndexOf(".") + 1).toLowerCase();
                        if (postfix != "jpg" && postfix != "png") {
                            alert("图片仅支持png、jpg类型的文件");
                            scope.fileName = "";
                            scope.file = null;
                            scope.$apply();
                            return false;
                        }
                        scope.$apply();
                        scope.reader = new FileReader(); //创建一个FileReader接口
                        if (scope.file) {
                            //获取图片（预览图片）
                            scope.reader.readAsDataURL(scope.file[0]); //FileReader的方法，把图片转成base64
                            scope.reader.onload = function(ev) {
                                scope.$apply(function() {
                                    scope.thumb = {
                                        imgSrc: ev.target.result //接收base64，scope.thumb.imgSrc为图片。
                                    };
                                });
                            };

                        } else {
                            alert('上传图片不能为空!');
                        }
                    });
                }
            }})
        .directive('detailPage',function(){
            return{
                restrict:'EAC',
                replace:true,
                template:'<div class=""><div class="pageList">' +
                '<button class="btn btn-default btnPage" ng-click="detailPage.prevDatas()" style="margin-right:20px;">上一页</button>当前是第{{pageNum}}页' +
                '<button class="btn btn-default btnPage" ng-click="detailPage.nextDatas()" style="margin-left:20px;">下一页</button></div></div>',
                link: function(scope,ele,attrs) {

                    scope.pageNum = 1;
                    scope.pageSize = 5;
                    scope.detailPage = {};

                    scope.detailPage.nextDatas = function(){
                        console.log(scope.count);

                        if(scope.pageNum> Math.ceil(scope.count/scope.pageSize)){
                            return;
                        }
                        if(scope.hint=="none"){
                            return;
                        }
                        if(scope.hint != 'none'){

                        }
                        scope.detailPageList();
                    };
                    scope.detailPage.prevDatas = function(){
                        if(scope.pageNum <=1)return;
                        scope.pageNum > 1? scope.pageNum--:scope.pageNum = 1;
                        scope.detailPageList();
                    };
                    scope.detailPageList();
                }
            }
        })
        .directive('delTool',function(){
            return {
                restrict:'EAC',
                replace:true,
                template:'<div class="alertContainer" ng-if="alertInfo"><div class="alertTit">{{delInfo}}</div><div class="alertBtn">'+
                '<div ng-click="delSure()">确认</div><div class="keepLight" ng-click="delCancle()">取消</div></div></div>',
                link:function(scope,element,atrrs){
                    scope.delInfo = atrrs.info;
                    scope.delete = function(id){
                        scope.alertInfo = true;
                        scope.delSure = function(){
                            scope.delFun(id);
                        }
                        scope.delCancle = function(){
                            scope.alertInfo = false;
                        }
                    }
                }
            }})
        .directive('enjoyMenu', ['$rootScope','$state','$timeout',
            function ($rootScope,$state,$timeout) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    template:' <div class="navbar_header">'+
                   '<div class="navbar_name">'+
                    '<span class="hello">Hello&nbsp</span>'+
                    '<span>欢迎来到美享99</span>&nbsp&nbsp'+
                    '<span class="btn-danger exit_btn" ng-click="candel()" ui-sref="register">退出</span>'+
                    '</div>'+
                    '</div>',
                    link:function(scope,ele,attrs){
                        scope.candel = function(){
                            window.localStorage.removeItem("logintoken");
                        }
                    }

                }
            }])
        .directive('navBar', ['$rootScope','$state','$timeout',
            function ($rootScope,$state,$timeout) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    scope: {
                        index1: '@',
                        index2: '@'
                    },
                    template:
                    '<div class="navbar_sideBox">' +
                    '<div class="navbar_title"><img src="images/logo.png"></div>'+
                    '<div class="hiddenX">'+
                    '<div class="navbar_side">' +

                    '<ul>' +
                    /* '<li>' +
                     '<p><a ui-sref="register"  ng-class="{\'active\':index==1}"><img ng-src="{{index==1?\'images/device02.png\':\'images/device01.png\'}}">个人中心</a></p>' +
                     '<div class="administration_content" >' +
                     '<p><a ui-sref="register" ng-class="{\'active\':index==51}">登录</a></p>' +
                     '<p><a ui-sref="peopleCenter" ng-click="bgColor(51)" ng-class="{\'active\':index==51}">修改密码</a></p>' +
                     '</div>' +
                     '</li>' +*/

                    '<li >' +
                    '<p class="stairMenu"><a  ng-class="{\'activeNavbarSide\':index1==2}"><img ng-src="{{index1==2?\'images/bt-product-pre.png\':\'images/bt-product-nor.png\'}}">商品管理</a></p>' +
                    '<div class="administration_content" >' +
                    '<p><a ui-sref="home" ng-class="{\'activeNavbarSide\':index2==21}">查询</a></p>' +
                    '<p><a ui-sref="uploading" ng-class="{\'activeNavbarSide\':index2==22}">商品管理-上传</a></p>' +
                    '<p><a ui-sref="homePageEditor" ng-class="{\'activeNavbarSide\':index2==23}">商城首页编辑</a></p>' +
                    '</div>' +
                    '</li>' +

                    '<li  >' +
                    '<p class="stairMenu"><a ng-class="{\'activeNavbarSide\':index1==3}"><img ng-src="{{index1==3?\'images/btOrderPre.png\':\'images/btOrderNor.png\'}}" >订单管理</a></p>' +
                    '<div class="administration_content" >' +
                    '<p><a ui-sref="order" ng-class="{\'activeNavbarSide\':index2==31}">查询</a></p>' +
                    '</div>' +
                    '</li>' +
                    '<li>' +
                    '<p class="stairMenu"><a  ng-class="{\'activeNavbarSide\':index1==4}"><img ng-src="{{index1==4?\'images/btOperationsPre.png\':\'images/btOperationsNor.png\'}}">视频管理</a></p>' +
                    '<div class="administration_content" >' +
                    '<p><a ui-sref="operation" ng-class="{\'activeNavbarSide\':index2==41}" >视频管理-展示</a></p>' +
                    '<p><a ui-sref="operationUploading" ng-class="{\'activeNavbarSide\':index2==42}" >视频管理-上传</a></p>' +
                    '</div>' +
                    '</li>' +
                    '<li>' +
                    '<p class="stairMenu"><a  ng-class="{\'activeNavbarSide\':index1==5}"><img ng-src="{{index1==5?\'images/bProxyPre.png\':\'images/bProxyNor.png\'}}" >代理管理</a></p>' +
                    '<div class="administration_content" >' +
                    '<p><a ui-sref="agency" ng-class="{\'activeNavbarSide\':index2==51}" >查询</a></p>' +
                    '</div>' +
                    '</li>' +
                    '<li >' +
                    '<p class="stairMenu"><a ng-class="{\'activeNavbarSide\':index1==6}"><img ng-src="{{index1==6?\'images/btFinancialPre.png\':\'images/btFinancialNor.png\'}}">财务管理</a></p>' +
                    '<div class="administration_content" >' +
                    '<p><a ui-sref="finance" ng-class="{\'activeNavbarSide\':index2==61}">账单管理</a></p>' +
                    '<p><a ui-sref="balance" ng-class="{\'activeNavbarSide\':index2==62}">余额管理</a></p>' +
                    '<p><a ui-sref="withdraw" ng-class="{\'activeNavbarSide\':index2==63}">提现管理</a></p>' +
                    '<p><a ui-sref="monthlyAccounts" ui-sref="withdraw" ng-class="{\'activeNavbarSide\':index2==64}" >佣金管理</a></p>' +
                    '</div>' +
                    '</li>' +

                    /*  '<li >' +
                     '<p><a ui-sref="peopleAccounts" ng-class="{\'active\':index1==7}"><img ng-src="{{index1==7?\'images/device02.png\':\'images/device01.png\'}}">人员管理</a></p>' +
                     '<div class="administration_content" >' +

                     '<p><a ui-sref="peopleNewAccount" >新增人员</a></p>' +
                     '<p><a ui-sref="menu" >权限管理</a></p>' +
                     '</div>' +
                     '</li>' +*/
                    '<div style="height:20px;"></div>' +
                    '</ul>' +
                  /*  '<p class="copy">版权所有 © 天津美享网络科技股份有限公司</p>' +*/
                    '</div>' +
                    '</div>' +
                    '</div>',
                    link: function (scope, ele, attrs) {
                        var h = $(window).height();
                        $(".navbar_side").height(h-116);
                        var w = $(window).width()-260;
                        $(" .main_content").width(w);
                        $(" .main_content").height(h);

                        /* var stairMenu = document.querySelectorAll(".stairMenu");
                         var secondLevelMenu = document.querySelectorAll(".administration_content");
                         console.log(stairMenu);
                         for(var i=0;i<stairMenu.length;i++){
                         secondLevelMenu[i].style.display="none";
                         stairMenu[i].onclick = function(){
                         console.log(this);

                         for(var j=0;j<stairMenu.length;j++){
                         stairMenu[j].getElementsByTagName('a')[0].className='';
                         }
                         change(this);

                         }
                         }
                         function change(obj){
                         for(var i=0;i<secondLevelMenu.length;i++){
                         if(stairMenu[i]==obj){
                         if(secondLevelMenu[i].style.display == "none"){
                         secondLevelMenu[i].style.display="block";
                         stairMenu[i].getElementsByTagName('a')[0].className="activeNavbarSide"
                         }
                         else
                         secondLevelMenu[i].style.display="none";
                         }
                         }
                         }*/
                    }
                }
            }])
        .directive('sixModel', ['$rootScope','$state','$timeout',
            function ($rootScope,$state,$timeout) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    scope: {
                        index: '@'
                    },
                    template: '<div class="btn_lis">' +
                    '<div class="center">' +
                    '<button ui-sref="home" ng-class="{\'active\':index==0}" >商品管理</button>' +
                    '<button  ui-sref="order" ng-class="{\'active\':index==1}" >订单管理</button>' +
                    '<button  ui-sref="operation" ng-class="{\'active\':index==2}" >视频管理</button>'+
                    '<button  ui-sref="agency" ng-class="{\'active\':index==3}" >代理管理</button>' +
                    '<button  ui-sref="finance" ng-class="{\'active\':index==4}" >财务管理</button>' +
                   /* '<button  ui-sref="peopleAccounts" ng-class="{\'active\':index==5}">人员管理</button>' +*/
                    '</div>' +
                    '</div>',
                    link: function (scope, ele, attrs) {

                    }
                }

            }])
        //分页
        .directive('tmPagination', [function () {
            return {
                restrict: 'EA',
                template: '<div class="page-list">' +
                '<ul class="pagination" ng-show="conf.totalItems > 0">' +
                '<li ng-class="{disabled: conf.currentPage == 1}" ng-click="prevPage()"><span>&laquo;</span></li>' +
                '<li ng-repeat="item in pageList track by $index" ng-class="{active: item == conf.currentPage, separate: item == \'...\'}" ' +
                'ng-click="changeCurrentPage(item)">' +
                '<span>{{ item }}</span>' +
                '</li>' +
                '<li ng-class="{disabled: conf.currentPage == conf.numberOfPages}" ng-click="nextPage()"><span>&raquo;</span></li>' +
                '</ul>' +
                '<div class="page-total" ng-show="conf.totalItems > 0">' +
                '每页<select ng-model="conf.itemsPerPage" ng-options="option for option in conf.perPageOptions " ng-change="changeItemsPerPage()"></select>' +
                '<span class="page-total" style="float: left; line-height: 52px; margin-right: 10px;" ng-show="conf.totalItems > 0">' +
                '共<strong>{{ conf.totalItems }}</strong>条 ' +
                '</span>' +
                '跳转至<input type="text" ng-model="jumpPageNum" ng-keyup="jumpPageKeyUp($event)"/>' +
                '</div>' +
                '<div class="no-items" ng-show="conf.totalItems <= 0">暂无数据</div>' +
                '</div>',



                replace: true,
                scope: {
                    conf: '='
                },
                link: function (scope, element, attrs) {
                    var conf = scope.conf;

                    // 默认分页长度
                    var defaultPagesLength = 9;

                    // 默认分页选项可调整每页显示的条数
                    var defaultPerPageOptions = [10, 15, 20, 30, 50];

                    // 默认每页的个数
                    var defaultPerPage = 15;

                    // 获取分页长度
                    if (conf.pagesLength) {
                        // 判断一下分页长度
                        conf.pagesLength = parseInt(conf.pagesLength, 10);

                        if (!conf.pagesLength) {
                            conf.pagesLength = defaultPagesLength;
                        }

                        // 分页长度必须为奇数，如果传偶数时，自动处理
                        if (conf.pagesLength % 2 === 0) {
                            conf.pagesLength += 1;
                        }

                    } else {
                        conf.pagesLength = defaultPagesLength
                    }

                    // 分页选项可调整每页显示的条数
                    if (!conf.perPageOptions) {
                        conf.perPageOptions = defaultPagesLength;
                    }

                    // pageList数组
                    function getPagination(newValue, oldValue) {

                        // conf.currentPage
                        if (conf.currentPage) {
                            conf.currentPage = parseInt(scope.conf.currentPage, 10);
                        }

                        if (!conf.currentPage) {
                            conf.currentPage = 1;
                        }

                        // conf.totalItems
                        if (conf.totalItems) {
                            conf.totalItems = parseInt(conf.totalItems, 10);
                        }

                        // conf.totalItems
                        if (!conf.totalItems) {
                            conf.totalItems = 0;
                            return;
                        }

                        // conf.itemsPerPage
                        if (conf.itemsPerPage) {
                            conf.itemsPerPage = parseInt(conf.itemsPerPage, 10);
                        }
                        if (!conf.itemsPerPage) {
                            conf.itemsPerPage = defaultPerPage;
                        }

                        // numberOfPages
                        conf.numberOfPages = Math.ceil(conf.totalItems / conf.itemsPerPage);

                        // 如果分页总数>0，并且当前页大于分页总数
                        if (scope.conf.numberOfPages > 0 && scope.conf.currentPage > scope.conf.numberOfPages) {
                            scope.conf.currentPage = scope.conf.numberOfPages;
                        }

                        // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                        var perPageOptionsLength = scope.conf.perPageOptions.length;

                        // 定义状态
                        var perPageOptionsStatus;
                        for (var i = 0; i < perPageOptionsLength; i++) {
                            if (conf.perPageOptions[i] == conf.itemsPerPage) {
                                perPageOptionsStatus = true;
                            }
                        }
                        // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                        if (!perPageOptionsStatus) {
                            conf.perPageOptions.push(conf.itemsPerPage);
                        }

                        // 对选项进行sort
                        conf.perPageOptions.sort(function (a, b) {
                            return a - b
                        });


                        // 页码相关
                        scope.pageList = [];
                        if (conf.numberOfPages <= conf.pagesLength) {
                            // 判断总页数如果小于等于分页的长度，若小于则直接显示
                            for (i = 1; i <= conf.numberOfPages; i++) {
                                scope.pageList.push(i);
                            }
                        } else {
                            // 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
                            // 计算中心偏移量
                            var offset = (conf.pagesLength - 1) / 2;
                            if (conf.currentPage <= offset) {
                                // 左边没有...
                                for (i = 1; i <= offset + 1; i++) {
                                    scope.pageList.push(i);
                                }
                                scope.pageList.push('...');
                                scope.pageList.push(conf.numberOfPages);
                            } else if (conf.currentPage > conf.numberOfPages - offset) {
                                scope.pageList.push(1);
                                scope.pageList.push('...');
                                for (i = offset + 1; i >= 1; i--) {
                                    scope.pageList.push(conf.numberOfPages - i);
                                }
                                scope.pageList.push(conf.numberOfPages);
                            } else {
                                // 最后一种情况，两边都有...
                                scope.pageList.push(1);
                                scope.pageList.push('...');

                                for (i = Math.ceil(offset / 2); i >= 1; i--) {
                                    scope.pageList.push(conf.currentPage - i);
                                }
                                scope.pageList.push(conf.currentPage);
                                for (i = 1; i <= offset / 2; i++) {
                                    scope.pageList.push(conf.currentPage + i);
                                }

                                scope.pageList.push('...');
                                scope.pageList.push(conf.numberOfPages);
                            }
                        }

                        scope.$parent.conf = conf;
                    }

                    // prevPage
                    scope.prevPage = function () {
                        if (conf.currentPage > 1) {
                            conf.currentPage -= 1;
                        }
                        getPagination();
                        if (conf.onChange) {
                            conf.onChange();
                        }
                    };

                    // nextPage
                    scope.nextPage = function () {
                        if (conf.currentPage < conf.numberOfPages) {
                            conf.currentPage += 1;
                        }
                        getPagination();
                        if (conf.onChange) {
                            conf.onChange();
                        }
                    };

                    // 变更当前页
                    scope.changeCurrentPage = function (item) {

                        if (item == '...') {
                            return;
                        } else {
                            conf.currentPage = item;
                            getPagination();
                            // conf.onChange()函数
                            if (conf.onChange) {
                                conf.onChange();
                            }
                        }
                    };

                    // 修改每页展示的条数
                    scope.changeItemsPerPage = function () {

                        // 一发展示条数变更，当前页将重置为1
                        conf.currentPage = 1;

                        getPagination();
                        // conf.onChange()函数
                        if (conf.onChange) {
                            conf.onChange();
                        }
                    };

                    // 跳转页
                    scope.jumpToPage = function () {
                        num = scope.jumpPageNum;
                        if (num.match(/\d+/)) {
                            num = parseInt(num, 10);

                            if (num && num != conf.currentPage) {
                                if (num > conf.numberOfPages) {
                                    num = conf.numberOfPages;
                                }

                                // 跳转
                                conf.currentPage = num;
                                getPagination();
                                // conf.onChange()函数
                                if (conf.onChange) {
                                    conf.onChange();
                                }
                                scope.jumpPageNum = '';
                            }
                        }

                    };

                    scope.jumpPageKeyUp = function (e) {
                        var keycode = window.event ? e.keyCode : e.which;

                        if (keycode == 13) {
                            scope.jumpToPage();
                        }
                    }

                    scope.$watch('conf.totalItems', function (value, oldValue) {

                        // 在无值或值相等的时候，去执行onChange事件
                        if (!value || value == oldValue) {

                            if (conf.onChange) {
                                conf.onChange();
                            }
                        }
                        getPagination();
                    })

                }
            };
        }])
        //home公共部分
        .directive('homeTop', ['$rootScope','$state','$timeout',
            function ($rootScope,$state,$timeout) {
            return {
                restrict: 'EA',
                template: '<div class="home"> <div class="shortcut"> ' +
                '<div class="shortcut_box"> <ul> <li> <a href="">唯美度跨境购欢迎您~</a> ' +
                '<a ng-click="goFn(\'login\')" href="">登录</a> <a ng-click="goFn(\'goodsList\')" href="">首页</a> </li> <li> <a ng-click="goFn(\'orderList\')" href="">我的订单</a> ' +
                '<a href="">客户服务</a> <div class="kefu">客服电话：19817162521</div></li> </ul> </div> </div> <div class="header"> ' +
                '<div class="logo"> <img src="images/mall_img/logo.png" alt=""> </div> ' +
                '<div class="search"> <div class="search_box"> <input ng-model="params.inportContent" type="text" placeholder="请搜索商品">' +
                '<div ng-click="searchGoods()"><img src="images/mall_img/search.png" alt=""></div> </div> </div> <div class="settleup"> ' +
                '<div ng-click="goFn(\'shoppingCart\')"> <i></i>购物车 </div> </div> </div></div>',
                replace: true,
                scope: {
                    params: '='
                },
                link: function (scope, element, attrs) {
                    scope.searchGoods = function () {
                        $state.go('goodsList')
                        scope.params.fn(scope.params.inportContent)
                    }
                    scope.goFn = function (bar) {
                        $state.go(bar)
                    }
                }
            };
        }])

   });
