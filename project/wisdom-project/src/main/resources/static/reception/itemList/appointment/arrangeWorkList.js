PADWeb.controller("arrangeWorkListCtrl", function($scope, $state, $stateParams,GetShopClerkScheduleList) {
    $scope.$parent.param.top_bottomSelect = "yuyue";
    $scope.$parent.mainSwitch.headerCashAllFlag = false;
    $scope.topFlag = false
    $scope.leftFlag = false
    $scope.param = {
        nowdate:new Date().getFullYear()+"年"+parseInt(new Date().getMonth()+1)+"月",
        compileDateFlag:true
    }
    /*---------------*/

    
    $scope.compileDateFn = function () {
        if(new Date().getFullYear()+"年"+parseInt(new Date().getMonth()+1)+"月" == $scope.param.nowdate){
            $scope.param.compileDateFlag = false
        }else{
            $scope.param.compileDateFlag = true
        }
    }
    $scope.compileDateFn()

    $scope.queryScheduleList = function (searchDate) {
        GetShopClerkScheduleList.get({
            searchDate:searchDate,
        },function (data) {
            if(data.result == "0x00001"){
                $scope.tempWeek = data.responseData.dateDetail
                for(var i = 0; i < $scope.tempWeek.length; i++){
                    $scope.tempWeek[i] = ($scope.tempWeek[i].split("||")[0].substr($scope.tempWeek[i].split("||")[0].length-2,2)+","+$scope.tempWeek[i].split("||")[1].replace("星期","周")).split(",")
                }
                $scope.tempUser = data.responseData.responseList
            }
        })
    }

    $scope.queryScheduleList($scope.param.nowdate.replace("年","-").replace("月","-1"))

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
        setTimeout(function(){$scope.tableThead()},2000);
    }
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
        setTimeout(function(){$scope.tableThead()},2000);
    }
    //调用固定表头类
    $scope.tableThead = function () {
        $("#ofix1_tb_header").remove();
        $("#ofix1_tb_left").remove();
        $("#ofix1_div_top_left").remove();
        setTimeout(function () {
            if ($("#tbTest1 thead tr td").length > 1) {
                var ofix1 = new oFixedTable('ofix1', document.getElementById('tbTest1'), {rows: 1, cols: 1});
            }
        }, 100)

    }
    $scope.tableThead();

    $scope.goCompileWorkList = function () {
        $scope.tempTime = $scope.param.nowdate.replace("年","-").replace("月","-1")
        $state.go("pad-web.compileWorkList",{time:$scope.tempTime})
    }

    $scope.arrangeWorkListBack = function () {
        window.history.go(-1)
    }





})


