PADWeb.controller("compileWorkListCtrl", function($scope, $state, $stateParams
    ,GetShopClerkScheduleList,UpdateShopClerkScheduleList) {
    $scope.$parent.param.top_bottomSelect = "yuyue";
    $scope.$parent.mainSwitch.headerCashAllFlag = false;


    $scope.topLeftFlag = false
    $scope.nowDate = $stateParams.time


    GetShopClerkScheduleList.get({
        searchDate:$stateParams.time,
        sysShopId:""
    },function (data) {
        if(data.result == "0x00001"){
            $scope.tempWeek = data.responseData.dateDetail
            for(var i = 0; i < $scope.tempWeek.length; i++){
                $scope.tempWeek[i] = ($scope.tempWeek[i].split("||")[0].substr($scope.tempWeek[i].split("||")[0].length-2,2)+","+$scope.tempWeek[i].split("||")[1].replace("星期","周")).split(",")
            }
            $scope.tempUser = data.responseData.responseList
            console.log(data)
        }
        //调用固定表头类
        var tiemInt = setInterval(function () {
            if ($("#tbTest1 thead tr td").length != 0) {
                var ofix1 = new oFixedTable('ofix1', document.getElementById('tbTest1'), {rows: 1, cols: 1});
                clearTimeout(tiemInt)
            }
        }, 100)
    })

    $scope.importData = {
        shopClerkSchedule:[],
    }
    $scope.tempType = ""


    $scope.changeType = function (type) {
        $scope.tempType = type

    }

    $scope.checkedItem = function (parentIndex,index,id,sysBossId,sysClerkId,sysClerkName,scheduleDate,sysShopId) {
        /*修改样式*/
        if($scope.tempType === ""){
            return
        }
        $scope.tempUser[parentIndex].clerkSchInfo[index].scheduleType = $scope.tempType


        $scope.importData.shopClerkSchedule.push({
            id:id,
            sysBossId:sysBossId,
            sysClerkId:sysClerkId,
            sysClerkName:sysClerkName,
            scheduleDate:scheduleDate,
            sysShopId:sysShopId,
            scheduleType:$scope.tempType
        })


    }
    $scope.save = function () {
        if($scope.tempType === ""){
            alert("请选择编辑对象")
            return
        }
        UpdateShopClerkScheduleList.save($scope.importData,function (data) {
            if(data.result == "0x00001"){
                alert("保存成功")
                $state.go("pad-web.arrangeWorkList")
            }
        })
    }
    









})


