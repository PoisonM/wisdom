angular.module('controllers',[]).controller('newUserCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','Detail','Global','UpdateArchiveInfo','SaveArchiveInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,Detail,Global,UpdateArchiveInfo,SaveArchiveInfo) {
            $rootScope.title = "";
            //手机号码格式验证
            var phoneReg=/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9]|147|177)\d{8}$/;
            $scope.newUser={
                    sex:"女",
                    birthday:"",
                    constellation:"",
                    detail:"",
                    sysUserName:"",
                    phone:""

            };

            /*点击女*/
            $scope.female=function () {
                $scope.newUser.sex = '女'
            };
            /*点击男*/
            $scope.male=function () {
                $scope.newUser.sex = '男'
            };
            if($stateParams.id!=""){
                Detail.get({
                    id:$stateParams.id
                },function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.newUser = data.responseData
                    }
                });
            }
            /*更新保存*/
            $scope.preservation=function () {
                if($stateParams.id==""){
                    $scope.shopUserArchivesDTO=$scope.newUser;
                    /*新建保存接口*/
                    if(phoneReg.test( $scope.newUser.phone)||$scope.newUser.sysUserName!=""){
                        SaveArchiveInfo.save($scope.shopUserArchivesDTO,function (data) {
                            $state.go("partialFiles");
                        })
                    }else {
                      alert("请检查您的档案信息")
                    }
                }else {
                    $scope.userInformation=$scope.newUser;
                    /*修改档案更新保存*/
                    UpdateArchiveInfo.save($scope.userInformation,function (data) {
                        if(Global.SUCCESS=data.result){
                            $state.go("archives")
                        }
                    });
                }


            }


        }]);