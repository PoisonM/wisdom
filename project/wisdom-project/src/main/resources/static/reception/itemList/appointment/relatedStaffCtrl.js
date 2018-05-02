function relatedStaffCtrl ($scope,ngDialog){
    $scope.relatedStaff = function(index){
        $scope.relatedAtaffIndex = index
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'relatedStaff',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.param.scratchCardObj.scratchCardData)
                $scope.getShopClerkList($scope.param.scratchCardObj.scratchCardData,'sysClerkName');
                $scope.close = function(type) {
                    if(type==1){
                       /* $scope.relatedAtaff.index
                        for(var i=0;i<$scope.param.relatedAtaffData.length;i++){
                            if($scope.relatedAtaffFlag[i]==true){

                            }
                        }*/

                    }
                    /*$scope.closeThisDialog();*/
                };
            }],
            className: 'scratchCard ngdialog-theme-custom'
        });
    }
}

