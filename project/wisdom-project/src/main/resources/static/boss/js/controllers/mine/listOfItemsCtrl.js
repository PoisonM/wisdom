angular.module('controllers',[]).controller('listOfItemsCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetShopTwoLevelProjectList','Global',
        function ($scope,$rootScope,$stateParams,$state,GetShopTwoLevelProjectList,Global) {

            $scope.param = {
                filterStr:"",
                arr:[],
                id:$stateParams.id,

            }
            console.log($stateParams.id)
            $scope.searchProject = function () {
                $scope.getInfo()
            }
            $scope.getInfo = function(){
                GetShopTwoLevelProjectList.get({
                    filterStr:$scope.param.filterStr,
                    pageNo:1,
                    pageSize:1000
                },function (data) {
                    if(data.result==Global.SUCCESS&&data.responseData!=null){
                        $scope.listOfItems = data.responseData;
                        for(var i=0;i<$scope.listOfItems.length;i++){
                            $scope.param.arr[i]=new Array;
                            for(var k=0;k<$scope.listOfItems[i].threeProjectList.length;k++){
                                $scope.param.arr[i][k] = true

                            }
                        }
                        if($rootScope.settingAddsome.editorCard.shopProjectInfoDTOS !=null){

                            var list = $rootScope.settingAddsome.editorCard.shopProjectInfoDTOS;
                            for(var i=0;i<$scope.listOfItems.length;i++){
                                for(var j=0;j<list.length;j++){
                                    if($scope.listOfItems[i].twoLevel.projectTypeTwoId == list[j].projectTypeTwoId ){
                                        for(var k=0;k<$scope.listOfItems[i].threeProjectList.length;k++){
                                            if($scope.listOfItems[i].threeProjectList[k].id == list[j].id ){
                                                console.log(k)
                                                console.log(i)
                                                $scope.param.arr[i][k] = false
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            $rootScope.settingAddsome.editorCard.shopProjectInfoDTOS=[]
                        }



                    }
                })
            }
            $scope.getInfo()


            $scope.addProject = function (items,index,indexs) {
                $scope.param.arr[index][indexs] = !$scope.param.arr[index][indexs]
                if($rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.length<=0){
                    $rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.push(items)
                    $scope.allAarketPrice()
                }else{
                    for(var i=0;i<$rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.length;i++){
                        if($rootScope.settingAddsome.editorCard.shopProjectInfoDTOS[i].id == items.id){
                            $rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.splice(i,1)
                            $scope.allAarketPrice()
                            return
                        }else{
                            $rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.push(items)
                            $scope.allAarketPrice()
                            return
                        }

                    }
                }


            }
            $scope.allAarketPrice = function () {
                $rootScope.settingAddsome.editorCard.marketPrice =0
                for(var i=0;i<$rootScope.settingAddsome.editorCard.shopProjectInfoDTOS.length;i++){
                    $rootScope.settingAddsome.editorCard.marketPrice =$rootScope.settingAddsome.editorCard.marketPrice+($rootScope.settingAddsome.editorCard.shopProjectInfoDTOS[i].marketPrice)*($rootScope.settingAddsome.editorCard.shopProjectInfoDTOS[i].serviceTimes)

                }
                console.log( $rootScope.settingAddsome.editorCard.marketPrice)
            }
            $scope.editorCardGo = function(){
                $state.go($stateParams.url,{id:$scope.param.id})
            }

        }])
