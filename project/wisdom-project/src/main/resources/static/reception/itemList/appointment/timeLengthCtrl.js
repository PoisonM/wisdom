
function selectTimeLengthCtrl ($scope,ngDialog){

    /*选择时长*/
    $scope.selectTimeLength = function(){
        ngDialog.open({
            template: 'timeLength',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.timeLengthIndex = -1;
                $scope.param.timeLengthObject.time ='';
                $scope.param.timeLengthArr=["1","1.5","2","2.5","3","3.5","4"];

                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'timeLength ngdialog-theme-custom'
        });
    };
    /*选择美容师*/
    $scope.selectBeautician = function(){
        ngDialog.open({
            template: 'selectBeautician',
            scope: $scope, //这样就可以传递参数
            controller: ['$scope', '$interval', function($scope, $interval) {
                $scope.param.timeLengthIndex = -1;
                $scope.param.timeLengthObject.time = ''
                $scope.param.timeLengthArr = ["李","网",'周'];
                $scope.close = function() {
                    $scope.closeThisDialog();
                };
            }],
            className: 'selectBeautician ngdialog-theme-custom'
        });
    }
    $scope.selectTimeLengthIndex = function(index,item){
        $scope.param.timeLengthIndex = index;
        $scope.param.timeLengthObject.time = item;

    }

}

