angular.module('controllers',[]).controller('uploadingParticularsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','FindProductById','ImageUploadToOSS','$http','UpdateProductByParameters','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,FindProductById,ImageUploadToOSS,$http,UpdateProductByParameters,ManagementUtil) {
            var ptoductType = $stateParams.type;
            $scope.flag = false;
            $scope.flagDel = false;
            var postage = document.querySelector("#postage");
            var serve = document.querySelectorAll(".serve input[type='checkbox']");
            var serveText =  document.querySelectorAll(".serve p label");
            var serveTextArr = [];
            $scope.mum = true;
            for(var i=0;i<serveText.length;i++){
                serveTextArr.push(serveText[i].innerHTML)
            }
            $scope.uploadingPar='';
 /*展示*/
            FindProductById.get({
                productId:$stateParams.productId
            },function(data){
                console.log(data)
                ManagementUtil.checkResponseData(data,"");
                if(data.errorInfo == Global.SUCCESS){
                    $scope.uploadingPar = data.responseData;
                   if(data.responseData.productDetail){
                       pic(data,"#particulars_viewPic","detailPic");
                       pic(data,"#list_viewPic","listPic");
                       pic(data,"#publicityPic","firstUrl");
                       remove("#list_viewPic ","listPic");
                       remove("#particulars_viewPic ","detailPic");
                       remove("#publicityPic ","firstUrl");
                       $scope.init(".states option","status",0,"");
                       $scope.init("#address option","productDetail",1,"senderAddress");
                       if($scope.uploadingPar.productDetail.services!=undefined){
                           for(var j=0;j<$scope.uploadingPar.productDetail.services.length;j++){
                               var num = serveTextArr.indexOf($scope.uploadingPar.productDetail.services[j]);
                               if(num>=0)
                                   serve[num].setAttribute("checked","checked")
                           }
                       }

                       if($scope.uploadingPar.productDetail.tag[0]=="包邮"){
                           postage.setAttribute("checked","checked")
                       }
                       if($scope.uploadingPar.productDetail.listPic.length>=1){
                           $scope.hintPic1 ="images/true.png"
                       }
                       if($scope.uploadingPar.productDetail.detailPic.length>=1){
                           $scope.hintPic2 ="images/true.png"
                       }
                       if($scope.uploadingPar.firstUrl.length!=""){
                           $scope.hintPic3 ="images/true.png"
                       }

                   }
                    $scope.mum = false;
                }
            });
 /*添加图片*/
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
 /*删除图片*/
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
                            patter.removeChild(div[i])
                            remove("#publicityPic","firstUrl");
                            remove("#particulars_viewPic","detailPic");
                            remove("#list_viewPic ","listPic");
                            if($scope.uploadingPar.firstUrl==""){
                                $scope.hintPic3 ="";
                            }
                            if($scope.uploadingPar.productDetail.listPic.length<1){
                                $scope.hintPic1 ="";
                            }
                            if($scope.uploadingPar.productDetail.detailPic.length<1){
                                $scope.hintPic2 ="";
                            }


                        }
                    }
                }


            }


 /*下拉菜单的默认值*/
            $scope.init = function(options,property,type,address){
                var addresss = document.querySelectorAll(options);
                if(type==0){
                    var address = $scope.uploadingPar[property]
                }else{
                    var address = $scope.uploadingPar[property][address]
                }
                for(var i=0;i<addresss.length;i++){
                    if(addresss[i].value == address){
                        addresss[i].setAttribute("selected","selected");
                    }
                }
            };
/*添加型号*/

            $scope.type=function(){
                $scope.flag = !$scope.flag ;
            };
            $scope.typeTrue = function(){
                var col = document.querySelector(".col").value;
                $scope.flag = false;
                if(col=='')return;
                $scope.uploadingPar.productDetail.spec.push(col);
                document.querySelector(".col").value=""
            };
