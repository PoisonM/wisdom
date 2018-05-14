angular.module('controllers',[]).controller('outboundCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "出库";
            $scope.param = {
                flag: false,
                type: 0, /*客装产品  易耗品*/
                selType: '2', /*扫码出库  手动出库*/
                ids: []/*出库产品*/
            };
            $scope.changeBtn = function (type) {
                $scope.param.type = type;
            };
            $scope.selNext = function () {
                $scope.param.flag = true;
            };
            $scope.all = function () {
                $scope.param.flag = false;
            };
            $scope.selThree = function () {

            };
            $scope.threeMess = function () {
                $scope.param.flag = false;
            }
            $scope.selType = function (type) {
                $scope.param.selType = type;
            }

            $scope.selProduct = function (domIndex) {
                if ($scope.param.ids.indexOf(domIndex) != -1) {
                    var key = 0;
                    angular.forEach($scope.param.ids, function (val, index) {
                        if (val == domIndex) {
                            $scope.param.ids.splice(key, 1);
                        }
                        key++;
                    })
                } else {
                    $scope.param.ids.push(domIndex);
                }
            }
           /*出库记录*/
            $scope.outboundRecordsGo = function(){
                $state.go("outboundRecords")
            }
            /*下一步*/
            $scope.AddOutboundGo = function(){
                $state.go("AddOutbound",{stockStyle:$scope.param.selType})
            }

        }])