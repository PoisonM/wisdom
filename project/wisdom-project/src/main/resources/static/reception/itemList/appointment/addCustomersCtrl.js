function addCustomersCtrl ($scope,ngDialog) {
    var pattern = /^1[34578]\d{9}$/;
    /*添加顾客*/
    $scope.addCustomersCtrl = function(){
        ngDialog.open({
            template: 'addCustomers',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function(type) {
                    if(type==1){
                        console.log($scope.param.addCustomersObject)
                        if($scope.param.addCustomersObject.userPhone == ""){
                            $scope.param.addCustomersObject.userPhone='请填写手机号';
                            return;
                        }
                        if(pattern.test($scope.param.addCustomersObject.userPhone)==false){
                            $scope.param.addCustomersObject.userPhone='请填写正确的手机号';
                            return
                        }
                        var ShopUserArchivesDTO = {
                            sex:$scope.param.addCustomersObject.sex,
                            picSrc:$scope.param.addCustomersObject.picSrc,
                            userName:$scope.param.addCustomersObject.userName,
                            userPhone:$scope.param.addCustomersObject.userPhone,
                        }/*添加客户参数 post*/
                    }
                    $scope.closeThisDialog();

                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });

    };
    /*上传图片*/
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
    $scope.selectSex = function(sex){
        $scope.param.addCustomersObject.sex=sex
    }

}

