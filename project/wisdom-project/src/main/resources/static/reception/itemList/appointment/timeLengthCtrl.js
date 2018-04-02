PADWeb.controller("timeLengthCtrl", function($scope, $state, $stateParams) {
   $scope.param = {
       timeLength:[1,1.5,2,2.5,3,3.5,4]
   }
   $scope.selectTimeLength = function(index){
       $scope.index = index
   }
})

