PADWeb.controller('personalFileCtrl', function($scope, $stateParams, ngDialog) {
    $scope.select = 0;
    $scope.tabclick = function(e) {
        $scope.select = e;
    }
    //这边引入include
    basicInfo && basicInfo($scope);
});