angular.module('controllers',[]).controller('operationCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryTrainingProductsByParameters','ManagementUtil','DelProductById','PutAwayProductById',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryTrainingProductsByParameters,ManagementUtil,DelProductById,PutAwayProductById) {
            $scope.mum = true;
            $scope.productName = ""
            $scope.price = ""
            $scope.loadPageList = function(){
                 $scope.PageParamVoDTO ={
                    pageNo:$scope.pageNo,
                    pageSize:$scope.pageSize,
                    requestData:{
                        status: $scope.status,
                        productName:$scope.productName,
                        price:$scope.price,
                        type:"training"
                    },
                    isExportExcel:"N"
                };
                QueryTrainingProductsByParameters.save($scope.PageParamVoDTO,function(data){
                    ManagementUtil.checkResponseData(data,"");
                    if(data.errorInfo == Global.SUCCESS){
                        $scope.rainingList = data.responseData.responseData;
                        for(var i=0;i<$scope.rainingList.length;i++){
                            if($scope.rainingList[i].status == '1'){
                                $scope.rainingList[i].status ="上架"
                            }else if($scope.rainingList[i].status == '0'){
                                $scope.rainingList[i].status ="下架"
                            }
                        }
                        $scope.mum = false;
                        $scope.counnt='';
                        $scope.response = {};
                        $scope.response.count = data.responseData.totalCount;
                        $scope.pageSize = 5;
                        $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                        $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                    }

                })
            };
            $scope.show = function(){
                $scope.loadPageList()
                $scope.choosePage(1)
            };
            $scope.educeLis = function(){
                if (confirm("确认要导出？")) {
                    $scope.PageParamVoDTO ={
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        requestData:{
                            status: $scope.status,
                            productName:$scope.productName,
                            price:$scope.price,
                            type:"training"
                        },
                        isExportExcel:"Y"
                    };
                    QueryTrainingProductsByParameters.save($scope.PageParamVoDTO,function(data){
                        ManagementUtil.checkResponseData(data,"");
                        if(data.errorInfo == Global.SUCCESS){
                            var $eleForm = $("<form method='get'></form>");
                            $eleForm.attr("action",data.result);
                            $(document.body).append($eleForm);
                            $eleForm.submit();
                            $scope.loadPageList();
                        }
                    })
                }
            }




            $scope.homeIndex =-1;
            $scope.upAndDownWay = function(index,type){
                $scope.productName = ""
                $scope.price = ""
                $scope.homeIndex = index;
                $scope.status = type;
                $scope.active = "active active_w";
                $scope.pageNo=1;
                $scope.loadPageList()
            };
            /*状态的改变*/
            $scope.upAndDown = function(id,type,status){
                if(type == status)return;
                if(type=="下架"){
                    if (confirm("确认商品下架？")) {
                        DelProductById.get({
                            id:id
                        },function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.errorInfo == Global.SUCCESS){
                                $scope.loadPageList()
                            }
                        })
                    }
                }else{
                    if (confirm("确认商品上架？")) {
                        PutAwayProductById.get({
                            id:id
                        },function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.errorInfo == Global.SUCCESS){
                                $scope.loadPageList()
                            }
                        })
                    }

                }
            }
        }]);