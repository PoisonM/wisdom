angular.module('controllers',[]).controller('recordCashierCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','$ionicPopup',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,$ionicPopup) {
            $rootScope.title = "收银记录";
            $scope.showAlert = function() {
                var alertPopup = $ionicPopup.alert({
                    title:"提示",
                    template: '<p class="fs17 col333 center">起始时间不能大于结束时间</p>'

                });
                alertPopup.then(function(res) {
                    console.log('Thank you for not eating my delicious ice cream cone');
                });
            };
            $scope.showAlert();
            $scope.detailsOfCashierGo=function () {
                $state.go("detailsOfCashier")
            }
        }]);
