/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyAppointCtrl',
    ['$scope','$rootScope','$stateParams','$state','BeautyUtil','GetClerkScheduleInfo','GetBeautyShopInfo','Global',
        function ($scope,$rootScope,$stateParams,$state,BeautyUtil,GetClerkScheduleInfo,GetBeautyShopInfo,Global) {

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

            $scope.$on('$ionicView.enter', function(){

                $scope.param = {
                    weekDays : [],
                    timeDate :[],
                    chooseDate : BeautyUtil.getAddDate(BeautyUtil.getNowFormatDate(),0),
                    clerkInfo : {},
                    beautyShopInfo : {}
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

                initialTimeDate();
                if($rootScope.shopAppointInfo.clerkId!='')
                {
                    GetBeautyShopInfo.clerkInfo($rootScope.shopAppointInfo.clerkId).then(function(data) {
                        //success函数
                        $scope.param.clerkInfo = data[0];
                        GetClerkScheduleInfo.get({clerkId:$rootScope.shopAppointInfo.clerkId,
                            searchDate:$scope.param.chooseDate},function (data){
                            arrangeTimeDate($scope.param.timeDate,data.responseData.split(","));
                        })
                    })

                }

                if($rootScope.shopAppointInfo.shopProjectId!='')
                {
                    GetBeautyShopInfo.shopProjectInfo($rootScope.shopAppointInfo.shopProjectId).then(function (data) {
                        $scope.param.beautyShopInfo = data.responseData;
                    })
                }
            })

            $scope.chooseAppointDate = function(dateValue){
                $scope.param.chooseDate = dateValue;
                GetClerkScheduleInfo.get({clerkId:$rootScope.shopAppointInfo.clerkId,
                    searchDate:$scope.param.chooseDate},function (data){
                    initialTimeDate();
                    console.log(data.responseData.split(","));
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

            }

            var arrangeTimeDate = function (timeDate,schedule) {
                console.log(schedule);
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