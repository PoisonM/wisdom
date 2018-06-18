PADWeb.controller('unclaimedAllClientCtrl', function($scope, $stateParams
    , ngDialog,Consumes) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.$parent.param.headerCash.title="领取记录"

    Consumes.save({
        goodsType:4,
        consumeType:1,
        pageSize:12
    },function (data) {
        $scope.dataList = data.responseData
    })
});