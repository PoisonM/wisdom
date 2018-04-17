function addCustomersCtrl ($scope,ngDialog) {
    $scope.uploadPic = function(){
         $scope.uploadingPic("userPic");
    }

    $scope.uploadingPic = function(id,big,type){
        var input = document.getElementById('userPic');
        var result,div;
        if(typeof FileReader==='undefined'){
            result.innerHTML = "抱歉，你的浏览器不支持 FileReader";
            input.setAttribute('disabled','disabled');
        }else{
            input.addEventListener('change',readFile,false);
        }
        function readFile(){
            for(var i=0;i<this.files.length;i++){
                if (!input['value'].match(/.jpg|.gif|.png|.bmp/i)){
                    return alert("上传的图片格式不正确，请重新选择")
                }

            }
            var MultipartFile = new FormData();
            for(var i=0;i<this.files.length;i++){
                var reader = new FileReader();
                reader.readAsDataURL(this.files[i]);
                MultipartFile.append("listFile",this.files[i]);

            }
            var url = "/system/file/imageUploadToOSS";
           console.log(this.files)
 /*           $http.post(url,MultipartFile,{
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }).success(function(data) {
                if(data.errorInfo == Global.SUCCESS){

                }else{
                    alert("上传图片失败");
                    delError(id)
                }
            }).error(function(){
                alert("上传图片失败");
                delError(id)
            })*/
        }
    };

}

