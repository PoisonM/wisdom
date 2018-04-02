angular.module('controllers',[]).controller('operationUploadingCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','$http','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,$http,ManagementUtil) {
            $scope.hintPic = "";
            var a =1;
            $scope.param={
                className:"拓客新思维",
                section:"第一课",
                minutia:"第一节",
                introduce:"系统课程",
                address:""
            }

            $scope.ProductDTO = {
                firstUrl:[],
                productDetail:{
                    detailPic:[]
                }
            }
/*上传视频*/
            $scope.activeBtn = function(){
                $scope.hintPic = "";
                $scope.blueBgLight="";
            }
            $scope.onFileSelect = function (files) {
                $scope.myFile = files;
            };
            $scope.uploadFile = function(){
                a++;
                if(a!=2){
                    alert("视频正在奔跑中...  请稍后");
                    return;
                }
                $scope.blueBgLight="blueBg";
                var file = $scope.myFile;
                var wav = document.querySelector(".wav").value;
                if (!wav.match(/.mp4|.mpeg|.avi|.rm|.wmv|.mov/i)){　　//判断上传文件格式
                    return alert("上传的图片格式不正确，请重新选择")
                }
                var uploadUrl = "/business/operation/aviUploadToOSS";
                var File = new FormData();
                for(var i=0;i<file.length;i++){
                    File.append("listFile",file[i]);
                }
                $http.post(uploadUrl, File, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).success(function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                         $scope.hintPic="images/true.png";
                         $scope.param.address=data.errorInfo;
                         a=1;
                    }else{
                         $scope.hintPic="images/false.png"
                    }
                }).error(function(){
                         $scope.hintPic="images/false.png"
                })
            };
            $scope.submit = function(){
                  $state.go("operation")
            }
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
                            console.log($scope.ProductDTO)
                            console.log(id)
                            console.log(id.length)
                            console.log("#publicityPic".length)
                            if(id!="#publicityPic"){
                                $scope.ProductDTO.productDetail[picArr].splice(i,1);
                            }else{
                                $scope.ProductDTO.firstUrl=''
                            }

                            patter.removeChild(div[i]);
                            if($scope.ProductDTO.firstUrl==""){
                                $scope.hintPic1 ="";
                            }

                            if($scope.ProductDTO.productDetail.detailPic.length<=0){
                                $scope.hintPic2 ="";
                            }
                            remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");

                            remove("#particulars_viewPic .falsePic","detailPic","#particulars_viewPic","#particulars_viewPic div");

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
                    /*if(as !=undefined){
                     for(var i=0;i<as.length;i++){
                     big1.removeChild(as[i])
                     }
                     }*/
                   if(id=="particulars_view"){
                        $scope.hintPic2="";
                    }else if(id=="publicity"){
                        $scope.hintPic1="";

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

                    var MultipartFile = new FormData();
                    MultipartFile.append("folder",ptoductType);
                    for(var i=0;i<this.files.length;i++){
                        var reader = new FileReader();
                        reader.readAsDataURL(this.files[i]);
                        MultipartFile.append("listFile",this.files[i]);
                    }
                    var url = "/business/system/imageUploadToOSS";

                    $http.post(url,MultipartFile,{
                        transformRequest: angular.identity,
                        headers: {
                            'Content-Type': undefined
                        }
                    }).success(function(data) {
                        console.log(data)
                        ManagementUtil.checkResponseData(data,"");
                        if(data.errorInfo == Global.SUCCESS){
                             if(id=="particulars_view"){
                                $scope.hintPic2="images/true.png";
                                $scope.ProductDTO.productDetail.detailPic=$scope.ProductDTO.productDetail.detailPic.concat(data.responseData)
                                 remove("#particulars_viewPic .falsePic","detailPic","#particulars_viewPic","#particulars_viewPic div");
                                 console.log($scope.ProductDTO)
                            }else if(id=="publicity"){
                                $scope.ProductDTO.firstUrl= data.responseData[0];
                                $scope.hintPic1="images/true.png";
                                 remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");
                            }
                        }else{
                            alert("上传图片失败")
                            delError(id)
                        }
                    }).error(function(){
                        alert("上传图片失败")
                        delError(id)
                    })
                }
            };

            $scope.uploadingPic("particulars_view","particulars_viewPic");
            $scope.uploadingPic("publicity","publicityPic","firstUrl");

//图片上传失败
            function delError (id){
                if(id=="particulars_view"){
                    remove("#particulars_viewPic .falsePic","detailPic","#particulars_viewPic","#particulars_viewPic div");

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