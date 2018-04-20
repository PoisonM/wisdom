function selectCouponsCtrl($scope,ngDialog){
    $scope.param.appearMoney="";
    $scope.selectCoupons = function(){
        $scope.ngDialog = ngDialog;
        ngDialog.open({
            template: 'selectCoupons',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                console.log($scope.$parent.content);
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'payType ngdialog-theme-custom'
        });
    }
    $scope.selectCouponsData = [
        {
            name:"项目券",
            money:"500.00",
            date:"2018.03-21-2020.03-21"
        },
        {
            name:"项目券",
            money:"300.00",
            date:"2018.04-8-2020.03-21"
        },
        {
            name:"项目券",
            money:"200.00",
            date:"2018.05-21-2020.03-21"
        }
    ];
    $scope.clickMoney=function (index) {
        // $scope.param.appearMoney=$scope.selectCouponsData[index].money;
    }
 }



