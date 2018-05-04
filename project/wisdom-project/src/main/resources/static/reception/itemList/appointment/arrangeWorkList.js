PADWeb.controller("arrangeWorkListCtrl", function($scope, $state, $stateParams,GetShopClerkScheduleList) {
    $scope.$parent.param.top_bottomSelect = "yuyue";
    $scope.$parent.mainSwitch.headerCashAllFlag = false;

    $scope.tempWeek = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]
    $scope.tempUser = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25]


    $scope.topLeftFlag = false


    GetShopClerkScheduleList.get({
        searchDate:"2018-04-28"
    },function (data) {
        if(data.result == "0x00001"){
            /*$scope.tempWeek = data.responseData.clerkInfo
            $scope.tempUser = data.responseData.responseList*/
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
                break;
            case 2:
                // alert("向下！")
                break;
            case 3:
                // alert("向左！")
                break;
            case 4:
                // alert("向右！")
                break;
            default:
        }
    }, false);












})


