angular.module('controllers',[]).controller('balanceCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryUserBalanceByParameters','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryUserBalanceByParameters,ManagementUtil) {
            $scope.BalanceAounnt = "";
            $scope.mum = true;
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
/*展示*/
            $scope.loadPageList = function(){

                        if($scope.BalanceAounnt!=""){
                            if(pattern.test($scope.BalanceAounnt) == false && pattern1.test($scope.BalanceAounnt)== false){
                                $scope.BalanceAounnt='请填写正确的手机号或身份证号';
                                return
                        }}

                            QueryUserBalanceByParameters.get({
                                phoneAndIdentify:$scope.BalanceAounnt,
                                pageNo:$scope.pageNo,
                                pageSize:$scope.pageSize,
                                isExportExcel:"N"
                            },function(data){
                                ManagementUtil.checkResponseData(data,"");
                                if(data.result == Global.SUCCESS){
                                    $scope.balanceLis = data.responseData.responseData;
                                    $scope.response = {};
                                    $scope.response.count = data.responseData.totalCount;
                                    $scope.pageSize = 5;
                                    $scope.param.pageFrom = ($scope.pageNo-1)*$scope.pageSize+1;
                                    $scope.param.pageTo = ($scope.pageNo-1)*$scope.pageSize+$scope.pageSize;
                                    for(var i=0;i<$scope.balanceLis.length;i++){
                                        $scope.balanceLis[i].userType = $scope.balanceLis[i].userType.substring(9,10)+"级";
                                    }
                                    /* $scope.BalanceAounnt = "";*/
                                    $scope.mum = false
                                }
                            })

            };
/*搜索*/

            $scope.searchBalance = function(){
                $scope.loadPageList()
                $scope.choosePage(1)
                }


//导表
            $scope.educeLis = function(){
                if (confirm("确认要导出？")) {

                   /* $scope.statusGet();
                    var PageParamVoDTO ={
                        param:$scope.BalanceAounnt,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        isExportExcel:"Y"
                    }*/
                    QueryUserBalanceByParameters.get({ phoneAndIdentify:$scope.BalanceAounnt,
                        pageNo:$scope.pageNo,
                        pageSize:$scope.pageSize,
                        isExportExcel:"Y"},function(data){
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
        }]);