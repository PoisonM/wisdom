angular.module('controllers',[]).controller('modifyProductCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','ProductInfoMess','Global','UpdateProductInfo','$filter','ImageBase64UploadToOSS',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,ProductInfoMess,Global,UpdateProductInfo,$filter,ImageBase64UploadToOSS) {
            $rootScope.title = "修改产品";

            $scope.param = {
                status:""
            };

            /*日期插件*/
            var disabledDates = [
                new Date(1437719836326),
                new Date(),
                new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
                new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
                new Date("08-14-2015"), //Short format
                new Date(1439676000000) //UNIX format
            ];
            //方便的年月日设置方式，正和我意，可以随便改了。
            var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
            var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];

            // 日期选择后的回调函数
            var datePickerCallback = function (val) {
                if (typeof (val) === 'undefined') {
                } else {
                    var dateValue = $filter('date')(val, 'yyyy-MM-dd') + " 00:00:00";
                    $rootScope.settingAddsome.product.effectDate = $filter('date')(val, 'yyyy-MM-dd')

                }
            };
            //主体对象
            $scope.datepickerObjectEnd = {
                titleLabel: '选择日期',  //可选
                todayLabel: '今天',  //可选
                closeLabel: '关闭',  //可选
                setLabel: '确定',  //可选
                setButtonType: 'button-assertive',  //可选
                todayButtonType: 'button-assertive',  //可选
                closeButtonType: 'button-assertive',  //可选
                inputDate: new Date(),  //可选，输入值
                mondayFirst: true,  //可选,星期一开头
                disabledDates: disabledDates, //可选
                weekDaysList: weekDaysList, //可选
                monthList: monthList, //可选
                templateType: 'modal', //可选i.e.的模式 modal or popup(兼容模式？)
                showTodayButton: 'true', //可选
                modalHeaderColor: 'bar-positive', //可选
                modalFooterColor: 'bar-positive', //可选
                from: new Date(2008, 8, 2), //可选
                to: new Date(2030, 8, 25),  //可选
                callback: function (val) {  //Mandatory
                    datePickerCallback(val);
                },
                dateFormat: 'yyyy-MM-dd', //可选
                closeOnSelect: true, //可选,设置选择日期后是否要关掉界面。呵呵，原本是false。
            };

                if( $stateParams.id !=''){
                    $rootScope.settingAddsome.productId = $stateParams.id
                    $ionicLoading.show({
                        content: 'Loading',
                        animation: 'fade-in',
                        showBackdrop: true,
                        maxWidth: 200,
                        showDelay: 0
                    })
                    ProductInfoMess.get({
                        productId:$rootScope.settingAddsome.productId
                    },function (data) {
                        if(data.result==Global.SUCCESS&&data.responseData!=null){
                            $ionicLoading.hide()
                            $rootScope.settingAddsome.product = data.responseData;
                            if($rootScope.settingAddsome.product.status =='0'){
                                $scope.param.status = true;
                            }else{
                                $scope.param.status = false;
                            }
                        }
                    })
                }



            $scope.selProductType = function(type){
                $rootScope.settingAddsome.product.productType = type
            }

            /*选择品牌*/
            $scope.selBrandGo = function () {
                $state.go('selBrand',{url:'modifyProduct'});
            }
            /*选择系列*/
            $scope.selectionSeriesGo=function(){
                $state.go("selectionSeries",{url:'modifyProduct'})
            }
            /*选择规格*/
            $scope.specificationsGo = function(){
                $state.go("specifications",{url:'modifyProduct'})

            }
            /*选择单位*/
            $scope.unitGo = function(){
                $state.go("unit",{url:'modifyProduct'})
            }
            /*适用部位*/
            $scope.applicablePartsGo = function(){
                $state.go("applicableParts",{url:'modifyProduct'})

            }
            /*选择功效*/
            $scope.efficacyGo = function(){
                $state.go("efficacy",{url:'modifyProduct'})

            }
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                if($rootScope.settingAddsome.product.imageList ==null) $rootScope.settingAddsome.product.imageList=[]
                if($rootScope.settingAddsome.product.imageList.length>=6){
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
                                $rootScope.settingAddsome.product.imageList.push(data.responseData)
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.delPic = function(index){
                $rootScope.settingAddsome.product.imageList.splice(index,1)
            }



            $scope.save = function(type){
                if(type=="0"){
                    $rootScope.settingAddsome.product.status = '1';
                }else{
                    if($scope.param.status ==true){
                        $rootScope.settingAddsome.product.status = '0';
                    }else{
                        $rootScope.settingAddsome.product.status = '1';
                    }
                }
                UpdateProductInfo.save($rootScope.settingAddsome.product,function(data){
                    if(data.result==Global.SUCCESS){

                        $state.go("warehouseProducts")
                    }
                })

            }

        }])