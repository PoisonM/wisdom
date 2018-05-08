PADWeb.controller("compileWorkListCtrl", function($scope, $state, $stateParams
    ,GetShopClerkScheduleList,UpdateShopClerkScheduleList) {
    $scope.$parent.param.top_bottomSelect = "yuyue";
    $scope.$parent.mainSwitch.headerCashAllFlag = false;


    $scope.topLeftFlag = false


    GetShopClerkScheduleList.get({
        searchDate:$stateParams.time
    },function (data) {
        if(data.result == "0x00001"){
            $scope.tempWeek = data.responseData.dateDetail
            for(var i = 0; i < $scope.tempWeek.length; i++){
                $scope.tempWeek[i] = ($scope.tempWeek[i].split("||")[0].substr($scope.tempWeek[i].split("||")[0].length-2,2)+","+$scope.tempWeek[i].split("||")[1].replace("星期","周")).split(",")
            }
            $scope.tempUser = data.responseData.responseList
        }
    })


    var startx, starty;
    //获得角度
    function getAngle(angx, angy) {
        return Math.atan2(angy, angx) * 180 / Math.PI;
    };

    //根据起点终点返回方向 1向上 2向下 3向左 4向右 0未滑动
    function getDirection(startx, starty, endx, endy) {
        var angx = endx - startx;
        var angy = endy - starty;
        var result = 0;

        //如果滑动距离太短
        if (Math.abs(angx) < 2 && Math.abs(angy) < 2) {
            return result;
        }

        var angle = getAngle(angx, angy);
        if (angle >= -135 && angle <= -45) {
            result = 1;
            $(".reality_content").css({"overflow-y":"scroll"})
            $(".reality_content").css({"overflow-x":"hidden"})
        } else if (angle > 45 && angle < 135) {
            result = 2;
            $(".reality_content").css({"overflow-y":"scroll"})
            $(".reality_content").css({"overflow-x":"hidden"})
        } else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
            result = 3;
            $(".reality_content").css({"overflow-y":"hidden"})
            $(".reality_content").css({"overflow-x":"scroll"})
        } else if (angle >= -45 && angle <= 45) {
            result = 4;
            $(".reality_content").css({"overflow-y":"hidden"})
            $(".reality_content").css({"overflow-x":"scroll"})
        }

        return result;
    }
    //手指接触屏幕
    document.addEventListener("touchstart", function(e) {
        startx = e.touches[0].pageX;
        starty = e.touches[0].pageY;
    }, false);
    //手指离开屏幕
    document.addEventListener("touchend", function(e) {
        var endx, endy;
        endx = e.changedTouches[0].pageX;
        endy = e.changedTouches[0].pageY;
        var direction = getDirection(startx, starty, endx, endy);
        switch (direction) {
            case 0:
                // alert("未滑动！");
                break;
            case 1:
                // alert("向上！")
                $scope.topLeftFlag = false
                break;
            case 2:
                // alert("向下！")
                $scope.topLeftFlag = false
                break;
            case 3:
                // alert("向左！")
                $scope.topLeftFlag = true
                break;
            case 4:
                // alert("向右！")
                $scope.topLeftFlag = true
                break;
            default:
        }
    }, false);

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


