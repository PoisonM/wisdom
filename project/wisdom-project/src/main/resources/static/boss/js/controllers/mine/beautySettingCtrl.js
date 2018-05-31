/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('beautySettingCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetBossShopInfo','Global','UpdateShopInfo','ImageBase64UploadToOSS',
        function ($scope,$rootScope,$stateParams,$state,GetBossShopInfo,Global,UpdateShopInfo,ImageBase64UploadToOSS) {

            $rootScope.title = "美容院设置";
            $scope.param = {
                flag:false
            }
            $scope.showPic = function () {
                $scope.param.flag =!$scope.param.flag
            }
            GetBossShopInfo.get({},function (data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                     $scope.beautySetting = data.responseData
                }
            })
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files,style) {

                var file = files[0];
                if(window.FileReader) {
                    var fr = new FileReader();
                    fr.onloadend = function(e) {
                        $scope.thumb = e.target.result
                        ImageBase64UploadToOSS.save($scope.thumb,function (data) {
                            if(data.errorInfo==Global.SUCCESS&&data.responseData!=null){
                                $scope.beautySetting[style]=data.responseData
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.save = function () {
                if($scope.beautySetting.name ==''||$scope.beautySetting.phone ==''||$scope.beautySetting.city ==''||$scope.beautySetting.province ==''||$scope.beautySetting.address ==''){
                    alert("请检查信息")
                    return
                }
                UpdateShopInfo.save($scope.beautySetting,function(data){
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $state.go("basicSetting")
                    }
                })
            }


        }]);