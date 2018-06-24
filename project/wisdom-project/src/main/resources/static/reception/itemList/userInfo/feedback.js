PADWeb.controller('feedbackCtrl', function($scope,$state, $stateParams,SuggestionDetail) {

    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="意见反馈"
    $scope.$parent.param.headerCash.backContent="取消"
    $scope.$parent.param.headerCash.leftTip="保存"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = true
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true
    // 提交反馈
    $scope.$parent.$parent.leftTipFn = function () {
        console.log($scope.param.suggestion);
        if(undefined != $scope.param.suggestion|| $scope.param.suggestion!=""){
            SuggestionDetail.get({suggestion:$scope.param.suggestion},function(data){
                if('0x00001'==data.result)
                {
                    alert("提交成功");
                    $state.go("pad-web.userInfo.todayPerformance")
                }
            })
        }else{
            alert("内容不能为空，请输入建议内容");
        }
        $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = false;
        $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    };

});