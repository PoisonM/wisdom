PADWeb.controller("left_navCtrl", function($scope, $state,FindArchives) {
    console.log("left_navCtrl")
    $scope.mainLeftSwitch = {
        peopleListFlag:false,
        priceListFlag:true
    }



    $scope.param = {
        selectSty:"1",
        priceType:"xm"
    }


    /*获取档案列表*/
    FindArchives.get({
        queryField:"",
        pageNo:"1",
        pageSize:"1"
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataList = [];
            $scope.info = data.responseData.info
        }
    })

    /*-------------------------------方法-------------------------------------------*/
    //
    //价目表切换
    $scope.selectPriceType = function (type) {
        $scope.param.priceType = type
        if(type == 'xm'){
            $state.go("pad-web.left_nav.project");
        }else if(type == 'cp'){
            $state.go("pad-web.left_nav.product");
        }else if(type == 'tk'){
            $state.go("pad-web.left_nav.rechargeableCard");
        }else if(type == 'czk'){
            $state.go("pad-web.left_nav.setCard");
        }
    }
})