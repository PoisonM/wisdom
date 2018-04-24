function selectCouponsCtrl($scope){
    $scope.param.appearMoney="";
    $scope.selectCoupons = function(){
        $scope.param.selectCoupons = true;
        $scope.param.consumptionNextStep = false;
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



