PADWeb.controller('housekeeperCtrl', function($scope, $rootScope,$stateParams, ngDialog, GetShopClerkList) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "shouyin";
    $scope.$parent.$parent.param.headerCash.leftAddContent = "添加档案";
    $scope.$parent.$parent.param.headerCash.backContent = "返回";
    $scope.$parent.$parent.param.headerCash.leftTip = "完成";
    $scope.$parent.$parent.param.headerCash.title = "关联员工";
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.middleFlag = true;
    $scope.$parent.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true;
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


    $rootScope.staffListIds = []
    $rootScope.staffListNames = []
    $scope.housekeeperCheck = function(index,id,name) {
        // $scope.select = index;
        if($rootScope.staffListIds.indexOf(id) != -1){
            $rootScope.staffListIds.remove(id)
            $rootScope.staffListNames.remove(name)
        }else {
            $rootScope.staffListIds.push(id)
            $rootScope.staffListNames.push(name)
        }
    }

    GetShopClerkList.get({
        pageNo: "1",
        pageSize: "100"
    }, function(data) {
        if (data.result == "0x00001") {
            $scope.UserList = data.responseData
        }
    })
    
    $scope.$parent.$parent.leftTipFn = function () {
        window.history.go(-1)
    }
    
});


/*
 * 方法:Array.remove(dx) 通过遍历,重构数组
 * 功能:删除数组元素.
 * 参数:dx删除元素的下标.
 */
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i]
        }
    }
    this.length-=1
}