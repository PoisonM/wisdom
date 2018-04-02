angular.module('controllers',[]).controller('peopleAccountsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserInfoDTOByParameters','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserInfoDTOByParameters,ManagementUtil) {
            $scope.accountLis = "";
            $scope.loadPageList = function(){
                QueryUserInfoDTOByParameters.get({
                    pageNo:$scope.pageNo,
                    pageSize:$scope.pageSize,
                    phoneAndIdentify: $scope.accountLis
                },function(data){
                    $scope.peopleAccounts = data.responseData.responseData;
                    $scope.response = {};
                    $scope.response.count = data.responseData.totalCount;
                    $scope.pageSize = 5;
                    $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                    $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                    $scope.accountLis = "";
                })
            };
            $scope.loginLis = function(){
                $scope.loadPageList()
            }
        }]);