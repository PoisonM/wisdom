PADWeb.controller('personalFileCtrl', function($scope,$rootScope,$stateParams, $state, ngDialog, Archives, GetShopUserArchivesInfoByUserId, GetUserCourseProjectList, GetUserProjectGroupList, GetUserRechargeCardList, GetUserProductList, GetUserRechargeSumAmount, ArchivesDetail) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    // $scope.$parent.$parent.param.headerCash.leftContent = "档案(9010)";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.title = "用户档案";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = false;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
    $scope.flagFn = function(bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = bool;
        $scope.$parent.mainLeftSwitch.priceListFlag = !bool;
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.leftFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.middleFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerCashFlag.rightFlag = bool;
    }
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true)

    console.log($stateParams.sysUserId);

    $scope.queruList = function(type) {
        if (type == 0) { //疗程卡
            GetUserCourseProjectList.get({
                sysUserId: $stateParams.sysUserId,
                cardStyle: 1,
                surplusTimes:1
            }, function(data) {
                if (data.result == "0x00001") {
                    console.log("疗程卡:" + data)
                    $scope.courseProjectList0 = data.responseData
                }
            })
        } else if (type == 1) { //套卡
            GetUserProjectGroupList.get({
                sysUserId: $stateParams.sysUserId
            }, function(data) {
                if (data.result == "0x00001") {
                    console.log("套卡:" + data)
                    $scope.projectGroupList = data.responseData
                }
            })
        } else if (type == 2) { //充值卡
            //获取充值卡金额
            GetUserRechargeSumAmount.get({
                sysUserId: $stateParams.sysUserId
            }, function(data) {
                if (data.result = "0x00001") {
                    $scope.sunAmount = data.responseData
                }
            })

            GetUserRechargeCardList.get({
                sysUserId:$stateParams.sysUserId
            }, function(data) {
                if (data.result == "0x00001") {
                    console.log("充值卡:" + data)
                    $scope.rechargeCardList = data.responseData.userRechargeCardList;
                }
            })

        } else if (type == 3) { //单次
            GetUserCourseProjectList.get({
                sysUserId: $stateParams.sysUserId,
                cardStyle: 0
            }, function(data) {
                if (data.result == "0x00001") {
                    console.log("单次:" + data)
                    $scope.courseProjectList1 = data.responseData
                }
            })
        } else if (type == 4) { //产品
            GetUserProductList.get({
                sysUserId: $stateParams.sysUserId,
                surplusTimes :'1'
            }, function(data) {
                if (data.result == "0x00001") {
                    console.log("产品:" + data)
                    $scope.productList = data.responseData
                }
            })
        }

    }

    $scope.select = 4;
    $scope.tabclick = function(e) {
        $scope.select = e;
        $scope.queruList(e)
    }
    $scope.tabclick(0);

    $scope.goConsumptionList = function(type, id) {
        $state.go('pad-web.left_nav.drawCardConsumption', { type: type, id: id });
    };

    $scope.goGetProduct = function(id) {
        $state.go('pad-web.left_nav.getProduct', { id: id })
    }

    $scope.goCureCardRecords = function (id) {
        $state.go('pad-web.left_nav.cureCardRecords',{id:id,userId:$stateParams.sysUserId})
    }

    //点击充值卡跳转
    $scope.goRechargeCardRecords = function (id,index,sunAmount) {
        if(index == 0){//特俗充值卡
            $state.go('pad-web.left_nav.featureRechargeCardRecords',{
                userId:$stateParams.sysUserId,
                id:id,
                sunAmount:sunAmount
            })
        }else{
            // $state.go('pad-web.left_nav.rechargeCardRecords',{userId:$stateParams.sysUserId})
            $state.go('pad-web.left_nav.rechargeCardDetail',{
                userId:$stateParams.sysUserId,
                id:id,
            })
        }
    }

    //套卡
    $scope.goCompleteCardRecords = function (consumeRecordId,projectList) {
        $scope.tempStr = ""
        for(var i = 0; i < projectList.length; i++){
            $scope.tempStr+=projectList[i].id+","
        }
        $state.go('pad-web.left_nav.completeCardRecords',{
            ids:$scope.tempStr,
            consumeRecordId:consumeRecordId,
            userId:$stateParams.sysUserId
        })
    }
    //产品
    $scope.goGetProductRecord = function (id) {
        $state.go("pad-web.left_nav.getProductRecord",{
            userId:$stateParams.sysUserId,
            id:id
        })
    }



    //这边引入include
    basicInfo && basicInfo($scope, $state, $stateParams,Archives, ArchivesDetail,$rootScope);
});