function balancePrepaid ($scope,ngDialog){
    $scope.ngDialog = ngDialog;
    $scope.balancePrepaid = function(){
        ngDialog.open({
            template: 'balancePrepaid',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.balancePrepaidDataPic = [];
                $scope.collectionCard = []
                var balancePrepaidData = {
                    "errorInfo":1,
                    "responseData":[
                        {
                            "map":1,
                            "amount":12222,
                            "discountDesc":"5折啦",
                            "imageUrl":"www.baudu.com",
                            "introduce":"好东西",
                            "name":"充值卡名字",
                            "shopRechargeCardId":"1"
                        },
                        {
                            "map":1,
                            "amount":12222,
                            "discountDesc":"打折啦",
                            "imageUrl":"www.baid.com",
                            "introduce":"发放",
                            "name":"名字的的的",
                            "shopRechargeCardId":"2"
                        }
                    ],
                    "result":"0x00001"
                };
                $scope.param.consumptionObj.balancePrepaidCtrlData =balancePrepaidData.responseData;
                for(var i=0;i<$scope.param.consumptionObj.collectionCardByShowId.length;i++){
                    $scope.balancePrepaidDataPic[i] = 'images/bt_Single%20election_nor_.png';
                    $scope.collectionCard[i] = true;
                }


                $scope.close = function() {
                    $scope.closeThisDialog();
                };
                /*$scope.showBalancePrepaidPic = function (index) {
                    if($scope.collectionCard[index] == true){
                        $scope.balancePrepaidDataPic[index]='images/bt_Single%20election_select.png';
                        $scope.collectionCard[index] = false;
                    }else{
                        $scope.balancePrepaidDataPic[index]='images/bt_Single%20election_nor_.png';
                        $scope.collectionCard[index] = true;

                    }
                }*/
            }],
            className: 'payType ngdialog-theme-custom'
        });
    }


}
