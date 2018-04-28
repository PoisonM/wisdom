function balancePrepaid ($scope,ngDialog,GetRechargeCardList){
    $scope.ngDialog = ngDialog;
    $scope.balancePrepaid = function(){
        ngDialog.open({
            template: 'balancePrepaid',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.balancePrepaidDataPic = [];
                $scope.collectionCard = [];
                GetRechargeCardList.get({
                    name:"",
                    pageSize:100
                },function(data){
                    $scope.param.consumptionObj.balancePrepaidCtrlData =data.responseData;
                    for(var i=0;i<$scope.param.consumptionObj.collectionCardByShowId.length;i++){
                        $scope.balancePrepaidDataPic[i] = 'images/bt_Single%20election_nor_.png';
                        $scope.collectionCard[i] = true;
                    }
                });
                console.log($scope.balancePrepaidDataPic);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
                $scope.showBalancePrepaidPic = function (index) {
                    if($scope.collectionCard[index] == true){
                        $scope.balancePrepaidDataPic[index]='images/bt_Single%20election_select.png';
                        $scope.collectionCard[index] = false;
                    }else{
                        $scope.balancePrepaidDataPic[index]='images/bt_Single%20election_nor_.png';
                        $scope.collectionCard[index] = true;

                    }
                }
            }],
            className: 'payType ngdialog-theme-custom'
        });
    }


}
