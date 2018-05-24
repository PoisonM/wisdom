angular.module('controllers',[]).controller('addProductCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','BossUtil','$filter','SaveProductInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,BossUtil,$filter,SaveProductInfo) {
            $rootScope.title = "添加产品";
            $scope.selFlag =''
            $scope.param ={
                productType:"0",
                productTypeOneName:"",
                productTypeOneId:"",
                productTypeTwoName:"",
                productTypeTwoId:"",
                productName:"",
                imageUrl:[],
                marketPrice:"",
                discountPrice:"",
                productCode:"",
                productSpec:"",
                productUnit:"",
                productPosition:"",
                productFunction:"",
                status:'0',
                introduce:"",
                effectDate:'2017-04-07',
                qualityPeriod:"",
                productWarningDay:"",
                productWarningNum:"",

            }

            $scope.style = function(routeStyle,dataStyle){
                if($stateParams[routeStyle] !=""){
                    $scope.param =JSON.parse(localStorage.getItem('param'));
                    $scope.param[dataStyle] =$stateParams[routeStyle];
                }
            };

            $scope.style('oneId','productTypeOneId')
            $scope.style('band','productTypeOneName')
            $scope.style('twoId','productTypeTwoId')
            $scope.style('series','productTypeTwoName')
            $scope.style('spec','productSpec')
            $scope.style('unit','productUnit')
            $scope.style('parts','productPosition')
            $scope.style('func','productFunction')



            if($scope. param.status =='0'){
                $scope.selFlag = true
            }else{
                $scope.selFlag = false
            }

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
                    $scope.param.effectDate = $filter('date')(val, 'yyyy-MM-dd')
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



            $scope.selProductType = function(type){
                $scope.param.productType = type
            }
            /*选择品牌*/
            $scope.selBrandGo = function () {
                $state.go('selBrand',{type:'add'});
                localStorage.setItem('param',JSON.stringify($scope.param));
            }
            /*选择系列*/
            $scope.selectionSeriesGo=function(){
                $state.go("selectionSeries",{type:'add',productTypeOneId:$scope.param.productTypeOneId})
                localStorage.setItem('param',JSON.stringify($scope.param));
            }
            /*选择规格*/
            $scope.specificationsGo = function(){
                $state.go("specifications",{type:'add',productSpec:$scope.param.productSpec})
                localStorage.setItem('param',JSON.stringify($scope.param));
            }
            /*选择单位*/
            $scope.unitGo = function(){
                $state.go("unit",{type:'add'})
                localStorage.setItem('param',JSON.stringify($scope.param));
            }
            /*适用部位*/
            $scope.applicablePartsGo = function(){
                $state.go("applicableParts",{type:'add'})
                localStorage.setItem('param',JSON.stringify($scope.param));
            }
            /*选择功效*/
            $scope.efficacyGo = function(){
                $state.go("efficacy",{type:'add',productFunc:$scope.param.productFunction})
                localStorage.setItem('param',JSON.stringify($scope.param));
            }




            $scope.save = function(){
                if($scope.selFlag ==true){
                    $scope.param.status = '0';
                }else{
                    $scope.param.status = '1';
                }
                /*if($scope.param.productTypeOneName == ""||$scope.param.productTypeTwoName ==""||$scope.param.productName ==""||$scope.param.marketPrice ==""||$scope.param.discountPrice ==""||$scope.param.productSpec ==""||$scope.param.productUnit ==""||$scope.param.effectDate ==""||$scope.param.qualityPeriod ==""||$scope.param.productWarningDay ==""||$scope.param.productWarningNum ==""){
                    alert('信息不完全')
                }*/
                localStorage.removeItem('param');
                console.log($scope.param);
                SaveProductInfo.save($scope.param,function(data){

                })
            }

        }])