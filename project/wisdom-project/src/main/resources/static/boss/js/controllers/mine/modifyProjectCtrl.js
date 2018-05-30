/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('modifyProjectCtrl',
    ['$scope','$rootScope','$stateParams','$state','ProjectInfo','UpdateProjectInfo','ImageBase64UploadToOSS','Global',
        function ($scope,$rootScope,$stateParams,$state,ProjectInfo,UpdateProjectInfo,ImageBase64UploadToOSS,Global) {

            $rootScope.title = "修改项目";
            $scope.param={
                id:$stateParams.projectId,/*接受项目列表穿过的id*/
                cardBox:true,
                secondary:"",
                timeLength:"",
                status:$stateParams.status
            };
            if($stateParams.status=="1"){/**/
                $scope.param.status=false
            }else {
                $scope.param.status=true
            }
            $scope.selectionCategoryGO=function (projectId) {
                $state.go("selectionCategory",{mod:"modifyProject",projectId:projectId});
                localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));
            };
            $scope.projectSeries=function (projectId) {
                $state.go("addProjectSeries",{typeId:$stateParams.typeId,mod:"modifyProject",projectId:projectId});
                localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));
            };

            ProjectInfo.get({id:$scope.param.id},function (data) {
                $scope.modifyList=data.responseData;
                if($stateParams.name !=''){
                    $scope.modifyList =JSON.parse(localStorage.getItem('modifyList'));
                    $scope.modifyList.projectTypeOneName = $stateParams.name;
                   /* localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));*/

                }
                
                if($stateParams.seriesName !=''){
                    $scope.modifyList =JSON.parse(localStorage.getItem('modifyList'));
                    $scope.modifyList.projectTypeTwoName = $stateParams.seriesName;
                   /* localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));*/
                }
                if($stateParams.typeId !=''){
                    $scope.modifyList =JSON.parse(localStorage.getItem('modifyList'));
                    $scope.modifyList.projectTypeOneId = $stateParams.typeId;
                  /*  localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));*/
                }
                if($stateParams.seriesId !=''){
                    $scope.modifyList =JSON.parse(localStorage.getItem('modifyList'));
                    $scope.modifyList.projectTypeTwoId = $stateParams.seriesId;
                    localStorage.setItem('modifyList',JSON.stringify($scope.modifyList));
                }

                $scope.secondaryCard=function (type,timeLength) {
                    if(type !='-1') {
                        $scope.modifyList.cardType = type;
                        $scope.param.timeLength = timeLength
                    }
                    console.log($scope.modifyList.cardType)
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
                                $scope.modifyList.imageUrl.push(data.responseData)
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.delPic = function(index){
                $scope.modifyList.imageUrl.splice(index,1)
            }
            /*点击保存调取接口*/

            $scope.Preservation=function () {
                if($scope.param.status==true){/*如果为true显示不启动，反之启动*/
                    $scope.param.status="1"
                }else {
                    $scope.param.status="0"
                }
                /*console.log($scope.modifyList);*/
                UpdateProjectInfo.save($scope.modifyList,function (data) {
                    if(data.result=="0x00001"){
                        localStorage.removeItem('modifyProduct');
                      $state.go("projectList")
                    }
                })
            }
        }]);