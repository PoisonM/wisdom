PADWeb.controller('cardRecordsCtrl', function($scope,$state, $stateParams,GetCompleteByflowId) {

    GetCompleteByflowId.get({
        id:$stateParams.id
    },function (data) {
        if(data.result == "0x00001"){
            $scope.dataInfo = data.responseData
            console.log(  $scope.dataInfo )
        }
    })
})
