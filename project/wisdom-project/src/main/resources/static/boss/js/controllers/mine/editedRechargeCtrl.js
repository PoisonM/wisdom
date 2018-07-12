/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('editedRechargeCtrl',
    ['$scope','$rootScope','$stateParams','$state','RechargeCardDetail','Global','$http','GetGoodsUseScope','UpdateRechargeCardInfo','ImageBase64UploadToOSS','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,RechargeCardDetail,Global,$http,GetGoodsUseScope,UpdateRechargeCardInfo,ImageBase64UploadToOSS,$ionicLoading) {
            $rootScope.title = "编辑充值卡";
            $scope.param={
                appearArr:[false,false,false],
                status:true,
                id:$stateParams.id
            };
                $ionicLoading.show({
                    content: 'Loading',
                    animation: 'fade-in',
                    showBackdrop: true,
                    maxWidth: 200,
                    showDelay: 0
                })
                RechargeCardDetail.get({
                    id: $stateParams.id
                }, function (data) {
                    if (data.result == Global.SUCCESS && data.responseData != null) {
                        $rootScope.settingAddsome.editedRecharge = data.responseData;
                        if ($rootScope.settingAddsome.editedRecharge.status == '0') {
                            $scope.param.status = true
                        } else {
                            $scope.param.status = false
                        }
                        GetGoodsUseScope.get({
                            shopRechargeCardId: $stateParams.id
                        }, function (data) {
                            if (data.responseData.timesList != undefined || data.responseData.periodList != undefined || data.responseData.productList != undefined) {
                                $rootScope.settingAddsome.editedRecharge.timesList = data.responseData.timesList;
                                $rootScope.settingAddsome.editedRecharge.periodList = data.responseData.periodList;
                                $rootScope.settingAddsome.editedRecharge.productList = data.responseData.productList;
                            } else {
                                $rootScope.settingAddsome.editedRecharge.timesList = new Array;
                                $rootScope.settingAddsome.editedRecharge.periodList = new Array;
                                $rootScope.settingAddsome.editedRecharge.productList = new Array;
                            }
                            for(var i=0;i<$scope.param.appearArr.length;i++){
                                $scope.param.appearArr[i]=true
                            }
                           /* if ($rootScope.settingAddsome.editedRecharge.productList.length > 0 || ($rootScope.settingAddsome.editedRecharge.productList != '' && $rootScope.settingAddsome.editedRecharge.productList != null)) {
                                $scope.param.appearArr[2] = true
                            }
                            if ($rootScope.settingAddsome.editedRecharge.periodList.length > 0 || ($rootScope.settingAddsome.editedRecharge.periodDiscount != '' && $rootScope.settingAddsome.editedRecharge.periodDiscount != null)) {
                                $scope.param.appearArr[1] = true
                            }
                            if ($rootScope.settingAddsome.editedRecharge.timesList.length > 0 || ($rootScope.settingAddsome.editedRecharge.timeDiscount != "" && $rootScope.settingAddsome.editedRecharge.timeDiscount != null)) {
                                $scope.param.appearArr[0] = true
                            }*/

                            $ionicLoading.hide()


                        })

                    }
                });


            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                if($rootScope.settingAddsome.editedRecharge.imageList.length>=6){
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
                                $rootScope.settingAddsome.editedRecharge.imageList.push(data.responseData)
                            }
                        })
                    };
                    fr.readAsDataURL(file);
                }else {
                    alert("浏览器不支持")
                }
            };
            $scope.delPic = function(index){
                $rootScope.settingAddsome.editedRecharge.imageList.splice(index,1)
            }
            $scope. appear=function (index) {
                $scope.param.appearArr[index ] =!$scope.param.appearArr[index ]
            }

            $scope.discount = function(style){
                $rootScope.settingAddsome.editedRecharge[style] = $rootScope.settingAddsome.editedRecharge[style].replace(/[^\d.]/g,'')

            }
            $scope.discounts = function (style) {
                if($rootScope.settingAddsome.editedRecharge[style]>1||$rootScope.settingAddsome.editedRecharge[style]<0.1){
                    $rootScope.settingAddsome.editedRecharge[style] =1
                }
            };
            $scope.numLimit=function (style,value) {
                $rootScope.settingAddsome.editedRecharge[style]=value.replace(/[^0-9.0-9]+/,'')
            }
            $scope.save = function () {
                if($rootScope.settingAddsome.editedRecharge.name==""||
                    $rootScope.settingAddsome.editedRecharge.amount==""||
                    ($rootScope.settingAddsome.editedRecharge.timesList.length<=0)||
                    ($rootScope.settingAddsome.editedRecharge.periodList.length<=0)||
                    ($rootScope.settingAddsome.editedRecharge.productList.length<=0)||
                    ($rootScope.settingAddsome.editedRecharge.productDiscount=='')||
                    ($rootScope.settingAddsome.editedRecharge.periodDiscount=='')||
                    ($rootScope.settingAddsome.editedRecharge.timesList=='')){
                    alert("信息不完全");
                    return
                }
             if ($scope.param.status == true) {
                    $rootScope.settingAddsome.editedRecharge.status = '0'
                } else {
                    $rootScope.settingAddsome.editedRecharge.status = '1'
                }

                UpdateRechargeCardInfo.save($rootScope.settingAddsome.editedRecharge,function (data) {
                    if(data.result==Global.SUCCESS){
                        $state.go("basicSetting")
                    }else{
                        alert("保存未成功")
                    }
                    
                })
            }




        }])