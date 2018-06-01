/**
 * Created by Administrator on 2018/5/31.
 */
angular.module('controllers',[]).controller('employeeSchedulingCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopClerkScheduleListForClerk',
        function ($scope,$rootScope,$stateParams,$state,GetShopClerkScheduleListForClerk) {
            $rootScope.title = "排班";
            $scope.param={
                nowdate:new Date().getFullYear()+"年"+parseInt(new Date().getMonth()+1)+"月",//初始化时间
                compileDateFlag:true
            };

            $scope.queryScheduleList = function (searchDate) {
                GetShopClerkScheduleListForClerk.get({
                    searchDate:searchDate
                },function (data) {
                    if(data.result == "0x00001"){
                        $scope.tempWeek = data.responseData.dateDetail;
                        console.log($scope.tempWeek);
                        for(var i = 0; i < $scope.tempWeek.length; i++){
                            $scope.tempWeek[i] = ($scope.tempWeek[i].split("||")[0].substr($scope.tempWeek[i].split("||")[0].length-2,2)+","+$scope.tempWeek[i].split("||")[1].replace("星期","周")).split(",")
                        }
                        $scope.tempUser = data.responseData.responseList;
                    }
                })
            };
            $scope.queryScheduleList($scope.param.nowdate.replace("年","-").replace("月","-1"));
            $scope.compileDateFn = function () {
                if(new Date().getFullYear()+"年"+parseInt(new Date().getMonth()+1)+"月" == $scope.param.nowdate){
                    $scope.param.compileDateFlag = false
                }else{
                    $scope.param.compileDateFlag = true
                }
            };
            $scope.compileDateFn();
            /*更改日期*/
            $scope.subMonth = function () {
                $scope.dataYear = parseInt($scope.param.nowdate.split("年")[0])
                $scope.dataMonth = parseInt($scope.param.nowdate.split("年")[1].split("月")[0])
                $scope.param.nowdate = $scope.dataYear+"年"+($scope.dataMonth-1)+"月"
                if($scope.dataMonth == 0){
                    $scope.dataYear = $scope.dataYear-1
                    $scope.dataMonth = 12
                    $scope.param.nowdate = $scope.dataYear+"年"+$scope.dataMonth+"月"
                }
                $scope.queryScheduleList($scope.param.nowdate.replace("年","-").replace("月","-1"))
                $scope.compileDateFn()
            };
            $scope.addMonth = function () {
                $scope.dataYear = parseInt($scope.param.nowdate.split("年")[0])
                $scope.dataMonth = parseInt($scope.param.nowdate.split("年")[1].split("月")[0])
                $scope.param.nowdate = $scope.dataYear+"年"+($scope.dataMonth+1)+"月"
                if($scope.dataMonth == 12){
                    $scope.dataYear = $scope.dataYear+1
                    $scope.dataMonth = 0
                    $scope.param.nowdate = $scope.dataYear+"年"+$scope.dataMonth+"月"
                }
                $scope.queryScheduleList($scope.param.nowdate.replace("年","-").replace("月","-1"))
                $scope.compileDateFn()
            };
            var tiemInt = setInterval(function () {
                if($("#tbTest1 thead tr td").length != 0){
                    var ofix1 = new oFixedTable('ofix1', document.getElementById('tbTest1'), {rows: 1, cols: 1});
                    clearTimeout(tiemInt)
                }
            },100)
        }]);