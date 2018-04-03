/**
 * Created by Administrator on 2018/3/29.
 */
PADWeb.controller("projectCtrl", function($scope, $state, $stateParams) {
    $scope.param = {
        selection:"0"
    }
    $scope.selection  = function (index,$event) {
        $scope.param.selection = index
        $event.stopPropagation();
    }
    $scope.selectSingleData = [0,1,0,0,0,0,0,0,0,0];
    $scope.selectSingleDataPic =[];
    var single =[];
    for(var i=0;i<$scope.selectSingleData.length;i++){
        $scope.selectSingleDataPic[i] = 'images/check2.png';
        single[i] = true;
    }
    $scope.projectAppear=false;
    $scope.checkImg = function(index,$event) {
        if (single[index] == true) {
            $scope.selectSingleDataPic[index] = 'images/check4.png';
            single[index] = false;
           $scope.projectAppear=true;
        } else {
            $scope.selectSingleDataPic[index] = 'images/check2.png';
            single[index] = true;
            $scope.projectAppear=false;
        }
        $event.stopPropagation();

    }
})