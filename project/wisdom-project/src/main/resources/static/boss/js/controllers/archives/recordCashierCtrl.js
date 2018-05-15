angular.module('controllers',[]).controller('recordCashierCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','$ionicPopup','Consumes','Global',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,$ionicPopup,Consumes,Global) {
            $rootScope.title = "收银记录";
           /* $scope.showAlert = function() {
                var alertPopup = $ionicPopup.alert({
                    title:"提示",
                    template: '<p class="fs17 col333 center">起始时间不能大于结束时间</p>'

                });
                alertPopup.then(function(res) {
                    console.log('Thank you for not eating my delicious ice cream cone');
                });
            };*/

            $scope.detailsOfCashierGo=function () {
                $state.go("detailsOfCashier")
            }

            $scope.userConsumeRequest = {
                consumeType:'2',
                goodType:"6",
                pageSize:1000,
                shopUserId:"11"

            }
            Consumes.save($scope.userConsumeRequest,function(data){
                if(data.result==Global.SUCCESS&&data.responseData!=null){
                    $scope.recordCashier =data.responseData
                }
            })
        }]);
