angular.module('controllers',[]).controller('homePageEditorCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,) {

             $scope.show = false;

             $scope.createImage = function(){
                $scope.go();
             }

             $scope.detailPageList = function () {
                    var page = {
                        pageNo:$scope.pageNum,
                        pageSize:$scope.pageSize,
                        requestData:{
                        },
                        isExportExcel:"N",
                    };
                   /* FindNextUserInfoControl.save(page,function(data){

                        ManagementUtil.checkResponseData(data,"");

                        if(data.result == Global.SUCCESS){
                            $scope.param.recommend = data.responseData.responseData;
                            for(var i =0;i<$scope.param.recommend.length;i++){
                                if( $scope.param.recommend[i].userInfoDTO.userType !=null){
                                    $scope.param.recommend[i].userInfoDTO.userType = $scope.param.recommend[i].userInfoDTO.userType.substring(9,10)+"级";
                                }
                            }

                            $scope.count = data.responseData.totalCount;
                            if(data.responseData.totalCount == 0){
                                data.responseData.totalCount=1;
                            }
                            if($scope.pageNum>=Math.ceil($scope.count/$scope.pageSize)){
                                $scope.hint="none"
                            }
                            $scope.mum = false;
                        }else{
                            $scope.count = 1;
                        }
                    });*/
                };

        }]);
