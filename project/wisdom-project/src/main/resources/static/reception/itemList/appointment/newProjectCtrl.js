function selectProductCtrl($scope,ngDialog){
    /*选择项目*/
    $scope.selectProduct = function(){
        ngDialog.open({
            template: 'newProduct',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'newProject ngdialog-theme-custom'
        });

    }
    $scope.newProductBtn = function(index){
        $scope.param.newProductObject.index =index;
        if(index == 1){
            $scope.param.newProductObject.titleFlag = true;
        }else{
            $scope.param.newProductObject.titleFlag = false;
        }
    }

}