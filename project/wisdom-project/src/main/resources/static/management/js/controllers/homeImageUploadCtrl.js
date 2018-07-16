/**
 * Created by Administrator on 2018/1/11.
 */
angular.module('controllers',[]).controller('homeImageUploadCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','$http','ExportNextUserInfoControl','UpdateIncomeRecordStatusById','$filter','ManagementUtil','FindHomeBannerInfoById','UpdateHomeBanner','AddHomeBanner',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,$http,ExportNextUserInfoControl,UpdateIncomeRecordStatusById,$filter,ManagementUtil,FindHomeBannerInfoById,UpdateHomeBanner,AddHomeBanner) {

            $scope.showImage = true;
            $scope.imageType = "banner";

            $scope.back = function(){
                $state.go("homePageEditor");
            };
            $scope.bannerDTO={
                   bannerType:"",
                   firstUrl:"",
                   bannerRank:"",
                   uri:"",
                   forward:"",
                   bannerId:""
            };



            $scope.loadPageList  = function(){
                if($stateParams.bannerId!="0"){
                        FindHomeBannerInfoById.get({bannerId:$stateParams.bannerId},function(data){
                            $("#imageType").val(data.responseData.bannerType);
                            $("#sequence").val(data.responseData.bannerRank);
                            $("#imageUrl").val(data.responseData.forward);
                            $("#imageShow").attr("src",data.responseData.uri);

                        })
                   }else{
                        $scope.showImage = false;
                   }
             };

            $scope.uploadImageInfo = function(){

                $scope.bannerDTO.bannerType = $("#imageType").val();
                $scope.bannerDTO.forward = $("#imageUrl").val();
                $scope.bannerDTO.uri = $scope.bannerDTO.firstUrl.toString();
                $scope.bannerDTO.bannerRank = $("#sequence").val();
                if($scope.bannerDTO.bannerRank==""){
                        alert("存在必填项，没有填写，请填写后在提交");
                        return;
                 }else{
                    if($stateParams.bannerId!="0"){

                        if($scope.bannerDTO.uri==""){
                            $scope.bannerDTO.uri = $('#imageShow').attr('src');
                        }
                        $scope.bannerDTO.bannerId = $stateParams.bannerId;
                        if($scope.bannerDTO.uri!=""&&$scope.bannerDTO.uri!=undefined){
                            UpdateHomeBanner.save($scope.bannerDTO,function(data){
                                if(data.result==Global.SUCCESS){
                                     alert("更新成功！");
                                     $state.go("homePageEditor");
                                }else{
                                    alert("更新失败");
                                }
                            })
                        }else{
                            alert("图片为空或图片正在上传请等待.....")
                            return;
                        }
                    }else{
                        if($scope.bannerDTO.uri!=""){
                            AddHomeBanner.save({
                                bannerType:$scope.bannerDTO.bannerType,
                                bannerRank:$scope.bannerDTO.bannerRank,
                                uri:$scope.bannerDTO.uri.toString(),
                                forward:$scope.bannerDTO.forward

                            },function(data){
                                if(data.result==Global.SUCCESS){
                                     alert("新增成功！");
                                     $state.go("homePageEditor");
                                }else{
                                    alert("新增失败");
                                }
                            })
                        }else{
                            alert("图片为空或图片正在上传请等待.....")
                            return;
                        }

                    }
                 }

            };

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
                                $scope.bannerDTO.productDetail[picArr].splice(i,1);
                            }else{
                                $scope.bannerDTO.firstUrl=''
                            }

                            patter.removeChild(div[i]);
                            if($scope.bannerDTO.firstUrl==""){
                                $scope.hintPic1 ="";
                            }
                            remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");

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
                             if(id=="publicity"){
                                $scope.bannerDTO.firstUrl= data.responseData;
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
                        console.log($scope.bannerDTO.firstUrl);
                        delError(id)
                    })
                }
            };

            $scope.uploadingPic("publicity","publicityPic","firstUrl");

            //图片上传失败
            function delError (id){
                if(id=="publicity"){
                    remove("#publicityPic .falsePic","firstUrl","#publicityPic","#publicityPic div");
                }

            }

}]);