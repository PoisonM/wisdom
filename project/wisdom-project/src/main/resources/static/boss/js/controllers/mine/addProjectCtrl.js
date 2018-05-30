/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveProjectInfo',
        function ($scope,$rootScope,$stateParams,$state,SaveProjectInfo) {

            $rootScope.title = "添加项目";
            $scope.selFlag ='';
            $scope.cardBox=false;/*点击次卡 点击时效卡显示的卡项*/
            $scope.extShopProjectInfoDTO={
                    functionIntr:"",/*功能介绍*/
                    oncePrice:"",/*单次价格*/
                    discountPrice:"",/*办卡价格*/
                    projectTypeOneId:$stateParams.typeId,/*类型id*/
                    projectTypeOneName:$stateParams.name,/*类型名称*/
                    projectTypeTwoId:$stateParams.seriesId,/*系列id*/
                    projectTypeTwoName:$stateParams.seriesName,/*系列名称*/
                    serviceTimes:"",/*包含次数*/
                    status:"0",/*不启动*/
                    visitDateTime:"",/*回访次数*/
                    projectName:"",/*项目名称*/
                    projectDuration:"",/*时长*/
                    imageList:[],/*图片*/
                    cardType:"0"/*卡的类型*/,
                    timeLength:12/*有效期*/
                }

            $scope.style = function(routeStyle,dataStyle){
                if($stateParams[routeStyle] !=""){
                    $scope.extShopProjectInfoDTO =JSON.parse(localStorage.getItem('param'));
                    $scope.extShopProjectInfoDTO[dataStyle] =$stateParams[routeStyle];
                }
            };
            $scope.style('typeId','projectTypeOneId');
            $scope.style('name','projectTypeOneName');
            $scope.style('seriesId','projectTypeTwoId');
            $scope.style('seriesName','projectTypeTwoName');

            if($scope.extShopProjectInfoDTO.status =='0'){
                $scope.selFlag = true
            }else{
                $scope.selFlag = false
            }
            /*选择分类*/
            $scope.selectionCategoryGO=function () {
                $state.go("selectionCategory",{add:"addProject"});
                localStorage.setItem('param',JSON.stringify($scope.extShopProjectInfoDTO));
            };
            /*选择系列*/
            $scope.projectSeries=function () {
                $state.go("addProjectSeries",{typeId:$stateParams.typeId,add:"addProject"});
                localStorage.setItem('param',JSON.stringify($scope.extShopProjectInfoDTO));
            };
            /*点击时效卡与次卡*/
            $scope.secondaryCard=function (type,timeLength) {
                if(type !='-1'){
                    $scope.extShopProjectInfoDTO.cardType = type;
                    if(type == '0'){
                        $scope.cardBox=false;
                    }else{
                        $scope.cardBox=true;
                        $scope.extShopProjectInfoDTO.timeLength = timeLength
                    }
                }else{
                    $scope.extShopProjectInfoDTO.cardType = '4';
                    $scope.cardBox=true;
                }
                console.log($scope.extShopProjectInfoDTO.cardType )

            };
         /*添加保存*/
            $scope.Preservation=function () {
                if($scope.selFlag ==true){
                    $scope.extShopProjectInfoDTO.status = '0';
                }else{
                    $scope.extShopProjectInfoDTO.status = '1';
                }
           /*    if($scope.extShopProjectInfoDTO.projectTypeTwoName ==""||$scope.extShopProjectInfoDTO.projectTypeOneName ==""||$scope.extShopProjectInfoDTO.projectName ==""||$scope.extShopProjectInfoDTO.projectDuration ==""||$scope.extShopProjectInfoDTO.oncePrice ==""||$scope.extShopProjectInfoDTO.discountPrice ==""||$scope.extShopProjectInfoDTO.serviceTimes ==""){
                   alert("填入的数据不完整")
               }*/
                localStorage.removeItem('param');

                   SaveProjectInfo.save($scope.extShopProjectInfoDTO,function (data) {
                       $state.go("basicSetting")
                   })
               
            }
        }]);