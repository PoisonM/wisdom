angular.module('controllers',[]).controller('operationClassParticularsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ManagementUtil','$http','GetTrainingProductDetail','UpdateTrainingProduct',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ManagementUtil,$http,GetTrainingProductDetail,UpdateTrainingProduct) {
            $scope.hintPic1='';
            $scope.hintPic2='';
            $scope.mum = true;
            $scope.mess = false;
            $scope.uploadingPar ={
                firstUrl:[],
                productDetail:{
                    detailPic:[]
                }
            };
            $scope.frame=new Array;
            $scope.framePic=new Array;
/*展示*/
            $scope.loadPageList = function(){
                GetTrainingProductDetail.get({
                    productId:$stateParams.id
                },function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS) {
                        $scope.data = data.responseData;
                        $scope.mum = false;
                        $scope.rainingList = $scope.data.productDetail;
                        pic(data,"#particulars_viewPic","detailList");
                        pic(data,"#publicityPic","firstUrl");
                        remove("#particulars_viewPic","detailPic");
                        remove("#publicityPic","firstUrl");
                        $scope.hideOrShow($scope.rainingList);
                        $scope.hideOrShowPic($scope.rainingList);
                        $scope.uploadingPar.productDetail.detailPic=$scope.uploadingPar.productDetail.detailPic.concat($scope.data.productDetail.detailList);
                        $scope.uploadingPar.firstUrl=$scope.data.firstUrl;
                        if($scope.uploadingPar.productDetail.detailPic.length>=1){
                            $scope.hintPic2 ="images/true.png"
                        }
                        if($scope.uploadingPar.firstUrl !=""){
                            $scope.hintPic1 ="images/true.png"
                        }
                    }
                })
            };
            $scope.loadPageList();

 /*保存*/
            $scope.saveClass = function(){
                $scope.data.firstUrl=$scope.uploadingPar.firstUrl;
                $scope.data.productDetail.detailList=$scope.uploadingPar.productDetail.detailPic;
                var product = $scope.data;
                if(product.firstUrl.length<=0||product.productDetail.detailList.length<=0||product.productName=="")
                {
                    $scope.mess=true;
                    console.log(1)
                }
                for(var i=0;i<product.productDetail.listCourse.length;i++){
                    if(product.productDetail.listCourse[i].name ==''){
                        $scope.mess=true;
                        console.log(2)
                    }
                    for(var j =0;j<product.productDetail.listCourse[i].list.length;j++){
                        if(product.productDetail.listCourse[i].list[j].duration ==""||product.productDetail.listCourse[i].list[j].name==""||product.productDetail.listCourse[i].list[j].url==""){
                            $scope.mess=true;
                            console.log(3)
                        }
                    }
                }
                console.log(product)
                UpdateTrainingProduct.save(product,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS) {
                        $state.go("operation");
                        $scope.mess=false
                    }
                })
            };
/*添加课程*/
            $scope.addClass = function(index1){
                if(index1 == -1){
                    $scope.rainingList.listCourse.unshift({name:"",list:[{duration:"",name:"",password:"",url:""}]}) ;
                    $scope.frame.unshift({classFlag:true,sectionFlag:[true],outsideClass:"outsideBorder",outsideSection:["outsideBorder"]});
                }else{
                    $scope.rainingList.listCourse.splice(index1+1,0,{name:"课程",list:[{duration:"时长",name:"小节",password:"",url:""}]}) ;
                    $scope.frame.splice(index1+1,0,{classFlag:true,sectionFlag:[true],outsideClass:"outsideBorder",outsideSection:["outsideBorder"]});
                    $scope.hideOrShowPic($scope.rainingList);
                    $scope.framePic[index1+1][0].hintPic="";
                    var length = $scope.frame.length-1;
                }
            };
 /*删除课程*/
            $scope.delClass = function(index){
                $scope.rainingList.listCourse.splice(index,1);
                $scope.frame.splice(index,1)
            };
  /*添加小节*/
           $scope.addSection = function(indexClass,indexSection){
               $scope.rainingList.listCourse[indexClass].list.splice(indexSection+1,0,{duration:"时长",name:"小节",password:"",url:"http://"});
               $scope.hideOrShowPic($scope.rainingList);
               $scope.framePic[indexClass][indexSection+1].hintPic="";
               $scope.frame[indexClass].sectionFlag.splice(indexSection+1,0,true);
               $scope.frame[indexClass].outsideClass = "outsideBorder";
               $scope.frame[indexClass].outsideSection.splice(indexSection+1,0,"outsideBorder");
           };
/*删除小节*/
            $scope.delSection = function(indexClass,indexSection){
                $scope.rainingList.listCourse[indexClass].list.splice(indexSection,1);
                $scope.frame[indexClass].outsideSection.splice(indexSection,1);
            };
 /*显示与隐藏*/
            $scope.hideOrShow = function(data){
                for(var i=0;i<data.listCourse.length;i++){
                    $scope.frame[i]=new Array;
                    $scope.frame[i] ={classFlag:false,sectionFlag:[],outsideClass:"",outsideSection:[]};
                    for(var j=0;j<data.listCourse[i].list.length;j++){
                        $scope.frame[i].sectionFlag[j]=false;
                        $scope.frame[i].outsideSection[j]="void";
                    }
                }
            };

