angular.module('controllers', []).controller('scanPayCtrl',
    ['$scope', '$interval', '$rootScope', '$stateParams', '$state', 'Global', '$timeout', 'PayOrder',
        function ($scope, $interval, $rootScope, $stateParams, $state, Global, $timeout, PayOrder) {
            console.log("scanPayCtrlCtrl")
            var qrcode = new QRCode(document.getElementById("qrcode"), {
                width: 100,
                height: 100
            });
            PayOrder.get(function (data) {
                $scope.qrcodeUrl = data.result;
                if (!$scope.qrcodeUrl) {
                    alert("二维码生成失败");
                    return;
                }
                qrcode.makeCode($scope.qrcodeUrl);
            })
        }]);