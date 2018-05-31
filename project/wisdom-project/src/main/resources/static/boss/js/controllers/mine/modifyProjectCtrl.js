/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('modifyProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state','ProjectInfo','UpdateProjectInfo','ImageBase64UploadToOSS','Global',
        function ($scope,$rootScope,$stateParams,$state,ProjectInfo,UpdateProjectInfo,ImageBase64UploadToOSS,Global) {

            $rootScope.title = "修改项目";
            $scope.param={
                projectId:$stateParams.projectId,/*接受项目列表穿过的id*/
                cardBox:true,
                secondary:"",
                timeLength:"",
            };

            $scope.selectionCategoryGO = function () {
                $state.go("selectionCategory",{projectId:$scope.param.projectId,url:'modifyProject'})
            }
            $scope.addProjectSeriesGo = function () {
                $state.go("addProjectSeries",{projectId:$scope.param.projectId,url:'modifyProject'})
            }

            ProjectInfo.get({id:$scope.param.projectId},function (data) {
                $scope.settingAddsome.project=data.responseData;
                if($rootScope.settingAddsome.project.status=="1"){
                    $scope.param.status=false
                }else {
                    $scope.param.status=true
                }

                $scope.secondaryCard=function (type,timeLength) {
                    if(type !='-1') {
                        $rootScope.settingAddsome.project.cardType = type;
                        $scope.param.timeLength = timeLength
                    }

                };
            });
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                if($scope.modifyList.imageUrl.length>6){
                    alert("图片上传不能大于6张")
                    return
                }
                var file = files[0];
                if(window.FileReader) {
                    var fr = new FileReader();
                    fr.onloadend = function(e) {
                        $scope.thumb = e.target.result
                        ImageBase64UploadToOSS.save($scope.thumb,function (data) {
                            if(data.errorInfo==Global.SUCCESS&&data.responseData!=null){
                                $rootScope.settingAddsome.project.imageUrl.push(data.responseData)
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.delPic = function(index){
                $rootScope.imageUrl.splice(index,1)
            }
            /*点击保存调取接口*/

            $scope.Preservation=function () {
                if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                    $rootScope.settingAddsome.project.status="0"
                }else {
                    $rootScope.settingAddsome.project.status ='1'
                }
                if($rootScope.settingAddsome.project.projectTypeOneName ==''||$rootScope.settingAddsome.project.projectTypeTwoName ==''||$rootScope.settingAddsome.project.projectName ==''||$rootScope.settingAddsome.project.projectDuration ==''||$rootScope.settingAddsome.project.oncePrice ==''||$rootScope.settingAddsome.project.discountPrice ==''){
                    alert("请检查信息")
                }
                UpdateProjectInfo.save($scope.settingAddsome.project,function (data) {
                    if(data.result=="0x00001"){
                      $state.go("projectList")
                    }
                })
            }
        }]);