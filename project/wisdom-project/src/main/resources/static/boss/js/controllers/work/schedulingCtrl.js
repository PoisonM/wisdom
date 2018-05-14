/**
 * Created by Administrator on 2018/5/4.
 */
angular.module('controllers',[]).controller('schedulingCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopClerkScheduleList','GetBossShopList',
        function ($scope,$rootScope,$stateParams,$state,GetShopClerkScheduleList,GetBossShopList) {
            $rootScope.title = "排班";
            $scope.param={
                nowdate:new Date().getFullYear()+"年"+parseInt(new Date().getMonth()+1)+"月",//初始化时间
                compileDateFlag:true,
                displayShopBox:false,
                displayShop:false
            };

            $scope.queryScheduleList = function (searchDate) {
                GetShopClerkScheduleList.get({
                    searchDate:searchDate,
                    sysShopId:"11"
                },function (data) {
                    if(data.result == "0x00001"){
                        $scope.tempWeek = data.responseData.dateDetail;
                        for(var i = 0; i < $scope.tempWeek.length; i++){
                            $scope.tempWeek[i] = ($scope.tempWeek[i].split("||")[0].substr($scope.tempWeek[i].split("||")[0].length-2,2)+","+$scope.tempWeek[i].split("||")[1].replace("星期","周")).split(",")
                        }
                        $scope.tempUser = data.responseData.responseList
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

            /*切换店铺*/
            $scope.tabShop=function () {
                $scope.param.displayShopBox=true;
                $scope.param.displayShop=true;
                GetBossShopList.get(function (data) {
                    console.log(data);
                    $scope.shopList=data.responseData;
                });
            };
            $scope.closeBox=function () {
                $scope.param.displayShopBox=false;
                $scope.param.displayShop=false;
            };

            /*点击店铺名字切换店铺*/
            $scope.choseShop=function (id) {
                $scope.queryScheduleList($scope.param.nowdate.replace("年","-").replace("月","-1"));
                $scope.param.displayShopBox=false;
                $scope.param.displayShop=false;
            };


            //调用固定表头类
            var tiemInt = setInterval(function () {
                if($("#tbTest1 thead tr td").length != 0){
                    var ofix1 = new oFixedTable('ofix1', document.getElementById('tbTest1'), {rows: 1, cols: 1});
                    clearTimeout(tiemInt)
                }
            },100)
        }]);