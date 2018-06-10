/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveProjectInfo','ImageBase64UploadToOSS','Global',
        function ($scope,$rootScope,$stateParams,$state,SaveProjectInfo,ImageBase64UploadToOSS,Global) {

            $rootScope.title = "添加项目";
            $scope.param={
                selFlag:true
            }
            $scope.cardBox=false;/*点击次卡 点击时效卡显示的卡项*/
            $rootScope.settingAddsome.extShopProjectInfoDTO={
                    functionIntr:"",/*功能介绍*/
                    oncePrice:"",/*单次价格*/
                    discountPrice:"",/*办卡价格*/
                    projectTypeOneId:'',/*类型id*/
                    projectTypeOneName:"",/*类型名称*/
                    projectTypeTwoId:"",/*系列id*/
                    projectTypeTwoName:"",/*系列名称*/
                    serviceTimes:"",/*包含次数*/
                    status:"0",/*不启动*/
                    visitDateTime:"",/*回访次数*/
                    projectName:"",/*项目名称*/
                    projectDuration:"",/*时长*/
                    imageList:[],/*图片*/
                    cardType:"0"/*卡的类型*/,
                    effectiveNumberMonth:12/*有效期*/
                }


            if($rootScope.settingAddsome.extShopProjectInfoDTO.status =='0'){
                $scope.selFlag = true
            }else{
                $scope.selFlag = false
            }
            /*选择分类*/
            $scope.selectionCategoryGO=function () {
                $state.go("selectionCategory",{url:"addProject"});
            };
            /*选择系列*/
            $scope.projectSeries=function () {
                if($rootScope.settingAddsome.extShopProjectInfoDTO.projectTypeOneId==""){
                    alert("请先选择类别")
                    return
                }
                $state.go("addProjectSeries",{url:"addProject"});
            };
            /*点击时效卡与次卡*/
            $scope.secondaryCard=function (type,timeLength) {
                if(type !='-1'){
                    $rootScope.settingAddsome.extShopProjectInfoDTO.cardType = type;
                    if(type == '0'){
                        $scope.cardBox=false;
                    }else{
                        $scope.cardBox=true;
                        $rootScope.settingAddsome.extShopProjectInfoDTO.effectiveNumberMonth = timeLength
                    }
                }else{
                    $rootScope.settingAddsome.extShopProjectInfoDTO.cardType = '4';
                }


            };
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                if( $rootScope.settingAddsome.extShopProjectInfoDTO.imageList.length>6){
                    alert("图片上传不能大于6张")
                    return
                }
                var file = files[0];
                if(window.FileReader) {
                    var fr = new FileReader();
                    fr.onloadend = function(e) {
                        $scope.thumb = e.target.result
                        ImageBase64UploadToOSS.save($scope.thumb,function (data) {
                            if(data.result==Global.SUCCESS&&data.responseData!=null){
                                $rootScope.settingAddsome.extShopProjectInfoDTO.imageList.push(data.responseData)
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.delPic = function(index){
                $rootScope.settingAddsome.extShopProjectInfoDTO.imageList.splice(index,1)
            }

         /*添加保存*/
            $scope.Preservation=function () {
                if($scope.param.selFlag ==true){
                    $rootScope.settingAddsome.extShopProjectInfoDTO.status = '0';
                }else{
                    $rootScope.settingAddsome.extShopProjectInfoDTO.status = '1';
                }
                if($rootScope.settingAddsome.extShopProjectInfoDTO.cardType=='0'){
                    $rootScope.settingAddsome.extShopProjectInfoDTO.serviceTimes ='1'
                }
               if($rootScope.settingAddsome.extShopProjectInfoDTO.projectTypeTwoName ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.projectTypeOneName ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.projectName ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.oncePrice ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.discountPrice ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.serviceTimes ==""||$rootScope.settingAddsome.extShopProjectInfoDTO.effectiveNumberMonth==''){
                   alert("填入的数据不完整")
               }

                   SaveProjectInfo.save($rootScope.settingAddsome.extShopProjectInfoDTO,function (data) {
                       $state.go("basicSetting")
                   })
               
            }
        }]);