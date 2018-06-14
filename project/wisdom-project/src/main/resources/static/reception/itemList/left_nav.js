PADWeb.controller("left_navCtrl", function($scope,$rootScope, $state,$stateParams, FindArchives) {
    console.log("left_navCtrl")
    $scope.mainLeftSwitch = {
        peopleListFlag: false,
        priceListFlag: true
    }
    $scope.$parent.mainSwitch.footerBoxFlag = true
    $rootScope.recordNum=""

    //获取档案列表
    $scope.queryRecordList = function() {
        FindArchives.get({
            queryField: $scope.param.queryField,
            pageSize: "100"
        }, function(data) {
            if (data.result == "0x00001") {
                $scope.dataList = [];
                $scope.info = data.responseData.info
                $rootScope.recordNum = data.responseData.data
                $scope.$parent.param.headerCash.leftContent = "档案("+data.responseData.data+")"
            }
        })
    }

    $scope.param = {
        selectSty: "",
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

    $scope.selectSty = function (id,sysUserId,shopid,sysShopId) {
        $scope.param.selectSty = sysUserId
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