/*删除型号*/
            $scope.index = -1;
            $scope.delType=function(name,index){
                $scope.index = index;
                $scope.colName=name;
                $scope.flagDel =!$scope.flagDel;
            };
            $scope.typeDelTrue = function(index){
                if(index==0){
                    if($scope.index>-1){
                        $scope.uploadingPar.productDetail.spec.splice($scope.index,1)
                    }
                    $scope.flagDel=false;
                }else{
                    if($scope.index>-1){
                        $scope.uploadingPar.productDetail.spec.splice($scope.index,1,$scope.colName)
                    }
                    $scope.flagDel=false;

                }

            };
            $scope.bgAll=function(){
                $scope.flag = false;
                $scope.flagDel = false
            };
  /*服务*/

 /*上传图片*/
            $scope.hintPic1 ="";
            $scope.hintPic2 ="";
            $scope.hintPic3 ="";
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
                    if(id=="list_view"){
                        $scope.hintPic1="";
                    }else if(id=="particulars_view"){
                        $scope.hintPic2="";
                    }else if(id=="publicity"){
                        $scope.hintPic3="";

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
                    if(ptoductType =="offline"){
                        ptoductType = "offlineProduct/"
                    }else if(ptoductType =="training"){
                        ptoductType = "trainingProduct/"
                    }
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
                           if(id=="list_view"){
                               $scope.hintPic1="images/true.png";
                               $scope.uploadingPar.productDetail.listPic= $scope.uploadingPar.productDetail.listPic.concat(data.responseData);
                               remove("#list_viewPic ","listPic");
                           }else if(id=="particulars_view"){
                               $scope.hintPic2="images/true.png";
                               $scope.uploadingPar.productDetail.detailPic=$scope.uploadingPar.productDetail.detailPic.concat(data.responseData)
                               remove("#particulars_viewPic","detailPic");
                           }else if(id=="publicity"){
                               $scope.uploadingPar.firstUrl= data.responseData[0];
                               $scope.hintPic3="images/true.png";
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
            $scope.uploadingPic("list_view","list_viewPic");
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

            /*保存*/
            $scope.mess = false;
            $scope.save = function(){
                    if($scope.hintPic1 ==""||$scope.hintPic2 ==""||$scope.hintPic3==""){
                        $scope.mess = true;
                        return;
                    }
                    var productName = document.querySelector(".name").value;
                    var brand = document.querySelector(".brand").value;
                    var secondType = document.querySelector(".types").value;
                    var status = document.querySelector(".states").value;
                    var price = document.querySelector(".price").value;
                    var description = document.querySelector("#introduction").value;
                    var typelis = document.querySelectorAll(".typelis span");
                    var addresss = document.querySelector("#address").value;
                    /*var productAdd = document.querySelector("#address1").value;*/
                    var listPic = document.querySelectorAll("#list_viewPic .as img")
                    var detailPic = document.querySelectorAll("#particulars_viewPic .as img")
                    /*型号*/                var typelisText = [];
                    /*服务*/                var services=[];
                    /*列表图*/               var listPicArr=[];
                    /*详情图*/                var detailPicArr=[];
                    /*包邮*/                 var tagArr=[];
                    /*列表图*/
                    for(var i=0;i<listPic.length;i++){
                        listPicArr.push(listPic[i].src)
                    }
                    for(var i=0;i<detailPic.length;i++){
                        detailPicArr.push(detailPic[i].src)
                    }
                    /*类型*/
                    for(var i=0;i<typelis.length;i++){
                        typelisText.push(typelis[i].innerHTML)
                    }
                    /*服务*/
                    for(var j=0;j<serve.length;j++){
                        if(serve[j].checked == true){
                            services.push(serveText[j].innerHTML)
                        }
                    }
                    /*包邮*/
                    if(postage.checked == true){
                        tagArr.push("包邮")
                    }else{
                        var tag= "不包邮";
                        tagArr.push(tag)
                    }
                    if(productName==""||brand==""||secondType==""||status==""||price==""||addresss==""||typelisText.length<=0||listPicArr.length<=0||detailPicArr.length<=0||$scope.uploadingPar.firstUrl==""||typelisText.length<=0||description ==""){
                        $scope.mess=true;
                        return
                    }
                    for(var i=0;i<typelis.length;i++){
                        if(typelis[i].innerHTML == ""){
                            $scope.mess=true;
                            return
                        }
                    }
                    /*传给服务器的值*/
                    var ProductDTO = {
                        id:$stateParams.id,
                        productName:productName,
                        productId:$stateParams.productId,
                        brand:brand,
                        type:"offline",
                        secondType:secondType,
                        description:description,
                        firstUrl:$scope.uploadingPar.firstUrl,
                        price:price,
                        status:status,
                        productDetail:{
                            createDate:$stateParams.createDate,
                            tag:tagArr,
                            services:services,
                            spec:typelisText,
                            detailPic:$scope.uploadingPar.productDetail.detailPic,
                            listPic:$scope.uploadingPar.productDetail.listPic,
                            senderAddress:addresss,
                            productId:$stateParams.productId
                        }
                    };
                    UpdateProductByParameters.save(ProductDTO,function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.result == Global.SUCCESS){
                            $scope.mess=false;
                            $state.go("home")
                        }
                    })
                }
    }])