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
                    $scope.modifyProduct.effectDate = $filter('date')(val, 'yyyy-MM-dd')

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
            ProductInfoMess.get({
                productId:$stateParams.id
            },function (data) {
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.modifyProduct = data.responseData;
                    $scope.style('oneId','productTypeOneId');
                    $scope.style('twoId','productTypeTwoId');
                    $scope.style('band','productTypeOneName');
                    $scope.style('series','productTypeTwoName');
                    $scope.style('spec','productSpec');
                    $scope.style('unit','productUnit');
                    $scope.style('parts','productPosition');
                    $scope.style('func','productFunction');
                    if($scope.modifyProduct.status =='0'){
                        $scope.param.status = true;
                    }else{
                        $scope.param.status = false;
                    }
                }
            })
            $scope.selProductType = function(type){
                $scope.modifyProduct.productType = type
            }

            $scope.style = function(routeStyle,dataStyle){
                if($stateParams[routeStyle] !=""){
                    $scope.modifyProduct =JSON.parse(localStorage.getItem('modifyProduct'));
                    $scope.modifyProduct[dataStyle] =$stateParams[routeStyle];
                }
            }
            /*选择品牌*/
            $scope.selBrandGo = function () {
                $state.go('selBrand',{id:$stateParams.id});
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*选择系列*/
            $scope.selectionSeriesGo=function(){
                $state.go("selectionSeries",{id:$stateParams.id,productTypeOneId:$scope.modifyProduct.productTypeOneId})
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*选择规格*/
            $scope.specificationsGo = function(){
                $state.go("specifications",{id:$stateParams.id,productSpec:$scope.modifyProduct.productSpec})
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*选择单位*/
            $scope.unitGo = function(){
                $state.go("unit",{id:$stateParams.id})
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*适用部位*/
            $scope.applicablePartsGo = function(){
                $state.go("applicableParts",{id:$stateParams.id})
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*选择功效*/
            $scope.efficacyGo = function(){
                $state.go("efficacy",{id:$stateParams.id,productFunc:$scope.modifyProduct.productFunction})
                localStorage.setItem('modifyProduct',JSON.stringify($scope.modifyProduct));
            }
            /*上传图片*/
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function(files) {
                console.log(files)
                if(files.length <=0)return
                if($scope.modifyProduct.imageUrl.length>6){
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
                                $scope.modifyProduct.imageUrl.push(data.responseData)
                            }

                        })
                    };
                    fr.readAsDataURL(file);

                }else {
                    alert("浏览器不支持")
                }


            };
            $scope.delPic = function(index){
                $scope.modifyProduct.imageUrl.splice(index,1)
            }



            $scope.save = function(type){
                if(type=="0"){
                    $scope.modifyProduct.status = '1';
                }else{
                    if($scope.param.status ==true){
                        $scope.modifyProduct.status = '0';
                    }else{
                        $scope.modifyProduct.status = '1';
                    }
                }
                UpdateProductInfo.save($scope.modifyProduct,function(data){
                    if(data.result==Global.SUCCESS){
                        localStorage.removeItem('modifyProduct');

                        $state.go("warehouseProducts")
                    }
                })

            }

        }])