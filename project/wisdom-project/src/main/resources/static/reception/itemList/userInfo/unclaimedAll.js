PADWeb.controller('unclaimedAllCtrl', function($scope, $stateParams, ngDialog,GetProductRecord) {
    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="待领取汇总"
    console.log('待领取汇总');
    /*-----------------------------------------------------------------------------------------------------*/
    GetProductRecord.get({
        searchFile:"22",
        sysClerkId:"18035609334"
    },function () {

    })
});