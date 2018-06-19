PADWeb.controller('feedbackCtrl', function($scope, $stateParams,SuggestionDetail) {

    /*-------------------------------------------定义头部信息----------------------------------------------*/
    $scope.$parent.param.headerCash.title="意见反馈"
    $scope.$parent.param.headerCash.backContent="取消"
    $scope.$parent.param.headerCash.leftTip="提交"
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.leftBackFlag = false
    $scope.$parent.mainSwitch.headerCashFlag.headerCashRightFlag.rightFlag = true


    console.log("意见反馈");
    // $scope.$parent.$parent.leftTipFn = function () {
    //     alert("提交成功")
    // }

    // 提交反馈
    $scope.$parent.$parent.leftTipFn = function () {
        console.log($scope.param.suggestion);
        if($scope.param.suggestion!=""){
            SuggestionDetail.get({suggestion:$scope.param.suggestion},function(data){
                if('0x00001'==data.result)
                {
                    alert("提交成功");
                }
            })
        }else{
            alert("内容不能为空，请输入建议内容");
        }

    };
});