/*失去焦点时为空显示边框*/
            $scope.voidBorder = function(index1,index2){
                if($scope.data.productDetail.listCourse[index1].name ==""){
                    $scope.frame[index1].outsideClass = "outsideBorder"
                }else{
                    $scope.frame[index1].outsideClass = "void"
                }
                if(index2 != undefined){
                    if($scope.data.productDetail.listCourse[index1].list[index2].name == ""||$scope.data.productDetail.listCourse[index1].list[index2].url==""||$scope.data.productDetail.listCourse[index1].list[index2].duration==""){
                        $scope.frame[index1].outsideSection[index2] = "outsideBorder";
                    }else{
                        $scope.frame[index1].outsideSection[index2] = "void";
                    }
                }
            };

 /*上传图片成功或失败的图片显示*/
            $scope.hideOrShowPic = function(data){
                for(var i=0;i<data.listCourse.length;i++){
                    $scope.framePic[i]=new Array;
                    for(var j=0;j<data.listCourse[i].list.length;j++){
                        $scope.framePic[i][j]={hintPic:"images/true.png",blueBgLight:'grayBg', a:1,myFile:""};
                    }
                }
            };
/*手风琴效果*/
            $scope.hideOrShowClass = function(index1){
                $scope.frame[index1].classFlag=! $scope.frame[index1].classFlag;
            };
            $scope.hideOrShowSection = function(index1,index2){
                $scope.frame[index1].sectionFlag[index2]=! $scope.frame[index1].sectionFlag[index2];
            };
/*上传图片*/
            $scope.hintPic1 ="";
            $scope.hintPic2 ="";
            $scope.uploadingPic = function(id,big){
                var input = document.getElementById(id);
                var big1 = document.getElementById(big);
                var result,div;
                if(typeof FileReader==='undefined'){
                    result.innerHTML = "抱歉，你的浏览器不支持 FileReader";
                    input.setAttribute('disabled','disabled');
                }else{
                    input.addEventListener('change',readFile,false);
                }
                function readFile(){
                    var as = big1.querySelectorAll('.as' );
                    /*if(as !=undefined){
                     for(var i=0;i<as.length;i++){
                     big1.removeChild(as[i])
                     }
                     }*/
                   if(id=="particulars_view"){
                        $scope.hintPic2="";
                    }else if(id=="publicity"){
                        $scope.hintPic1="";
                       for(var i=0;i<as.length;i++){
                           big1.removeChild(as[i])
                       }

                    }
                    for(var i=0;i<this.files.length;i++){
                        if (!input['value'].match(/.jpg|.gif|.png|.bmp/i)){
                            return alert("上传的图片格式不正确，请重新选择")
                        }
                        var reader = new FileReader();
                        reader.readAsDataURL(this.files[i]);
                        reader.onload = function(e){
                            result = '<img src="'+this.result+'" alt=""/>';
                            div = document.createElement('div');
                            div.className="as";
                            var img1 = document.createElement('img');
                            img1.src = "images/cha.png";
                            img1.className="falsePic";
                            div.innerHTML = result;
                            div.appendChild(img1);
                            big1.appendChild(div);
                        }
                    }


                    $scope.ptoductType = "trainingImg/";
                    var MultipartFile = new FormData();
                    MultipartFile.append("folder",$scope.ptoductType);
                    for(var i=0;i<this.files.length;i++){
                        var reader = new FileReader();
                        reader.readAsDataURL(this.files[i]);
                        MultipartFile.append("listFile",this.files[i]);
                    }
                    var url = "/system/file/imageUploadToOSS";

                    $http.post(url,MultipartFile,{
                        transformRequest: angular.identity,
                        headers: {
                            'Content-Type': undefined
                        }
                    }).success(function(data) {
                        ManagementUtil.checkResponseData(data,"");
                        if(data.errorInfo == Global.SUCCESS){
                            if(id=="particulars_view"){
                                $scope.hintPic2="images/true.png";
                                $scope.uploadingPar.productDetail.detailPic=$scope.uploadingPar.productDetail.detailPic.concat(data.responseData);
                                remove("#particulars_viewPic","detailPic");
                            }else if(id=="publicity"){
                                $scope.uploadingPar.firstUrl= data.responseData[0];
                                $scope.hintPic1="images/true.png";
                                remove("#publicityPic","firstUrl");

                            }
                        }else{
                            alert("上传图片失败")
                           /* delError(id)*/
                        }
                    }).error(function(){
                        alert("上传图片失败")
                        /*delError(id)*/
                    })
                }
            };
            $scope.uploadingPic("particulars_view","particulars_viewPic");
            $scope.uploadingPic("publicity","publicityPic","firstUrl");
 //图片上传失败 删除
            function delError (id){
                if(id=="particulars_view"){
                    remove("#particulars_viewPic","detailPic");
                }else if(id=="publicity"){
                    remove("#publicityPic","firstUrl");
                }
            }
