/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('modifyProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state','ProjectInfo','UpdateProjectInfo','ImageBase64UploadToOSS','Global','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,ProjectInfo,UpdateProjectInfo,ImageBase64UploadToOSS,Global,$ionicLoading) {

            $rootScope.title = "修改项目";
            $scope.param={
                projectId:$stateParams.projectId,/*接受项目列表穿过的id*/

                secondary:"",
                timeLength:"",
            };

            $scope.selectionCategoryGO = function () {
                $state.go("selectionCategory",{projectId:$scope.param.projectId,url:'modifyProject'})
            }
            $scope.addProjectSeriesGo = function () {
                $state.go("addProjectSeries",{projectId:$scope.param.projectId,url:'modifyProject'})
            };
            $ionicLoading.show({
                content: 'Loading',
                animation: 'fade-in',
                showBackdrop: true,
                maxWidth: 200,
                showDelay: 0
            })
            ProjectInfo.get({id:$scope.param.projectId},function (data) {
                if(data.result == '0x00001'){
                    $ionicLoading.hide()
                    $scope.settingAddsome.extShopProjectInfoDTO=data.responseData;
                    if($rootScope.settingAddsome.extShopProjectInfoDTO.status=="1"){
                        $scope.param.status=false
                    }else {
                        $scope.param.status=true
                    }
                }
            });

            $scope.secondaryCard=function (type,timeLength) {
                if(type !='-1') {
                    $rootScope.settingAddsome.extShopProjectInfoDTO.cardType = type;
                    $rootScope.settingAddsome.extShopProjectInfoDTO.effectiveNumberMonth = timeLength;
                }

            };
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                if($rootScope.settingAddsome.extShopProjectInfoDTO.imageList.length>=6){
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
                            }else{
                                alert("请重新上传")
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
            };
            $scope.projectTheLength = function (type) {
                if(type==0){
                    if($rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration/1==30)return
                    $rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration=$rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration/1-30
                }else{
                    $rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration=$rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration/1+30
                }
            };
            $scope.numLimit=function (style,value) {
                $rootScope.settingAddsome.extShopProjectInfoDTO[style]=value.replace(/[^0-9.0-9]+/,'')
            }
            /*点击保存调取接口*/

            $scope.Preservation=function () {
                if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                    $rootScope.settingAddsome.extShopProjectInfoDTO.status="0"
                }else {
                    $rootScope.settingAddsome.extShopProjectInfoDTO.status ='1'
                }
                if($rootScope.settingAddsome.extShopProjectInfoDTO.cardType=='0'){
                    $rootScope.settingAddsome.extShopProjectInfoDTO.serviceTimes ='1'
                }
                if($rootScope.settingAddsome.extShopProjectInfoDTO.projectTypeOneName ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.projectTypeTwoName ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.projectName ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.projectDuration ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.oncePrice ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.discountPrice ==''||$rootScope.settingAddsome.extShopProjectInfoDTO.effectiveNumberMonth==''||$rootScope.settingAddsome.extShopProjectInfoDTO.serviceTimes==''){
                    alert("请检查信息")
                }
                UpdateProjectInfo.save($scope.settingAddsome.extShopProjectInfoDTO,function (data) {
                    if(data.result=="0x00001"){
                      $state.go("projectList")
                    }else{
                        alert("保存未成功")
                    }
                })
            }
        }]);