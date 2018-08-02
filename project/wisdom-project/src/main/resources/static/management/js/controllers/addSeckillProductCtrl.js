angular.module('controllers',[]).controller('addSeckillProductCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','$http','ManagementUtil','FindProductDetail','AddSecKillActivity',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,$http,ManagementUtil,FindProductDetail,AddSecKillActivity) {
             $scope.mess = false;

             $scope.secKillSessions = [{sessionSum:1,startTime:"",endTime:""}];

             $scope.sessions = [];
             $scope.SeckillActivityDTO={
                 activityName:"",
                 productId:"",
                 startTime:"",
                 endTime:"",
                 favorablePrice:"",
                 activityNum:"",
                 productNum:"",
                 isEnable:"",
                 sessionList:[],
                 productDTOList:[]
            };

            $scope.addSession = function(index){
                    $scope.secKillSessions.splice(index + 1, 0,
                        {sessionSum:''});

            }

            $scope.deleteSession = function($index){
                 $scope.secKillSessions.splice($index, 1);
            }

            $scope.backList = function(){
                $state.go("secKillProduct");
            }

            $scope.getProductDetail = function(){
                var productId = $("#productId").val();
                FindProductDetail.get({
                    productId:productId
                },function(data){
                    if(data.result==Global.SUCCESS){
                        $scope.SeckillActivityDTO.productDTOList = data.responseData;
                    }else{
                        alert("该项目编码查询不到商品！");
                    }

                })

            };

            $scope.delProduct = function(){

                $scope.SeckillActivityDTO.productDTOList =[];
            }

            $scope.submit = function(){

                $scope.SeckillActivityDTO.activityName = $("#activityName").val();
                $scope.SeckillActivityDTO.productId = $("#productId").val();
                $scope.SeckillActivityDTO.startTime = $("#startTime").val();
                $scope.SeckillActivityDTO.endTime = $("#endTime").val();
                $scope.SeckillActivityDTO.favorablePrice = $("#favorablePrice").val();
                $scope.SeckillActivityDTO.activityNum = $("#activityNum").val();
                $scope.SeckillActivityDTO.productNum = $("#productNum").val();
                $scope.SeckillActivityDTO.isEnable = $("#isEnable").val();

                $scope.sessions = $("#sec_kill_session_div").find(".sec_kill_session");
                for(var i=0; i< $scope.sessions.length;i++){
                    $scope.time = {startTime:"",startTimeString:"",endTimeString:"",endTime:""};
                    $scope.time.startTimeString = $($scope.sessions[i]).find(".startTime").val();
                    $scope.time.endTimeString = $($scope.sessions[i]).find(".endTime").val();
                    $scope.SeckillActivityDTO.sessionList[i]=$scope.time;
                }
                if($scope.SeckillActivityDTO.sessionList.length<1){
                    alert("场次最少添选一条！");
                    return;
                }else{
                     for(var i=0; i< $scope.SeckillActivityDTO.sessionList.length;i++){
                            if($scope.SeckillActivityDTO.sessionList[i].startTimeString==""||$scope.SeckillActivityDTO.sessionList[i].endTimeString==""){
                                    $scope.mess = true;
                                    return;
                             }
                        }

                }

                if($scope.SeckillActivityDTO.favorablePrice==""||$scope.SeckillActivityDTO.activityNum==""||$scope.SeckillActivityDTO.isEnable==""||$scope.SeckillActivityDTO.endTime == ""||$scope.SeckillActivityDTO.startTime == ""||$scope.SeckillActivityDTO.productId == ""||$scope.SeckillActivityDTO.productNum == ""|| $scope.SeckillActivityDTO.activityName == ""){
                     $scope.mess = true;
                     return;
                }
                if($scope.SeckillActivityDTO.favorablePrice!= undefined  && $scope.SeckillActivityDTO.activityNum!=undefined){
                            AddSecKillActivity.save($scope.SeckillActivityDTO,function(data){
                                    if(data.responseData == "success"){
                                        alert("新增成功!");
                                        $state.go("secKillProduct");
                                    }else{
                                        alert("新增失败，请稍后重试！")
                                    }
                                })
                }




            };

            //删除图片
            function remove (name,picArr,id,div){
               /* var img = document.querySelectorAll(name);
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

                }*/
            }

        }]);