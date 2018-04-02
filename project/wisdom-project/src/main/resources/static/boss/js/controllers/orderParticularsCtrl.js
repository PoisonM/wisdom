angular.module('controllers',[]).controller('orderParticularsCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','QueryOrderDetailsById','UpdateOrderAddress','ManagementUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,QueryOrderDetailsById,UpdateOrderAddress,ManagementUtil) {
            $scope.mum = true;
            var changeReadOnly = document.querySelectorAll(".uploading_product_name input[readonly='readonly']");
            $scope.amend = function(){
                for(var i=0;i<changeReadOnly.length;i++){
                      changeReadOnly[i].removeAttribute("readonly")
                }
                document.form1.userName.focus()
               /* document.form1.userName.select()*/
            };
            $scope.back = function(){
                $state.go("order")
            };
            $scope.statusFlag=true;
 /*获得详情信息*/
            QueryOrderDetailsById.get({
                orderId:$stateParams.id
            },function(data){
                ManagementUtil.checkResponseData(data,"");
                if(data.errorInfo == Global.SUCCESS){
                      if(data.responseData.status){
                          if(data.responseData.status == "1"){
                              $scope.status ="付款时间";
                              $scope.statusFlag = false;
                          }else if(data.responseData.status == "2"){
                              $scope.status ="完成时间:";
                              $scope.statusFlag = true;
                          }else if(data.responseData.status == "4"){
                              $scope.status ="发货时间:";
                              $scope.statusFlag = true;
                          }else{
                              $scope.statusFlag = false;
                          }
                      }
                      if(data.responseData.type=="training"){
                          data.responseData.type = "视频"
                      }else{
                          data.responseData.type="线上产品"
                      }
                      if(data.responseData.userAddress){
                          var address = data.responseData.userAddress;
                          $scope.userAddress = data.responseData.userAddress.split(" ");
                      }
                      $scope.orderParticulars = data.responseData;
                      $scope.mum = false;

                }
                $scope.submit = function(){
                    if($scope.orderParticulars.userOrderAddressId){
                        var pattern = /^1[34578]\d{9}$/;
                        if(pattern.test($scope.orderParticulars.userPhoneAddress) == false){
                            $scope.orderParticulars.userPhoneAddress='请填写正确的手机号';
                            return;
                        }
                        if($scope.orderParticulars.userNameAddress==''||$scope.orderParticulars.userPhoneAddress==''||$scope.userAddress[0]==''||$scope.userAddress[2]==''){
                            alert("请检查信息");
                            return;
                        }

                        var userOrderAddressDTO = {
                            userName:$scope.orderParticulars.userNameAddress,
                            userPhone:$scope.orderParticulars.userPhoneAddress,
                            province:$scope.userAddress[0],
                            city:$scope.userAddress[1],
                            detailAddress:$scope.userAddress[2],
                            id:$scope.orderParticulars.userOrderAddressId
                        };
                        UpdateOrderAddress.save(userOrderAddressDTO,function(data){
                            ManagementUtil.checkResponseData(data,"");
                            if(data.result == Global.SUCCESS){
                                     $state.go("order")
                            }

                        })
                    }

                }
            })


        }]);