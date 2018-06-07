/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyAppointCtrl',
    ['$scope','$rootScope','$stateParams','$state','BeautyUtil','GetClerkScheduleInfo',
        'GetBeautyShopInfo','Global','SaveUserAppointInfo','GetCurrentLoginUserInfo',
        function ($scope,$rootScope,$stateParams,$state,BeautyUtil,GetClerkScheduleInfo,
                  GetBeautyShopInfo,Global,SaveUserAppointInfo,GetCurrentLoginUserInfo) {

            var initialTimeDate = function () {
                $scope.param.timeDate = [
                    [{index:'0',value:'00:00',status:'0'},{index:'1',value:'00:30',status:'0'},{index:'2',value:'01:00',status:'0'},{index:'3',value:'01:30',status:'0'},{index:'4',value:"02:00",status:'0'},{index:'5',value:'02:30',status:'0'}],
                    [{index:'6',value:'03:00',status:'0'},{index:'7',value:'03:30',status:'0'},{index:'8',value:'04:00',status:'0'},{index:'9',value:'04:30',status:'0'},{index:'10',value:"05:00",status:'0'},{index:'11',value:'05:30',status:'0'}],
                    [{index:'12',value:'06:00',status:'0'},{index:'13',value:'06:30',status:'0'},{index:'14',value:'07:00',status:'0'},{index:'15',value:'07:30',status:'0'},{index:'16',value:"08:00",status:'0'},{index:'17',value:'08:30',status:'0'}],
                    [{index:'18',value:'09:00',status:'0'},{index:'19',value:'09:30',status:'0'},{index:'20',value:'10:00',status:'0'},{index:'21',value:'10:30',status:'0'},{index:'22',value:"11:00",status:'0'},{index:'23',value:'11:30',status:'0'}],
                    [{index:'24',value:'12:00',status:'0'},{index:'25',value:'12:30',status:'0'},{index:'26',value:'13:00',status:'0'},{index:'27',value:'13:30',status:'0'},{index:'28',value:"14:00",status:'0'},{index:'29',value:'14:30',status:'0'}],
                    [{index:'30',value:'15:00',status:'0'},{index:'31',value:'15:30',status:'0'},{index:'32',value:'16:00',status:'0'},{index:'33',value:'16:30',status:'0'},{index:'34',value:"17:00",status:'0'},{index:'35',value:'17:30',status:'0'}],
                    [{index:'36',value:'18:00',status:'0'},{index:'37',value:'18:30',status:'0'},{index:'38',value:'19:00',status:'0'},{index:'39',value:'19:30',status:'0'},{index:'40',value:"20:00",status:'0'},{index:'41',value:'20:30',status:'0'}],
                    [{index:'42',value:'21:00',status:'0'},{index:'43',value:'21:30',status:'0'},{index:'44',value:'22:00',status:'0'},{index:'45',value:'22:30',status:'0'},{index:'46',value:"23:00",status:'0'},{index:'47',value:'23:30',status:'0'}],
                ]
            }

            var appointTimeDate = function () {
                $scope.param.timeDate = [
                    [{index:'0',value:'00:00',status:'1'},{index:'1',value:'00:30',status:'1'},{index:'2',value:'01:00',status:'1'},{index:'3',value:'01:30',status:'1'},{index:'4',value:"02:00",status:'1'},{index:'5',value:'02:30',status:'1'}],
                    [{index:'6',value:'03:00',status:'1'},{index:'7',value:'03:30',status:'1'},{index:'8',value:'04:00',status:'1'},{index:'9',value:'04:30',status:'1'},{index:'10',value:"05:00",status:'1'},{index:'11',value:'05:30',status:'1'}],
                    [{index:'12',value:'06:00',status:'1'},{index:'13',value:'06:30',status:'1'},{index:'14',value:'07:00',status:'1'},{index:'15',value:'07:30',status:'1'},{index:'16',value:"08:00",status:'1'},{index:'17',value:'08:30',status:'1'}],
                    [{index:'18',value:'09:00',status:'1'},{index:'19',value:'09:30',status:'1'},{index:'20',value:'10:00',status:'1'},{index:'21',value:'10:30',status:'1'},{index:'22',value:"11:00",status:'1'},{index:'23',value:'11:30',status:'1'}],
                    [{index:'24',value:'12:00',status:'1'},{index:'25',value:'12:30',status:'1'},{index:'26',value:'13:00',status:'1'},{index:'27',value:'13:30',status:'1'},{index:'28',value:"14:00",status:'1'},{index:'29',value:'14:30',status:'1'}],
                    [{index:'30',value:'15:00',status:'1'},{index:'31',value:'15:30',status:'1'},{index:'32',value:'16:00',status:'1'},{index:'33',value:'16:30',status:'1'},{index:'34',value:"17:00",status:'1'},{index:'35',value:'17:30',status:'1'}],
                    [{index:'36',value:'18:00',status:'1'},{index:'37',value:'18:30',status:'1'},{index:'38',value:'19:00',status:'1'},{index:'39',value:'19:30',status:'1'},{index:'40',value:"20:00",status:'1'},{index:'41',value:'20:30',status:'1'}],
                    [{index:'42',value:'21:00',status:'1'},{index:'43',value:'21:30',status:'1'},{index:'44',value:'22:00',status:'1'},{index:'45',value:'22:30',status:'1'},{index:'46',value:"23:00",status:'1'},{index:'47',value:'23:30',status:'1'}],
                ]
            }

            $scope.$on('$ionicView.enter', function(){

                GetCurrentLoginUserInfo.get(function (data) {
                    BeautyUtil.checkResponseData(data,'beautyAppoint');
                })

                $scope.param = {
                    weekDays : [],
                    timeDate :[],
                    chooseDate : BeautyUtil.getAddDate(BeautyUtil.getNowFormatDate(),0),
                    clerkInfo : {},
                    beautyShopInfo : [],
                    beautyProjectName : '',
                    beautyProjectIds : '',
                    beautyProjectDuration : 0,
                    appointFlag : false,
                    chooseDateTime : ''
                }

                for(var i = 0; i<7; i++)
                {
                    var dateValue = BeautyUtil.getAddDate(BeautyUtil.getNowFormatDate(),i);
                    var dateIndex = BeautyUtil.getAddDateIndex(BeautyUtil.getNowFormatDate(),i);
                    if(i==0)
                    {
                        dateIndex = '今天';
                    }
                    if(i==1)
                    {
                        dateIndex = '明天';
                    }
                    if(i==2)
                    {
                        dateIndex = '后天';
                    }
                    var value = {
                        dateIndex : angular.copy(dateIndex),
                        dateValue : angular.copy(dateValue)
                    }
                    $scope.param.weekDays.push(angular.copy(value));
                }

                if($rootScope.shopAppointInfo.clerkId!='')
                {    //防止用户已经选中预约时间段被冲掉
                    if($rootScope.shopAppointInfo.appointValue!=''){
                        $scope.appointProject($rootScope.shopAppointInfo.appointValue);
                        //帮助回显
                        $scope.param.chooseDate = $rootScope.shopAppointInfo.chooseDate;
                    }else{
                        initialTimeDate();
                        GetBeautyShopInfo.clerkInfo($rootScope.shopAppointInfo.clerkId).then(function(data) {
                            //success函数
                            $scope.param.clerkInfo = data[0];
                            GetClerkScheduleInfo.get({clerkId:$rootScope.shopAppointInfo.clerkId,
                                searchDate:$scope.param.chooseDate},function (data){
                                arrangeTimeDate($scope.param.timeDate,data.responseData.split(","));
                            })
                        })
                    }
                }
                //如果没有选中美容师，默认为全选中状态
                else {
                    appointTimeDate();
                }

                if($rootScope.shopAppointInfo.shopProjectIds.length!=0)
                {
                    var length = $rootScope.shopAppointInfo.shopProjectIds.length
                    getBeautyShopInfo($rootScope.shopAppointInfo.shopProjectIds.length[length-1],length);
                }
            })

            var getBeautyShopInfo = function (projectId,length) {
                GetBeautyShopInfo.shopProjectInfo(projectId).then(function (data) {
                    BeautyUtil.checkResponseData(data,'beautyAppoint');
                    $scope.param.beautyShopInfo.push(data.responseData);
                    length--;
                    if(length>=0)
                    {
                        getBeautyShopInfo($rootScope.shopAppointInfo.shopProjectIds[length],length);
                    }
                    else
                    {
                        $scope.param.beautyShopInfo.splice(0,1);
                        angular.forEach($scope.param.beautyShopInfo,function (value,index) {
                            if(index==($scope.param.beautyShopInfo.length-1))
                            {
                                $scope.param.beautyProjectName = $scope.param.beautyProjectName + value.projectName;
                                $scope.param.beautyProjectIds = $scope.param.beautyProjectIds + value.id;
                            }
                            else
                            {
                                $scope.param.beautyProjectName = $scope.param.beautyProjectName + value.projectName + ',';
                                $scope.param.beautyProjectIds = $scope.param.beautyProjectIds + value.id + ',';
                            }
                            $scope.param.beautyProjectDuration = $scope.param.beautyProjectDuration + value.projectDuration;
                        })
                    }
                });
            }

            $scope.chooseAppointDate = function(dateValue){
                $scope.param.chooseDate = dateValue;
                //帮助回显
                $rootScope.shopAppointInfo.chooseDate = dateValue;
                GetClerkScheduleInfo.get({clerkId:$rootScope.shopAppointInfo.clerkId,
                    searchDate:$scope.param.chooseDate},function (data){
                    BeautyUtil.checkResponseData(data,'beautyAppoint');
                    initialTimeDate();
                    arrangeTimeDate($scope.param.timeDate,data.responseData.split(","));
                })
            }

            $scope.chooseProject=function () {
                $state.go("beautyProjectList");
            }

            $scope.chooseClerk=function () {
                $state.go("beautyClerkList");
            }

            $scope.appointProject = function(appointValue){
                //缓存用户选择的时间
                $rootScope.shopAppointInfo.appointValue = appointValue;
                initialTimeDate();
                if($rootScope.shopAppointInfo.clerkId!='')
                {
                    GetBeautyShopInfo.clerkInfo($rootScope.shopAppointInfo.clerkId).then(function(data) {
                        BeautyUtil.checkResponseData(data,'beautyAppoint');
                        //success函数
                        $scope.param.clerkInfo = data[0];
                        GetClerkScheduleInfo.get({clerkId:$rootScope.shopAppointInfo.clerkId,
                            searchDate:$scope.param.chooseDate},function (data){
                            arrangeTimeDate($scope.param.timeDate,data.responseData.split(","));

                            $scope.param.chooseDateTime = $scope.param.chooseDate+' '+appointValue.value;
                            var length = $scope.param.beautyProjectDuration/30;

                            var flag = false;
                            angular.forEach($scope.param.timeDate,function (val,index) {
                                angular.forEach(val,function (data,index) {
                                    if(flag&&length>=0)
                                    {
                                        if(data.status=='0')
                                        {
                                            alert("此时间无法预约");
                                            $scope.chooseAppointDate($scope.param.chooseDate);
                                            flag = false;
                                        }
                                        else
                                        {
                                            data.status = '2';
                                            //防止时间出现小数，向上取整
                                            if (Math.ceil(length) == 0)
                                            {
                                                $scope.param.appointFlag = true;
                                            }
                                        }
                                        length--;
                                    }
                                    if(data.value==appointValue.value&&val.status=='0')
                                    {
                                        alert("此时间无法预约");
                                    }
                                    else if(data.value==appointValue.value)
                                    {
                                        flag = true;
                                        data.status = '2';
                                        length--;
                                    }
                                })
                            })
                        })
                    })
                }


            }
            
            $scope.confirmAppoint = function () {
                if($scope.param.appointFlag)
                {
                    SaveUserAppointInfo.save({shopProjectId:$scope.param.beautyProjectIds,
                        sysClerkId:$rootScope.shopAppointInfo.clerkId,
                        appointStartTimeS:$scope.param.chooseDateTime,
                        shopProjectName:$scope.param.beautyProjectName,
                        appointPeriod:$scope.param.beautyProjectDuration,
                        detail:$rootScope.shopAppointInfo.shopProjectDetail,
                        status:'0'
                    },function (data) {
                        BeautyUtil.checkResponseData(data,'beautyAppoint');
                        if(data.result==Global.SUCCESS)
                        {
                            $rootScope.shopAppointInfo.shopProjectDetail = '';
                            $state.go("beautyUserAppointDetail",{appointId:data.responseData.appointmentId});
                        }
                    })
                }
                else
                {
                    alert("不满足预约条件");
                }
            }

            var arrangeTimeDate = function (timeDate,schedule) {
                angular.forEach(timeDate,function (value,index) {
                    angular.forEach(value,function (value1,index) {
                        angular.forEach(schedule,function (val,index) {
                            if(value1.index==val)
                            {
                                value1.status = '1';
                            }
                        })
                    })
                })
            }

}])
//捎句话过滤器
var beauty = angular.module('filter', []);
beauty.filter('textLengthSet', function() {
    return function(value, wordwise, max, tail) {
        if (!value) {
            return '';
        }
        max = parseInt(max, 10);
        if (!max) return value;
        if (value.length <= max){
            return value;
        }
        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                value = value.substr(0, lastspace);
            }
        }
        return value + (tail || ' …');//'...'可以换成其它文字
    };
});