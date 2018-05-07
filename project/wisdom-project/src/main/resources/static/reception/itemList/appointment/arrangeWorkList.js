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
            searchDate:searchDate
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
    }






    var startx, starty;
    //获得角度
    function getAngle(angx, angy) {
        return Math.atan2(angy, angx) * 180 / Math.PI;
    };

    var tempTop = ""
    var tempLeft = ""
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

            /*$("#showTop").show()
            $("#showLeft").hide()
            /!*获取本次滑动距离*!/
            tempTop = $("#showTop .reality_content").scrollTop()
            /!*设置上次滑动距离*!/
            $("#showTop .reality_content").scrollLeft(tempLeft)
            $("#showTop .reality_content .float_top").scrollLeft(tempLeft)*/
            /*$(".content_info").css({"padding-top":"67px"})
            $(".float_top_temp").css({"position":"absolute","top":"0px"})*/


            $("#showTop .reality_content").css({"overflow-y":"scroll"})
            $("#showTop .reality_content").css({"overflow-x":"hidden"})
        } else if (angle > 45 && angle < 135) {
            result = 2;
            /*$("#showTop").show()
            $("#showLeft").hide()

            tempTop = $("#showTop .reality_content").scrollTop()
            $("#showTop .reality_content").scrollLeft(tempLeft)
            $("#showTop .reality_content").scrollLeft(tempLeft)
            $("#showTop .reality_content .float_top").scrollLeft(tempLeft)*/

            /*----测试----*/
           /* $(".content_info").css({"padding-top":"67px"})
            $(".float_top_temp").css({"position":"absolute","top":"0px"})*/

            $("#showTop .reality_content").css({"overflow-y":"scroll"})
            $("#showTop .reality_content").css({"overflow-x":"hidden"})
        } else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
            result = 3;

            /*$("#showTop").hide()
            $("#showLeft").show()
            tempLeft = $("#showLeft .reality_content").scrollLeft()
            $("#showLeft .reality_content").scrollTop(tempTop)
            $("#showLeft .reality_content").scrollTop(tempTop)
            $("#showTop .reality_content .float_left").scrollTop(tempTop)*/
           /* $(".content_info").css({"padding-top":"0px"})
            $(".float_top_temp").css({"position":"absolute","top":"0px"})*/

            $("#showTop .reality_content").css({"overflow-y":"hidden"})
            $("#showTop .reality_content").css({"overflow-x":"scroll"})
        } else if (angle >= -45 && angle <= 45) {
            result = 4;

            /*$("#showTop").hide()
            $("#showLeft").show()
            tempLeft = $("#showLeft .reality_content").scrollLeft()
            $("#showLeft .reality_content").scrollTop(tempTop)
            $("#showTop .reality_content .float_left").scrollTop(tempTop)*/


           /* $(".content_info").css({"padding-top":"0px"})
            $(".float_top_temp").css({"position":"absolute","top":"0px"})*/

            $("#showTop .reality_content").css({"overflow-y":"hidden"})
            $("#showTop .reality_content").css({"overflow-x":"scroll"})
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



    $scope.tempTime = $scope.param.nowdate.replace("年","-").replace("月","-1")
    $scope.goCompileWorkList = function () {
        $state.go("pad-web.compileWorkList",{time:$scope.tempTime})
    }







})


