angular.module('controllers',[]).controller('operationUploadingCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','$http','ManagementUtil','AddTrainingProduct',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,$http,ManagementUtil,AddTrainingProduct) {
            $scope.hintPic = "";
            $scope.mess = false;
            var a =1;
            var status =document.querySelector("#status");
             $scope.productDTO={
                 brand:"唯美度",
                 description:"",
                 firstUrl:"",
                 price:'',
                 productName:'',
                 secondType:'系统课程',
                 type:"training",
                 status: status.options[status.selectedIndex].value,
                 productDetail:{
                    detailList:[],
                    listCourse:[{
                        name:'第一课',
                        list:[{
                            name:"第一节",
                            duration:'0分0秒',
                            url:'123'
                        }]
                    }]}
            };
      /*上传视频*/

            $scope.onFileSelect = function (files) {
                $scope.myFile = files;
            };
            $scope.uploadFile = function(){
                a++;
                if(a!=2){
                    alert("视频正在奔跑中...  请稍后");
                    a=1;
                    return;
                }

                $scope.blueBgLight="blueBgUP";
                $scope.framePic[index][index1].hintPic="";
                var file = $scope.myFile;
                var wav = document.querySelector(".wav").value;
                if (!wav.match(/.mp4|.mpeg|.avi|.rm|.wmv|.mov/i)){
                    a=1;
                    $scope.blueBgLight　='grayBg';
                    return alert("上传的视频格式不正确，请重新选择")
                }
                var uploadUrl = "/system/file/aviUploadToOSS";
                var File = new FormData();
                for(var i=0;i<file.length;i++){
                    File.append("listFile",file[i]);
                }
                $http.post(uploadUrl, File, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).success(function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.errorInfo == Global.SUCCESS){
                         $scope.hintPic="images/true.png";
                        productDTO.productDetail.listCourse[0].list[0].url=data.result;
                         a=1;
                    }else{
                         $scope.hintPic="images/false.png"
                    }
                }).error(function(data){
                         $scope.hintPic="images/false.png"
                })
            };
            $scope.submit = function(){
                if($scope.productDTO.firstUrl==""||$scope.productDTO.price==""||$scope.productDTO.productName==""||$scope.productDTO.secondType==""||$scope.productDTO.productDetail.detailList.length==0||$scope.productDTO.productDetail.listCourse[0].name==""||$scope.productDTO.productDetail.listCourse[0].list[0].name==""||$scope.productDTO.productDetail.listCourse[0].list[0].duration==""||$scope.productDTO.productDetail.listCourse[0].list[0].url==""){
                    $scope.mess = true;
                    return;
                }
                $scope.productDTO.status = status.options[status.selectedIndex].value;
                AddTrainingProduct.save($scope.productDTO,function(data) {
                    ManagementUtil.checkResponseData(data,"");
                    if(data.errorInfo == Global.SUCCESS){
                        $state.go("operation")
                    }
                })

            };

            //删除图片
            function remove (name,picArr,id,div){
                var img = document.querySelectorAll(name);
                var patter = document.querySelector(id);
                var div = document.querySelectorAll(div);
                for(var i=0;i<img.length;i++){
                    img[i].onclick = function(){
                        change(this);
                    }
                }
                function change(obj){
                    for(var i=0;i<img.length;i++){
                        if(img[i]==obj){
                            if(id!="#publicityPic"){
                                $scope.productDTO.productDetail[picArr].splice(i,1);
                            }else{
                                $scope.productDTO.firstUrl=''
                            }

                            patter.removeChild(div[i]);
                            if($scope.productDTO.firstUrl==""){
                                $scope.hintPic1 ="";
                            }

                            if($scope.productDTO.productDetail.detailList.length<=0){
                                $scope.hintPic2 ="";
                            }
                            remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");

                            remove("#particulars_viewPic .falsePic","detailList","#particulars_viewPic","#particulars_viewPic div");

                        }
                    }

                }
            }
 //上传图片
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
                    var ptoductType = "trainingImg/";
                    if(id=="particulars_view"){
                        $scope.hintPic2="images/true.png";
                    }else if(id=="publicity"){
                        $scope.hintPic1="images/true.png";
                    }
                    var MultipartFile = new FormData();
                    MultipartFile.append("folder",ptoductType);
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
                                $scope.productDTO.productDetail.detailList=$scope.productDTO.productDetail.detailList.concat(data.responseData)
                                 remove("#particulars_viewPic .falsePic","detailList","#particulars_viewPic","#particulars_viewPic div");

                            }else if(id=="publicity"){
                                $scope.productDTO.firstUrl= data.responseData[0];
                                $scope.hintPic1="images/true.png";
                                 remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");
                            }
                        }else{
                            if(id=="particulars_view"){
                                $scope.hintPic2="images/true.png";

                            }else if(id=="publicity"){
                                $scope.hintPic1="images/true.png";
                            }
                            alert("上传图片失败");
                            delError(id)
                        }
                    }).error(function(){
                        alert("上传图片失败");
                        console.log($scope.productDTO.firstUrl);
                        delError(id)
                    })
                }
            };

            $scope.uploadingPic("particulars_view","particulars_viewPic");
            $scope.uploadingPic("publicity","publicityPic","firstUrl");

//图片上传失败
            function delError (id){
                if(id=="particulars_view"){
                    remove("#particulars_viewPic .falsePic","detailList","#particulars_viewPic","#particulars_viewPic div");

                }else if(id=="publicity"){
                    remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");
                }
            }


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