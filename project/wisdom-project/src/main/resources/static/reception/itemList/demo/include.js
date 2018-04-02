/*PADWeb.controller('demoCtrl', function($scope, $stateParams, ngDialog) {
    $scope.num = "";
    $scope.heihei = "123";
    $scope.clickInclude = function(num) {
        $scope.num = num
        console.log("2" + $scope.num);
    }
});*/


function include(e) {
    e.heihei = "123";
    e.haha = 'change'
}