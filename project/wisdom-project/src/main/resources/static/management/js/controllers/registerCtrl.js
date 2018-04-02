angular.module('controllers',[]).controller('registerCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','ManagerLogin',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,ManagerLogin) {

    var pattern = /^1[34578]\d{9}$/;

    $scope.param = {
        userAccount : '',
        userPassword:"",
        verifyMess:false
    };

    $scope.login = function() {
        if(pattern.test($scope.param.userAccount) == false || $scope.param.userPassword==""){
            alert("请检查信息");
            return
        }
        ManagerLogin.save({userPhone: $scope.param.userAccount, code: $scope.param.userPassword}, function (data) {
            if(data.result==Global.FAILURE)
            {
                alert(data.errorInfo);
            }
            else {
                window.localStorage.removeItem("logintoken");
                window.localStorage.setItem("logintoken", data.responseData);

                if ($stateParams.redirectUrl == ''||$stateParams.redirectUrl ==undefined) {
                    window.location.href = "";
                }
                else {
                    window.location.href = "#/" + $stateParams.redirectUrl.replace("&", "/");
                }
            }

        })

    }
}]);