/*图片上传成功  删除*/
            function remove (id,name,index){
                var patter = document.querySelector(id);
                var img = patter.querySelectorAll(".falsePic");
                var div = patter.getElementsByTagName("div");
                for(var i=0;i<img.length;i++){
                    img[i].onclick = function(){
                        change(this)
                    }
                }
                function change(obj){
                    for(var i=0;i<img.length;i++){
                        if(img[i]==obj){
                            id = id.replace(/\s+/g,"");
                            console.log(id);
                            console.log(id.length);
                            console.log("#publicityPic".length);
                            if(id!="#publicityPic"){
                                $scope.uploadingPar.productDetail[name].splice(i,1);
                                if($scope.uploadingPar.productDetail.detailPic.length<1){
                                    $scope.hintPic2 ="";
                                }
                            }else{
                                $scope.uploadingPar.firstUrl='';
                                $scope.hintPic1 ="";

                            }
                            patter.removeChild(div[i]);
                            remove("#publicityPic","firstUrl");
                            remove("#particulars_viewPic","detailPic");


                        }
                    }
                }


            }
/*图片展示*/
            function pic(data,name,picName){
                var  listPic=[]
                var big = document.querySelector(name);
                if(data.responseData.productDetail){
                    if(picName=="firstUrl"){
                        listPic[0]= data.responseData[picName];
                    }else{
                        listPic= data.responseData.productDetail[picName];
                    }
                    for(var i=0;i<listPic.length;i++){
                        var div = document.createElement('div');
                        div.className="as";
                        var img = document.createElement('img');
                        var img1 = document.createElement('img');
                        img1.src = "images/cha.png";
                        img1.className="falsePic";
                        img.src=  listPic[i];
                        div.appendChild(img1);
                        div.appendChild(img);
                        big.appendChild(div);
                    }
                }
            }
 //上传视频
            $scope.activeBtn = function(){
                $scope.hintPic = "";
                $scope.blueBgLight="grayBg";
            }
            $scope.onFileSelect = function (files,index,index1) {
                $scope.framePic[index][index1].myFile = files;
            };
            $scope.uploadFile = function(index,index1){
                if($scope.framePic[index][index1].myFile == ""){
                    alert("请选择要上传的视频");
                    return
                }
                $scope.framePic[index][index1].a=$scope.framePic[index][index1].a+1;
                if($scope.framePic[index][index1].a!=2){
                    $scope.framePic[index][index1].a!=1
                    alert("视频正在奔跑中...  请稍后");
                    return;
                }
                $scope.framePic[index][index1].hintPic="";
                $scope.framePic[index][index1].blueBgLight ="blueBg";
                var file = $scope.framePic[index][index1].myFile;
                var wavArea = document.querySelectorAll(".wavArea");
                var wavArr = [];
                for(i=0;i<wavArea.length;i++){
                    var n = wavArea[i].querySelectorAll(".wav");
                    wavArr[i] ={};
                    for(j=0;j<n.length;j++){
                        wavArr[i].wav = n
                    }
                }
                var wav = wavArr[index].wav[index1].value;
                if (!wav.match(/.mp4|.mpeg|.avi|.rm|.wmv|.mov/i)){
                    $scope.framePic[index][index1].a=1;
                    $scope.framePic[index][index1].blueBgLight = 'grayBg';　
                    　//判断上传文件格式
                    console.log(!wav.match(/.mp4|.mpeg|.avi|.rm|.wmv|.mov/i));　//判断上传文件格式
                    return alert("上传的视频格式不正确，请重新选择")
                }
                var uploadUrl = "/system/file/aviUploadToOSS";
                var MultipartFile = new FormData();
                for(var i=0;i<file.length;i++){
                    MultipartFile.append("listFile",file[i]);
                }
                $http.post(uploadUrl, MultipartFile, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).success(function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.errorInfo == Global.SUCCESS){
                        $scope.framePic[index][index1].hintPic="images/true.png";
                        $scope.framePic[index][index1].a=1;
                        $scope.data.productDetail.listCourse[index].list[index1].url = data.result
                    }else{
                        $scope.framePic[index][index1].hintPic="images/false.png";
                        $scope.framePic[index][index1].a=1;
                        alert("plese again")
                    }
                }).error(function(){
                    $scope.framePic[index][index1].hintPic="images/false.png";
                    $scope.blueBgLight="grayBg";
                    $scope.framePic[index][index1].a=1;
                    alert("plese again")
                })
            };



        }]).directive('ngFileSelect', [ '$parse', '$timeout', function($parse, $timeout) {
    return function(scope, elem, attr) {
        var fn = $parse(attr['ngFileSelect']);
        elem.bind('change', function(evt) {
            var files = [], fileList, i;
            fileList = evt.target.files;
            if (fileList != null) {
                for (i = 0; i < fileList.length; i++) {
                    files.push(fileList.item(i));
                }
            }
            $timeout(function() {
                fn(scope, {
                    $files : files,
                    $event : evt
                });
            });
        });
    };
}]);