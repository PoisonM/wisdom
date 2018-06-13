PADWeb.controller("left_navCtrl", function($scope, $state,$stateParams, FindArchives) {
    console.log("left_navCtrl")
    $scope.mainLeftSwitch = {
        peopleListFlag: false,
        priceListFlag: true
    }


    //获取档案列表
    $scope.queryRecordList = function() {
        FindArchives.get({
            queryField: $scope.param.queryField,
            pageSize: "10"
        }, function(data) {
            if (data.result == "0x00001") {
                $scope.dataList = [];
                $scope.info = data.responseData.info
            }
        })
    }

    $scope.param = {
        selectSty: "1",
        priceType: "xm",
        queryField: ""
    }


    /*获取档案列表*/
    $scope.queryRecordList()

    /*--------------------------------方法-------------------------------------------*/
    $scope.cancelSearch = function() {
        $scope.param.queryField = ""
        $scope.queryRecordList()
    }

    $scope.searchRecord = function() {
        $scope.queryRecordList()
    }


    var timeIn = setInterval(function () {
        if($(".user_info").length!=0){
            // $(".user_info").eq(0).trigger("click");
            clearInterval(timeIn)
        }
     },100)
    $scope.selectSty = function (id,shopid,sysShopId,sysUserId) {
        $scope.param.selectSty = id
        $state.go("pad-web.left_nav.personalFile",{
            id:id,
            shopid:shopid,
            sysShopId:sysShopId,
            sysUserId:sysUserId
        })
    }


    //价目表部分的切换
    $scope.selectPriceType = function(type) {
        $scope.param.priceType = type
        if (type == 'xm') {
            $state.go("pad-web.left_nav.project");
        } else if (type == 'cp') {
            $state.go("pad-web.left_nav.product");
        } else if (type == 'tk') {
            $state.go("pad-web.left_nav.rechargeableCard");
        } else if (type == 'czk') {
            $state.go("pad-web.left_nav.setCard");
        }
    }
})