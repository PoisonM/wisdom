angular.module('controllers',[]).controller('operationCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryAllTrainingProductDTO','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryAllTrainingProductDTO,ManagementUtil) {
            $scope.mum = true;
            $scope.loadPageList = function(){
                QueryAllTrainingProductDTO.save({
                    pageNo:1,
                    pageSize:5
                },function(data){
                    console.log(data);
                    ManagementUtil.checkResponseData(data,"");
                    console.log(data);
                    if(data.result == Global.SUCCESS){
                        $scope.rainingList = data.responseData.responseData;
                        $scope.mum = false;
                    }

                })
            };
            $scope.loadPageList();
            $scope.tabArray=[
                {"productFlag":[]},
                {"productFlag":[]},
                {"productFlag":[]},
                {"productFlag":[]}
            ];
            $scope.ab = function(num,type){
                for(var i=0;i<=num;i++){
                    $scope.tabArray[type].productFlag[i]=false;
                }
            };
            $scope.ab(1,2);
            $scope.ab(15,3);
            $scope.classShow = function(indexes,type,num){
                $scope.types=$scope.tabArray[type];
                $scope.types.productFlag[indexes]=!$scope.types.productFlag[indexes];
            };


            $scope.homeIndex =-1
            $scope.upAndDownWay = function(index,type){
                $scope.homeIndex = index;
                $scope.status = type;
                $scope.active = "active active_w";
                $scope.pageNo=1;
            };
        }]);