/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addrechargeCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveRechargeCardInfo','Global','ImageBase64UploadToOSS',
        function ($scope,$rootScope,$stateParams,$state,SaveRechargeCardInfo,Global,ImageBase64UploadToOSS) {

            $rootScope.title = "添加充值卡";
            $scope.param={
                appearArr:[false,false,false],
                status:true
            };
            $rootScope.settingAddsome.editedRecharge={
                name:'',
                amount:"",
                imageList:[],
                introduce:'',
                status:'0',
                timesList:[],/*次卡数组id*/
                periodList:[],/*疗程卡数组id*/
                productList:[]/*产品数组id*/,
                timeDiscount:'',/*单次折扣*/
                periodDiscount:'',/*疗程卡折扣*/
                productDiscount:''/*产品折扣*/
            }
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
                if($rootScope.settingAddsome.editedRecharge[style]!=''){
                    if($rootScope.settingAddsome.editedRecharge[style]>1||$rootScope.settingAddsome.editedRecharge[style]<0.1){
                        $rootScope.settingAddsome.editedRecharge[style] ='';
                        alert('折扣价格范围为0-1')
                    }
                }

            };
            $scope.numLimit=function (style,value) {
                $rootScope.settingAddsome.editedRecharge[style]=value.replace(/[^0-9.0-9]+/,'')
            }
            $scope.save = function () {
                if($scope.param.status == true){
                    $rootScope.settingAddsome.editedRecharge.status = '0'
                }else{
                    $rootScope.settingAddsome.editedRecharge.status = '1'
                }
                if($rootScope.settingAddsome.editedRecharge.name==""){
                    alert("请填写充值卡名称")
                    return
                }
                if ($rootScope.settingAddsome.editedRecharge.amount == "") {
                    alert("请填写充值面额")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.timeDiscount==''&&$rootScope.settingAddsome.editedRecharge.periodDiscount==''&&$rootScope.settingAddsome.editedRecharge.productDiscount==''){
                    alert("请至少选择一个折扣")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.timeDiscount!=''&&$rootScope.settingAddsome.editedRecharge.timesList.length<=0){
                    alert("请填写单次卡适用范围")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.timeDiscount==''&&$rootScope.settingAddsome.editedRecharge.timesList.length>0){
                    alert("请填写单次卡折扣")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.periodDiscount!=''&&$rootScope.settingAddsome.editedRecharge.periodList.length<=0){
                    alert("请填写疗程卡适用范围")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.periodDiscount==''&&$rootScope.settingAddsome.editedRecharge.periodList.length>0){
                    alert("请填写疗程卡折扣")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.productDiscount!=''&&$rootScope.settingAddsome.editedRecharge.productList.length<=0){
                    alert("请填写产品适用范围")
                    return
                }
                if($rootScope.settingAddsome.editedRecharge.productDiscount==''&&$rootScope.settingAddsome.editedRecharge.productList.length>0){
                    alert("请填写产品折扣")
                    return
                }
                SaveRechargeCardInfo.save($rootScope.settingAddsome.editedRecharge, function (data) {
                    if(data.result==Global.SUCCESS){
                        $state.go("basicSetting")
                        $rootScope.settingAddsome.editedRecharge={
                            name:'',
                            amount:"",
                            imageList:[],
                            introduce:'',
                            status:'0',
                            timesList:[],/*次卡数组id*/
                            periodList:[],/*疗程卡数组id*/
                            productList:[]/*产品数组id*/,
                            timeDiscount:'',/*单次折扣*/
                            periodDiscount:'',/*疗程卡折扣*/
                            productDiscount:''/*产品折扣*/
                        }
                    }else{
                        alert("保存未成功")
                    }

                })
            }

        }]);