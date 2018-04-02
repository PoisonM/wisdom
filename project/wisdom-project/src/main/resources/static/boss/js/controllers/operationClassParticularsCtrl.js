angular.module('controllers',[]).controller('operationClassParticularsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','ManagementUtil','QueryAllTrainingProductDTO','$http',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,ManagementUtil,QueryAllTrainingProductDTO,$http) {
            $scope.hintPic1='';
            $scope.hintPic2='';
            $scope.uploadingPar ={
                firstUrl:[],
                productDetail:{
                    detailPic:[]
                }
            };
            $scope.frame=new Array;
            $scope.saveClass = function(){
                $state.go("operation")
            };
            var big = document.querySelector("#list_viewPic");
            var div = document.createElement('div');
            div.className="classUrl";
            div.innerHTML=  $stateParams.url;
            big.appendChild(div);
            $scope.loadPageList = function(){
                QueryAllTrainingProductDTO.save({
                    pageNo:1,
                    pageSize:5
                },function(data){
                    console.log(data);
                    ManagementUtil.checkResponseData(data,"");
                    console.log(data);
                    if(data.result == Global.SUCCESS){
                        $scope.rainingList = data.responseData.responseData[0];
                        console.log($scope.rainingList)
                        $scope.mum = false;
                        for(var i=0;i<$scope.rainingList.listCourse.length;i++){
                            $scope.frame[i]=new Array;
                            for(var j=0;j<$scope.rainingList.listCourse[i].list.length;j++){
                                $scope.frame[i][j]={hintPic:"",blueBgLight:'grayBg',a:1,myFile:""};
                            }
                        }

                    }

                })
            };
            $scope.loadPageList();
            $scope.tabArray=[
                {"productFlag":[]},
                {"productFlag":[]},
                {"productFlag":[]},
                {"productFlag":[]}
            ];
            $scope.ab = function(num,type){
                for(var i=0;i<=num;i++){
                    $scope.tabArray[type].productFlag[i]=false;
                }
            };
            $scope.ab(1,2);
            $scope.ab(15,3);
            $scope.classShow = function(indexes,type,num){
                $scope.types=$scope.tabArray[type];
                $scope.types.productFlag[indexes]=!$scope.types.productFlag[indexes];
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
                    var url = "/business/system/imageUploadToOSS";

                    $http.post(url,MultipartFile,{
                        transformRequest: angular.identity,
                        headers: {
                            'Content-Type': undefined
                        }
                    }).success(function(data) {
                        console.log(data);
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
 //图片上传失败 删除
            function delError (id){
                if(id=="particulars_view"){
                    remove("#particulars_viewPic .falsePic","detailPic","#particulars_viewPic","#particulars_viewPic div");

                }else if(id=="publicity"){
                    remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");
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
                            if(id!="#publicityPic"){
                                $scope.uploadingPar.productDetail[name].splice(i,1);
                            }else{
                                $scope.uploadingPar.firstUrl=''
                            }
                            patter.removeChild(div[i]);
                            remove("#publicityPic","firstUrl");
                            remove("#particulars_viewPic","detailPic");
                            if($scope.uploadingPar.firstUrl==""){
                                $scope.hintPic1 ="";
                            }
                            if($scope.uploadingPar.productDetail.detailPic.length<1){
                                $scope.hintPic2 ="";
                            }
                        }
                    }
                }


            }


 //上传视频

            $scope.activeBtn = function(){
                $scope.hintPic = "";
                $scope.blueBgLight="grayBg";
            }
            $scope.onFileSelect = function (files,index,index1) {
                $scope.frame[index][index1].myFile = files;
            };
            $scope.uploadFile = function(index,index1){
                console.log($scope.frame);
                if($scope.frame[index][index1].myFile == ""){
                    alert("请选择要上传的视频");
                    return
                }
                $scope.frame[index][index1].a=$scope.frame[index][index1].a+1
                if($scope.frame[index][index1].a!=2){
                    alert("视频正在奔跑中...  请稍后");
                    return;
                }
                $scope.frame[index][index1].blueBgLight ="blueBg";
                var file = $scope.frame[index][index1].myFile;
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
                    console.log(data);
                    ManagementUtil.checkResponseData(data,"");
                    if(data.result == Global.SUCCESS){
                        $scope.frame[index][index1].hintPic="images/true.png";
                        $scope.frame[index][index1].a=1;
                        alert("plese again")
                    }else{
                        $scope.frame[index][index1].hintPic="images/false.png";
                        $scope.frame[index][index1].a=1;
                        alert("plese again")
                    }
                }).error(function(){
                    $scope.frame[index][index1].hintPic="images/false.png";
                    $scope.blueBgLight="grayBg";
                    $scope.frame[index][index1].a=1;
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