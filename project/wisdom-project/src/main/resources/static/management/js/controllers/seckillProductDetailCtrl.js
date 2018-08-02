angular.module('controllers',[]).controller('seckillProductDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','$http','ManagementUtil','FindProductDetail','GetSecKillActivityDetail',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,$http,ManagementUtil,FindProductDetail,GetSecKillActivityDetail) {
             $scope.mess = false;

             $scope.secKillSessions = [];

             $scope.sessions = [];

             $scope.SeckillActivityDTO={
                 activityName:"",
                 productId:"",
                 startTime:"",
                 startTimeString:"",
                 endTime:"",
                 endTimeString:"",
                 favorablePrice:'',
                 activityNum:'',
                 productNum:"",
                 isEnable:"",
                 sessionList:[],
                 productDTOList:[]
            };




            $scope.getSecKillActivity = function(){
                var id = $stateParams.id;
                GetSecKillActivityDetail.get({id:id},
                function(data){
                     if(data.result == Global.SUCCESS){
                         $scope.SeckillActivityDTO = data.responseData;
                         $scope.secKillSessions =  $scope.SeckillActivityDTO.sessionList;
                         $("#productId").val($scope.SeckillActivityDTO.productId);
                         $scope.getProductDetail();
                         $("#activityName").val($scope.SeckillActivityDTO.activityName);
                         $("#startTime").val($scope.SeckillActivityDTO.startTimeString);
                         $("#endTime").val($scope.SeckillActivityDTO.endTimeString);
                         $("#isEnable").val($scope.SeckillActivityDTO.isEnable);
                         $("#productNum").val($scope.SeckillActivityDTO.productNum);
                         $("#favorablePrice").val($scope.SeckillActivityDTO.favorablePrice);
                         $("#activityNum").val($scope.SeckillActivityDTO.activityNum);
                     }
                })
            };

            $scope.getSecKillActivity();

            $scope.backList = function(){
                $state.go("secKillProduct");
            };

            $scope.getProductDetail = function(){
                var productId = $("#productId").val();
                FindProductDetail.get({
                    productId:productId
                },function(data){
                    $scope.SeckillActivityDTO.productDTOList = data.responseData;

                })
                $("#favorablePrice").val($scope.SeckillActivityDTO.favorablePrice);
                $("#activityNum").val($scope.SeckillActivityDTO.activityNum);
            };


         /*   $scope.submit = function(){

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
                    $scope.time = {startTime:"",endTime:""};
                    $scope.time.startTimeString = $($scope.sessions[i]).find(".startTime").val();
                    $scope.time.endTimeString = $($scope.sessions[i]).find(".endTime").val();
                    $scope.SeckillActivityDTO.sessionList[i]=$scope.time;
                }
                if($scope.SeckillActivityDTO.sessionList<1){
                    alert("场次最少添选一条！");
                    return;
                }
           *//*     if($scope.SeckillActivityDTO.productNum||$scope.SeckillActivityDTO.activityName == ""||$scope.SeckillActivityDTO.productId==""||$scope.SeckillActivityDTO.startTime==""||$scope.SeckillActivityDTO.endTime==""||$scope.SeckillActivityDTO.favorablePrice==""||$scope.SeckillActivityDTO.activityNum==""||$scope.SeckillActivityDTO.isEnable==""){
                     $scope.mess = true;
                     return;
                }*//*

                AddSecKillActivity.save($scope.SeckillActivityDTO,function(data){
                    if(data.responseData == "success"){
                        alert("新增成功!");
                        $state.go("secKillProduct");
                    }else{
                        alert("新增失败，请稍后重试！")
                    }
                })


            };*/

        }]);