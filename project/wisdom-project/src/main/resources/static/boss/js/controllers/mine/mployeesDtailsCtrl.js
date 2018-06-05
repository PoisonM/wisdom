angular.module('controllers',[]).controller('mployeesDtailsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading','ClerkInfo','UpateClerkInfo',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading,ClerkInfo,UpateClerkInfo) {
            $scope.param={
                beauticianStatus:false,
                managerStatus:false,
                fontDeskStatus:false
            };
            ClerkInfo.query({
                clerkId:$stateParams.id
            },function (data) {
                $scope.mployeesDtails = data[0]
                $scope.decideStatus("美容师",'beauticianStatus')
                $scope.decideStatus("店员",'managerStatus')
                $scope.decideStatus("前台",'fontDeskStatus')

            })
            $scope.decideStatus = function (text,attribute) {
                if($scope.mployeesDtails.role.indexOf(text)!=-1){
                    $scope.param[attribute] = true
                }else{
                    $scope.param[attribute] = false
                }
            }

            $scope.save = function () {
                $scope.mployeesDtails.role =[]
                if ($scope.param.beauticianStatus == true) {
                    $scope.mployeesDtails.role += "美容师 "

                }
                if ($scope.param.managerStatus == true) {
                    $scope.mployeesDtails.role += "店员 "

                }
                if ($scope.param.fontDeskStatus == true) {
                    $scope.mployeesDtails.role += "前台 "

                }
                $scope.mployeesDtails.role = $scope.mployeesDtails.role.slice(0, $scope.mployeesDtails.role.length - 1);
                var sysClerkDTO =  $scope.mployeesDtails
                UpateClerkInfo.save(sysClerkDTO,function (data) {
                     if(data.result=="0x00001"){
                             $state.go('addFamily')
                     }
                })
            }

        }])