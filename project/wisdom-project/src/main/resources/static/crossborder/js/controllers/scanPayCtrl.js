angular.module('controllers', []).controller('scanPayCtrl',
    ['$scope', '$interval', '$rootScope', '$stateParams', '$state', 'Global', '$timeout', 'PayOrder','CheackOrderStatus',
        function ($scope, $interval, $rootScope, $stateParams, $state, Global, $timeout, PayOrder,CheackOrderStatus) {
            console.log("scanPayCtrlCtrl")
            var qrcode = new QRCode(document.getElementById("qrcode"), {
                width: 100,
                height: 100
            });
            PayOrder.get(function (data) {
                $scope.qrcodeUrl = data.responseData.codeUrl;
                $scope.transactionId = data.responseData.transactionId;
                $scope.payPrice = data.responseData.payPrice;
                if (!$scope.qrcodeUrl) {
                    alert("二维码生成失败");
                    return;
                }else{
                    qrcode.makeCode($scope.qrcodeUrl);
                    if( $scope.transactionId  !=null && $scope.transactionId  !=''){
                        $scope.timer = setInterval(function () {
                            //检测用户是否支付
                            CheackOrderStatus.get({transactionId:$scope.transactionId },function (data) {
                                console.log(data)
                                if(data.responseData == "success"){
                                    clearInterval($scope.timer)
                                    alert("支付成功");
                                    $state.go("orderList");
                                }
                            })
                        },1000)
                    }
                }
            })
        }]);